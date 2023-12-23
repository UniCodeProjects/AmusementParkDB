package org.apdb4j.core.permissions.uid;

import lombok.NonNull;
import org.apache.commons.codec.digest.XXHash32;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a sequence that can be hashed.
 * A specific {@code PermissionUID} section can be identified and retrieved
 * by using the generated hash.
 * @see Sequence
 */
public class HashableSequence {

    private static final Map<String, Sequence> HASH_AND_RETURN_SEQUENCES = new HashMap<>();

    /**
     * Retrieves the {@code Sequence} associated with the given hash.
     * @param hash the hash linked to the desired {@code Sequence}
     * @return the {@code Sequence} linked to the hash
     */
    public static Sequence getFromHash(final @NonNull String hash) {
        return HASH_AND_RETURN_SEQUENCES.get(stringNonBlank(hash));
    }

    /**
     * Generates the sequence's hash given the sequence and the desired input to be hashed.
     * @param source the sequence to be linked to the hash
     * @param input the meaningful data to be hashed
     * @return a string containing the hashed data as hex values
     */
    protected String generateHash(final @NonNull Sequence source, final @NonNull String input) {
        final XXHash32 hash = new XXHash32();
        hash.update(input.getBytes(StandardCharsets.UTF_8));
        final String result = Long.toHexString(hash.getValue());
        HASH_AND_RETURN_SEQUENCES.put(result, source);
        return result;
    }

    private static String stringNonBlank(final @NonNull String string) {
        if (string.isBlank()) {
            throw new IllegalArgumentException("The given string is blank.");
        }
        return string;
    }

}
