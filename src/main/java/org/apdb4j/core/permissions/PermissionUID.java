package org.apdb4j.core.permissions;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
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
 * Generates an UID (Unique ID) for a set of access settings (permission).
 * @see Access
 */
public final class PermissionUID {

    private final Access source;
    private final String uid;
    private static final String CLASS_NAME_PATTERN_TO_MATCH = "Permission";
    private static final char UID_INTERNAL_DELIMITER = '.';
    private static final char UID_EXTERNAL_DELIMITER = '-';

    /**
     * Creates a new UID from a permission class.
     * @param source the permission object
     */
    public PermissionUID(final @NonNull Access source) {
        this.source = source;
        uid = assemblePermissionUID();
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
                        .values(type, uid)
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
        // If the source class does not implement the interface, a 'None' sequence in returned.
        if (!Arrays.asList(source.getClass().getInterfaces()).contains(actualInterface)) {
            return AccessType.NONE.toString().repeat(actualInterface.getDeclaredMethods().length);
        }
        // Gets only the methods that are actively implemented by source.
        final var methods = Arrays.stream(source.getClass().getDeclaredMethods())
                .filter(method -> Arrays.stream(actualInterface.getMethods())
                        .anyMatch(m -> m.getName().equals(method.getName())))
                .sorted(Comparator.comparing(Method::getName))
                .toList();
        final var sequence = new StringBuilder();
        for (final var method : methods) {
            sequence.append(method.invoke(source));
        }
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
        final Reflections reflections = new Reflections("org.apdb4j.core.permissions");
        return reflections.getSubTypesOf(Access.class).stream()
                .filter(Class::isInterface)
                .map(Class::getName)
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
        final PermissionUID that = (PermissionUID) o;
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
