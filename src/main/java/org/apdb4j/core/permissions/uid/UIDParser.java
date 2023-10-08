package org.apdb4j.core.permissions.uid;

import lombok.NonNull;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apdb4j.core.permissions.Access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A parser for the {@code PermissionUID}.
 */
public final class UIDParser {

    private UIDParser() {
    }

    /**
     * Retrieves the actual contents of the UID.
     * @param uid the uid
     * @return a list of {@link UIDSection}, each representing a section of the whole uid
     */
    public static @NonNull List<UIDSection> parse(final @NonNull String uid) {
        final List<String> sections = splitUID(uid, "/");
        final MultiValuedMap<String, String> sectionMap = new ArrayListValuedHashMap<>();
        for (final String section : sections) {
            final String packageAndInterface = splitUID(section, "-").stream().reduce(Objects::toString).orElseThrow();
            final List<String> returnValues = splitUID(section.replaceAll(".+-", ""), "\\.");
            returnValues.forEach(value -> sectionMap.put(packageAndInterface, value));
        }
        final List<UIDSection> result = new ArrayList<>();
        sectionMap.keySet().forEach(key -> {
            final Collection<String> values = sectionMap.get(key);
            final List<ReturnSequence> valuesHashes = values.stream()
                    .map(ReturnSequence::getFromHash)
                    .map(sequence -> (ReturnSequence) sequence)
                    .toList();
            result.add(new UIDSection((PackageInterfaceSequence) PackageInterfaceSequence.getFromHash(key), valuesHashes));
        });
        return result;
    }

    /**
     * Returns the {@link UIDSection} to which the specified interface is mapped.
     * @param parsed the list of parsed sections
     * @param anInterface the interface
     * @return the {@link UIDSection} to which the specified interface is mapped
     */
    public static @NonNull UIDSection get(final List<UIDSection> parsed, final @NonNull Class<? extends Access> anInterface) {
        return parsed.stream()
                .filter(uidSection -> uidSection.packageInterfaceSequence().getInterface().equals(anInterface))
                .findFirst()
                .orElseThrow();
    }

    private static List<String> splitUID(final String uid, final @NonNull String regex) {
        return Arrays.asList(uid.split(regex, 0));
    }

}
