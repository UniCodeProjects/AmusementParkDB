package org.apdb4j.core.permissions.uid;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSetting;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.AllAccess;
import org.apdb4j.core.permissions.ImmutableAccessSetting;
import org.apdb4j.util.QueryBuilder;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.apdb4j.db.tables.Permissions.PERMISSIONS;

/**
 * Represents an UID (Unique ID) for a set of access settings (permission).
 * It represents the application-side permission UID.
 * @see Access
 */
public class AppPermissionUID implements PermissionUID {

    private final Access source;
    private final @Getter UID uid;
    private static final String CLASS_NAME_PATTERN_TO_MATCH = "Permission";
    private static final char INTERNAL_SEPARATOR = '-';
    private static final char RETURN_SEQUENCE_SEPARATOR = '.';
    private static final char EXTERNAL_SEPARATOR = '/';

    /**
     * Creates a new UID from a permission class.
     * @param source the permission object
     */
    @SuppressFBWarnings("MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR")
    public AppPermissionUID(final @NonNull Access source) {
        this.source = source;
        uid = new UID(assemblePermissionUID());
    }

    /**
     * Maps the given permission instance to an actual database tuple.
     * @return {@code true} on successful insertion
     */
    public boolean insertInDB() {
        final var type = getSimplePermissionName(source);
        final int insertedTuples = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.insertInto(PERMISSIONS)
                        .values(type, uid.uid())
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return insertedTuples == 1;
    }

    @SneakyThrows
    private @NonNull String assemblePermissionUID() {
        final List<String> interfacesNames = getKnownAccessInterfacesNames();
        final var result = new StringBuilder();
        for (final String i : interfacesNames) {
            final var actual = Class.forName(i).asSubclass(Access.class);
            result.append(new PackageInterfaceSequence(actual.getPackage(), actual).getHash())
                    .append(INTERNAL_SEPARATOR)
                    .append(generateReturnSequence(i))
                    .append(EXTERNAL_SEPARATOR);
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private @NonNull String getSimplePermissionName(final @NonNull Access permission) {
        final String className = permission.getClass().getSimpleName();
        if (!className.contains(CLASS_NAME_PATTERN_TO_MATCH)) {
            throw new IllegalStateException("Wrong class name format: "
                    + className + " does not contain 'Permission' in its name.");
        }
        return className.replace(CLASS_NAME_PATTERN_TO_MATCH, "");
    }

    @SneakyThrows
    private @NonNull String generateReturnSequence(final @NonNull String interfaceName) {
        final var actualInterface = Class.forName(interfaceName).asSubclass(Access.class);
        // If the source class implements the AllAccess interface always put Read and Write to GLOBAL.
        if (Arrays.asList(source.getClass().getInterfaces()).contains(AllAccess.class)) {
            return getReturnSequenceOf(actualInterface, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
        }
        // If the source class does not implement the interface, a 'None' Read and Write setting in returned.
        if (!Arrays.asList(source.getClass().getInterfaces()).contains(actualInterface)) {
            return getReturnSequenceOf(actualInterface, AccessType.Read.NONE, AccessType.Write.NONE);
        }
        // Gets only the methods that are actively implemented by source.
        final var methods = Arrays.stream(source.getClass().getDeclaredMethods())
                .filter(method -> Arrays.stream(actualInterface.getMethods())
                        .anyMatch(m -> m.getName().equals(method.getName())))
                .sorted(Comparator.comparing(Method::getName))
                .toList();
        final var sequence = new StringBuilder();
        for (final var method : methods) {
            final AccessSetting returnedValue = (AccessSetting) method.invoke(source);
            sequence.append(new ReturnSequence(returnedValue).getHash())
                    .append(RETURN_SEQUENCE_SEPARATOR);
        }
        sequence.deleteCharAt(sequence.length() - 1);
        return sequence.toString();
    }

    private String getReturnSequenceOf(final Class<? extends Access> actualInterface,
                                       final AccessType.Read type, final AccessType.Write type2) {
        final AccessSetting sequence = new ImmutableAccessSetting(type, type2);
        final String result = (new ReturnSequence(sequence).getHash() + RETURN_SEQUENCE_SEPARATOR)
                .repeat(actualInterface.getDeclaredMethods().length);
        return result.substring(0, result.length() - 1);
    }

    // Gets all the known Access interfaces in the package.
    private List<String> getKnownAccessInterfacesNames() {
        final String rootPackage = "org.apdb4j.core.permissions";
        final Reflections reflections = new Reflections(rootPackage);
        return reflections.getSubTypesOf(Access.class).stream()
                .filter(Class::isInterface)
                .map(Class::getName)
                .filter(name -> name.startsWith(rootPackage + '.')    // Filter only the subpackages of rootPackage.
                        && name.lastIndexOf('.') > (rootPackage + '.').length())
                .sorted()
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppPermissionUID that = (AppPermissionUID) o;
        return Objects.equals(uid, that.uid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

}
