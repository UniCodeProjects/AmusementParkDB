package org.apdb4j.core.permissions;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apdb4j.core.permissions.account.GuestPermission;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Maps an Access class instance to an actual database tuple.
 * @see Access
 */
// TODO: make this class the generator, instead of the validator.
public final class PermissionMapper {

    private static final String CLASS_NAME_PATTERN_TO_MATCH = "Permission";

    private PermissionMapper() {
    }

    /**
     * Inserts a tuple in the database by using
     * the provided access object as a data source.
     * @param source the access object
     */
    public static void insertInDB(final @NonNull Access source) {
        final String permissionType = getSimplePermissionName(source);
        final List<String> packageCode = getPackageCodes(Arrays.stream(source.getClass().getInterfaces()).map(Class::getPackageName).toList()); // TODO: make it like in perm validator.
        final List<String> interfaceCode = getInterfaceCodes(Arrays.stream(source.getClass().getInterfaces()).map(Class::getSimpleName).toList());
        final List<String> accessSequence = getReturnValuesSequences(source);
        if (packageCode.size() != interfaceCode.size() || packageCode.size() != accessSequence.size()) {
            throw new IllegalStateException(); // TODO: add message.
        }
        // TODO: implement full string gen.
    }

    private static @NonNull String getSimplePermissionName(final @NonNull Access permission) {
        final String className = permission.getClass().getSimpleName();
        if (!className.contains(CLASS_NAME_PATTERN_TO_MATCH)) {
            throw new IllegalStateException("Wrong class name format: "
                    + className + " does not contain 'Permission' in its name.");
        }
        return className.replace(CLASS_NAME_PATTERN_TO_MATCH, "");
    }

    private static @NonNull List<String> getPackageCodes(final @NonNull List<String> packageName) {
        final var regex = "(?<=\\.)\\w+$";
        final List<String> result = new ArrayList<>();
        packageName.forEach(name -> result.add("P"
                + getMatch(name, regex)
                .toUpperCase(Locale.ROOT)
                .charAt(0)));
        return result;
    }

    // Retrieves the correct interface code (alphabetical + numerical).
    private static @NonNull List<String> getInterfaceCodes(final @NonNull List<String> interfaces) {
        final var interfaceCodesMap = generateAllInterfaceCodes();
        final List<String> result = new ArrayList<>();
        interfaces.forEach(i -> {
            result.add(interfaceCodesMap.get(i));
        });
        return result;
    }

    @SneakyThrows
    private static @NonNull Map<String, String> generateAllInterfaceCodes() {
        final var allInterfaces = PermissionConsistencyValidator.getInstance().getKnownAccessInterfacesNames();
        final Map<String, String> interfaceCodesMap = new TreeMap<>();
        for (final String i : allInterfaces) {
            final String code = generateInterfaceCode(Class.forName(i).getSimpleName());
            if (interfaceCodesMap.containsValue(code)) {
                final int duplicateCount = (int) interfaceCodesMap.values().stream()
                        .filter(string -> string.equals(code))
                        .count();
                interfaceCodesMap.put(Class.forName(i).getName(),
                        generateInterfaceCode(Class.forName(i).getSimpleName(), duplicateCount));
            } else {
                interfaceCodesMap.put(Class.forName(i).getName(),
                        generateInterfaceCode(Class.forName(i).getSimpleName()));
            }
        }
        return interfaceCodesMap;
    }

    private static @NonNull String generateInterfaceCode(final @NonNull String interfaceName) {
        return interfaceName.substring(0, 3).toUpperCase(Locale.ROOT) + "00";
    }

    private static @NonNull String generateInterfaceCode(final @NonNull String interfaceName, final int numericalCode) {
        final int numericalCodeLength = String.valueOf(numericalCode).length();
        final String numCodeAsString = switch (numericalCodeLength) {
            case 1 -> "0" + numericalCode;
            case 2 -> String.valueOf(numericalCode);
            default -> throw new IllegalArgumentException("Numerical code cannot be greater than 99: " + numericalCode);
        };
        return interfaceName.substring(0, 3).toUpperCase(Locale.ROOT) + numCodeAsString;
    }

    private static @NonNull List<String> getReturnValuesSequences(final @NonNull Access source) {
        return Arrays.stream(source.getClass().getInterfaces())
                .map(i -> i.<Access>asSubclass(Access.class))
                .map(i -> getSingleReturnSequence(source, i))
                .toList();
    }

    @SneakyThrows
    private static @NonNull String getSingleReturnSequence(final @NonNull Access source,
                                                           final @NonNull Class<? extends Access> currentInterface) {
        final var methods = Arrays.stream(source.getClass().getDeclaredMethods())
                .filter(method -> Arrays.stream(currentInterface.getMethods()).anyMatch(m -> m.getName().equals(method.getName())))
                .sorted(Comparator.comparing(Method::getName))
                .toList();
        // Not using lambdas to allow the use of SneakyThrows.
        final List<AccessType> returnValues = new ArrayList<>();
        for (final var method : methods) {
            returnValues.add((AccessType) method.invoke(source));
        }
        // Joining the accesses in one string.
        return returnValues.stream()
                .map(AccessType::toString)
                .collect(Collectors.joining());
    }

    private static @NonNull String getMatch(final @NonNull String string, final @NonNull String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);
        String match = "";
        if (matcher.find()) {
            match = matcher.group();
        }
        return match;
    }

    // TODO: remove.
    public static void main(String[] args) {
        PermissionMapper.insertInDB(new GuestPermission());
    }

}
