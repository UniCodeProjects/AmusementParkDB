package org.apdb4j.core.permissions.uid;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSetting;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.AllAccess;
import org.apdb4j.core.permissions.ImmutableAccessSetting;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.util.RegexUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
    private static final char UID_INTERNAL_DELIMITER = '.';
    private static final char UID_EXTERNAL_DELIMITER = '-';

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

    private @NonNull String assemblePermissionUID() {
        final MultiValuedMap<String, String> permissionMap = generateAllInterfaceCodes();
        final List<String> sortedKeys = permissionMap.keySet().stream().sorted().toList();
        final var result = new StringBuilder();
        sortedKeys.forEach(key -> {
            final List<String> values = permissionMap.get(key).stream().toList();
            final String packageCode = values.get(0);
            final String interfaceCode = values.get(1);
            final String accessSequence = values.get(2);
            result.append(packageCode)
                    .append(UID_INTERNAL_DELIMITER)
                    .append(interfaceCode)
                    .append(UID_INTERNAL_DELIMITER)
                    .append(accessSequence)
                    .append(UID_EXTERNAL_DELIMITER);
        });
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

    private @NonNull String generatePackageCodes(final @NonNull String packageName) {
        final var regex = "(?<=\\.)\\w+$";
        return "P" + (RegexUtils.getMatch(packageName, regex).orElseThrow())
                .toUpperCase(Locale.ROOT)
                .charAt(0);
    }

    @SneakyThrows
    private @NonNull MultiValuedMap<String, String> generateAllInterfaceCodes() {
        final var allInterfaces = getKnownAccessInterfacesNames();
        final MultiValuedMap<String, String> interfacesAndCodes = new ArrayListValuedHashMap<>();
        for (final String currentInterface : allInterfaces) {
            final var actualInterface = Class.forName(currentInterface).asSubclass(Access.class);
            // Putting the package code for the current interface.
            interfacesAndCodes.put(currentInterface, generatePackageCodes(actualInterface.getPackageName()));
            // Generating the interface code and checking for duplicates.
            final String interfaceCode = generateInterfaceCode(currentInterface);
            if (interfacesAndCodes.containsValue(interfaceCode)) {
                final int duplicateCount = (int) interfacesAndCodes.values().stream()
                        .filter(string -> string.equals(interfaceCode))
                        .count();
                // Putting the interface code with a progressive number sequence.
                interfacesAndCodes.put(currentInterface, generateInterfaceCode(currentInterface, duplicateCount));
            } else {
                interfacesAndCodes.put(currentInterface, generateInterfaceCode(currentInterface));
            }
            // Putting the return values sequence for the current interface.
            interfacesAndCodes.put(currentInterface, generateReturnSequence(currentInterface));
        }
        return interfacesAndCodes;
    }

    @SneakyThrows
    private @NonNull String generateReturnSequence(final @NonNull String interfaceName) {
        final var actualInterface = Class.forName(interfaceName).asSubclass(Access.class);
        // If the source class implements the AllAccess interface always put Read and Write to GLOBAL.
        if (Arrays.asList(source.getClass().getInterfaces()).contains(AllAccess.class)) {
            final AccessSetting sequence = new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
            final String result = (new ReturnSequence(sequence).getHash() + '.')
                    .repeat(actualInterface.getDeclaredMethods().length);
            return result.substring(0, result.length() - 1);
        }
        // If the source class does not implement the interface, a 'None' Read and Write setting in returned.
        if (!Arrays.asList(source.getClass().getInterfaces()).contains(actualInterface)) {
            final AccessSetting sequence = new ImmutableAccessSetting(AccessType.Read.NONE, AccessType.Write.NONE);
            final String result = (new ReturnSequence(sequence).getHash() + '.')
                    .repeat(actualInterface.getDeclaredMethods().length);
            return result.substring(0, result.length() - 1);
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
                    .append('.');
        }
        sequence.deleteCharAt(sequence.length() - 1);
        return sequence.toString();
    }

    /*
     * Helper method for generateAllInterfaceCodes().
     * Generates an interface code with the default numerical sequence.
     */
    @SneakyThrows
    private @NonNull String generateInterfaceCode(final @NonNull String interfaceName) {
        return Class.forName(interfaceName)
                .getSimpleName()
                .substring(0, 3)
                .toUpperCase(Locale.ROOT) + "00";
    }

    /*
     * Helper method for generateAllInterfaceCodes().
     * Generates an interface code with a progressive numerical sequence.
     */
    @SneakyThrows
    private @NonNull String generateInterfaceCode(final @NonNull String interfaceName, final int numericalCode) {
        final int numericalCodeLength = String.valueOf(numericalCode).length();
        final String numCodeAsString = switch (numericalCodeLength) {
            case 1 -> "0" + numericalCode;
            case 2 -> String.valueOf(numericalCode);
            default -> throw new IllegalArgumentException("Numerical code cannot be greater than 99: " + numericalCode);
        };
        return Class.forName(interfaceName)
                .getSimpleName()
                .substring(0, 3)
                .toUpperCase(Locale.ROOT) + numCodeAsString;
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
