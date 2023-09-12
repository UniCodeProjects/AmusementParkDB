package org.apdb4j.core.permissions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apdb4j.core.permissions.account.AccountAccess2Impl;
import org.apdb4j.util.BitSequence;
import org.apdb4j.util.BitSequenceImpl;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
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

    @Getter private static final PermissionIntegrityValidator INSTANCE = new PermissionIntegrityValidator();
    @NonNull private final List<String> knownAccessInterfacesNames = getKnownAccessInterfacesNames();

    /**
     * @return
     */
    public @NonNull String generateIdentifierSequence(@NonNull final Class<? extends Access> permissions) {
        final List<BitSequence> bitSequenceList = createListAtRunTime(permissions.getName());
        final List<String> prefixCodes = generateSequencePrefixes();
        final List<String> bitSequences = generateBitSequencesAsStrings(bitSequenceList);
        final var result = new StringBuilder();
        if (prefixCodes.size() == bitSequences.size()) {
            for (int i = 0; i < prefixCodes.size(); i++) {
                result.append(prefixCodes.get(i))
                        .append(".")
                        .append(bitSequences.get(i))
                        .append("-");
            }
        } else {
            throw new IllegalStateException();
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
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

    private static List<String> getKnownAccessInterfacesNames() {
        Reflections reflections = new Reflections("org.apdb4j.core.permissions");
        return reflections.getSubTypesOf(Access.class).stream()
                .filter(Class::isInterface)
                .map(Class::getName)
                .sorted()
                .toList();
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
                className = "";
            }
            map.put(i, className);
        }
        return map;
    }

    @SneakyThrows
    private String getSimpleClassName(@NonNull final String longClassName) {
        final var actualClass = Class.forName(longClassName);
        return actualClass.getSimpleName();
    }

    @SneakyThrows
    private List<String> getSimpleClassName(@NonNull final List<String> longClassNames) {
        final List<String> result = new ArrayList<>();
        for (final var name : longClassNames) {
            result.add(Class.forName(name).getSimpleName());
        }
        return result;
    }

    // TODO: needs cleanup.
    @SneakyThrows
    private List<String> generateSequencePrefixes() {
        final var stringBuilder = new StringBuilder();
        final List<String> result = new ArrayList<>();
        for (final var i : knownAccessInterfacesNames) {
            final var actualInterface = Class.forName(i).asSubclass(Access.class);
            Pattern pattern = Pattern.compile("\\b\\w");
            final var packagePath = actualInterface.getName().replace(actualInterface.getSimpleName(), "");
            Matcher matcher = pattern.matcher(packagePath);
            while (matcher.find()) {
                stringBuilder.append(matcher.group());
            }
            stringBuilder.append(".");
            final var restOfName = actualInterface.getSimpleName().substring(0, 3);
            stringBuilder.append(restOfName);

            // Check if present in history.
            if (result.contains(stringBuilder.toString().toUpperCase() + "00")) {
                final int amount = Math.toIntExact(
                        result.stream()
                                .filter(s -> s.contentEquals(stringBuilder))
                                .count());
                var amountAsString = String.valueOf(amount + 1);
                if (amountAsString.length() > 2) {
                    throw new IllegalStateException();
                }
                if (amountAsString.length() == 1) {
                    amountAsString = "0" + amountAsString;
                }
                stringBuilder.append(amountAsString);
                result.add(stringBuilder.toString().toUpperCase());
            } else {
                stringBuilder.append("00");
                result.add(stringBuilder.toString().toUpperCase());
            }
            stringBuilder.setLength(0);
        }
        return result;
    }

    private List<String> generateBitSequencesAsStrings(@NonNull final List<BitSequence> bitSequences) {
        final SortedMap<String, String> interfaceBitSequenceMap = new TreeMap<>();
        // Init tree map.
        knownAccessInterfacesNames.forEach(s -> interfaceBitSequenceMap.put(s, getDefaultSequence(s)));
        // Populate map with actual bit sequences.
        for (BitSequence sequence : bitSequences) {
            final var accessInterface = ((BitSequenceImpl) sequence).getAccessInterface();
            interfaceBitSequenceMap.replace(accessInterface, sequence.getSequenceAsString());
        }
        return interfaceBitSequenceMap.values().stream().toList();
    }

    @SneakyThrows
    private String getDefaultSequence(@NonNull final String interfaceName) {
        final var actualInterface = Class.forName(interfaceName).asSubclass(Access.class);
        final int numberOfMethods = actualInterface.getDeclaredMethods().length;
        return "0".repeat(numberOfMethods);
    }

    private String numberOfOccurrencesAsString(@NonNull final List<String> history, @NonNull final String testString) {
        final int occurrences = Math.toIntExact(
                history.stream()
                        .filter(s -> s.equals(testString))
                        .count());
        final var numberAsString = String.valueOf(occurrences);
        return numberAsString.length() == 1 ? "0" + numberAsString : numberAsString;
    }

    // todo: remove.
    public static void main(final String[] args) {
        System.out.println(PermissionIntegrityValidator.getINSTANCE().generateIdentifierSequence(AccountAccess2Impl.class));
    }

}
