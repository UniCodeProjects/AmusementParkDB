package org.apdb4j.core.permissions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apdb4j.util.BitSequence;
import org.apdb4j.util.BitSequenceImpl;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates the integrity of the permissions coming from the stored java objects
 * and their database counterparts by generating an identifier sequence.
 * The same access settings from both the java objects and the database tables must
 * result in the same sequence. This ensures that no illegal access is granted
 * to access restricted queries.
 * The method that generates the identifier character sequences is bijective, meaning
 * that no collisions occur, and it is possible to infer the access settings by their
 * related identifier sequence.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionIntegrityValidator {

    private static final PermissionIntegrityValidator INSTANCE = new PermissionIntegrityValidator();
    private static final String SEQUENCE_INTERNAL_SEPARATOR = ".";
    private static final String SEQUENCE_EXTERNAL_SEPARATOR = "-";
    private static final int SEQUENCE_NUMERICAL_CODE_MAXSIZE = 2;
    private static final String SEQUENCE_NUMERICAL_CODE_DEFAULT = "0";
    private static final String EMPTY_STRING = "";
    @NonNull private final List<String> knownAccessInterfacesNames = getKnownAccessInterfacesNames();

    /**
     * Returns the single instance of this class.
     * @return the instance
     */
    public static PermissionIntegrityValidator getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the unique identifier for a set of access permissions.
     * @param permissions the class that implements a set of accesses
     * @return a string representing an UID for the given permissions
     * @see Access
     */
    public @NonNull String generatePermissionsUID(@NonNull final Class<? extends Access> permissions) {
        if (!isClass(permissions)) {
            throw new IllegalArgumentException(permissions + " is not a class.");
        }
        final List<BitSequence> bitSequenceList = createListAtRunTime(permissions.getName());
        final List<String> prefixCodes = generatePrefixes();
        final List<String> bitSequences = generateBitStrings(bitSequenceList);
        if (prefixCodes.size() != bitSequences.size()) {
            throw new IllegalStateException();
        }
        final var result = new StringBuilder();
        for (int i = 0; i < prefixCodes.size(); i++) {
            result.append(prefixCodes.get(i))
                    .append(SEQUENCE_INTERNAL_SEPARATOR)
                    .append(bitSequences.get(i))
                    .append(SEQUENCE_EXTERNAL_SEPARATOR);
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private boolean isClass(@NonNull final Class<? extends Access> clazz) {
        return !clazz.isInterface() && Access.class.isAssignableFrom(clazz);
    }

    private static List<String> getKnownAccessInterfacesNames() {
        final Reflections reflections = new Reflections("org.apdb4j.core.permissions");
        return reflections.getSubTypesOf(Access.class).stream()
                .filter(Class::isInterface)
                .map(Class::getName)
                .sorted()
                .toList();
    }

    @SneakyThrows
    private List<BitSequence> createListAtRunTime(@NonNull final String permissionClassName) {
        final var interfaceAndImplNames = createMapFromClassNames(knownAccessInterfacesNames, permissionClassName);
        final List<BitSequence> list = new ArrayList<>();
        // Using for loops instead of lambdas to allow the usage of the SneakyThrows annotation.
        for (final var entry : interfaceAndImplNames.entrySet()) {
            if (entry.getValue().isEmpty()) {
                continue;
            }
            final Class<? extends Access> actualInterface = Class.forName(entry.getKey()).asSubclass(Access.class);
            final Class<? extends Access> actualClass = Class.forName(entry.getValue()).asSubclass(Access.class);
            list.add(new BitSequenceImpl(actualInterface, actualClass));
        }
        return list;
    }

    @SneakyThrows
    private SortedMap<String, String> createMapFromClassNames(@NonNull final List<String> interfaces,
                                                                    @NonNull final String clazz) {
        final SortedMap<String, String> map = new TreeMap<>();
        for (final String i : interfaces) {
            final var actualInterface = Class.forName(i);
            final var actualClass = Class.forName(clazz);
            String className;
            if (actualInterface.isAssignableFrom(actualClass)) {
                className = clazz;
            } else {
                className = EMPTY_STRING;
            }
            map.put(i, className);
        }
        return map;
    }

    @SneakyThrows
    private List<String> generatePrefixes() {
        final List<String> result = new ArrayList<>();
        for (final var i : knownAccessInterfacesNames) {
            final var prefix = generatePrefixFromInterface(i);
            final var numericalSequence = generateNumericalSequenceFromPrefix(result, prefix);
            result.add(prefix.toUpperCase(Locale.ROOT) + numericalSequence);
        }
        return result;
    }

    @SneakyThrows
    private @NonNull String generatePrefixFromInterface(@NonNull final String interfaceName) {
        final var actualInterface = Class.forName(interfaceName).asSubclass(Access.class);
        final Pattern pattern = Pattern.compile("\\b\\w");
        // Input to match
        final var packagePath = actualInterface.getName()
                .replace("org.apdb4j.core", EMPTY_STRING)
                .replace(actualInterface.getSimpleName(), EMPTY_STRING);
        final Matcher matcher = pattern.matcher(packagePath);
        // Building the prefix.
        final var stringBuilder = new StringBuilder();
        while (matcher.find()) {
            stringBuilder.append(matcher.group());
        }
        stringBuilder.append(SEQUENCE_INTERNAL_SEPARATOR);
        final var restOfName = actualInterface.getSimpleName().substring(0, 3);
        stringBuilder.append(restOfName);
        return stringBuilder.toString();
    }

    private @NonNull String generateNumericalSequenceFromPrefix(@NonNull final List<String> history,
                                                                @NonNull final String prefix) {
        // Checking for duplicates.
        final String defaultNumericalSequence = SEQUENCE_NUMERICAL_CODE_DEFAULT.repeat(SEQUENCE_NUMERICAL_CODE_MAXSIZE);
        // The generated sequence to test, it is all in uppercase and starts with a 0 numerical sequence.
        final var testSequence = prefix.toUpperCase(Locale.ROOT) + defaultNumericalSequence;
        if (!history.contains(testSequence)) {
            return defaultNumericalSequence;
        }
        // Calculating the actual numerical sequence.
        final int amount = Math.toIntExact(
                history.stream()
                        .filter(s -> s.equals(prefix))
                        .count());
        return getAmountAsString(amount);
    }

    private @NonNull String getAmountAsString(final int amount) {
        var amountAsString = String.valueOf(amount + 1);
        if (amountAsString.length() > SEQUENCE_NUMERICAL_CODE_MAXSIZE) {
            throw new IllegalStateException();
        }
        if (amountAsString.length() < SEQUENCE_NUMERICAL_CODE_MAXSIZE) {
            final int repeatAmount = SEQUENCE_NUMERICAL_CODE_MAXSIZE - amountAsString.length();
            amountAsString = SEQUENCE_NUMERICAL_CODE_DEFAULT.repeat(repeatAmount) + amountAsString;
        }
        return amountAsString;
    }

    private List<String> generateBitStrings(@NonNull final List<BitSequence> bitSequences) {
        final SortedMap<String, String> interfaceBitSequenceMap = new TreeMap<>();
        // Init tree map.
        knownAccessInterfacesNames.forEach(s -> interfaceBitSequenceMap.put(s, getDefaultSequence(s)));
        // Populate map with actual bit sequences.
        for (final BitSequence sequence : bitSequences) {
            final var accessInterface = ((BitSequenceImpl) sequence).getAccessInterface();
            interfaceBitSequenceMap.replace(accessInterface, sequence.getSequenceAsString());
        }
        return interfaceBitSequenceMap.values().stream().toList();
    }

    @SneakyThrows
    private String getDefaultSequence(@NonNull final String interfaceName) {
        final var actualInterface = Class.forName(interfaceName).asSubclass(Access.class);
        final int numberOfMethods = actualInterface.getDeclaredMethods().length;
        return SEQUENCE_NUMERICAL_CODE_DEFAULT.repeat(numberOfMethods);
    }

}
