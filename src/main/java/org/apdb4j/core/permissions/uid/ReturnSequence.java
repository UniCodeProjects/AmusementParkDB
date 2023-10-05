package org.apdb4j.core.permissions.uid;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.codec.digest.XXHash32;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSetting;
import org.apdb4j.core.permissions.AccessType;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Collects all the information contained in an {@code AccessSetting} object.<br>
 * Generates a hash that represents the group of information contained in this
 * {@code ReturnSequence} object. A specific {@code AccessSetting} can be identified and retrieved
 * by using the previously generated hash.
 * @see AccessSetting
 */
@Getter
@ToString
public final class ReturnSequence {

    private static final Map<String, ReturnSequence> HASH_AND_RETURN_SEQUENCES = new HashMap<>();

    private final String hash;
    private final AccessType.Read read;
    private final AccessType.Write write;
    private final Set<Class<? extends Access>> readTargets;
    private final Set<Class<? extends Access>> writeTargets;

    /**
     * Creates a new {@code ReturnSequence} instance by collecting the contents of the
     * given {@code AccessSetting}.
     * @param setting the given access setting
     */
    public ReturnSequence(final @NonNull AccessSetting setting) {
        read = setting.getReadAccess().getLeft();
        readTargets = setting.getReadAccess().getRight();
        write = setting.getWriteAccess().getLeft();
        writeTargets = setting.getWriteAccess().getRight();
        hash = generateHash();
        HASH_AND_RETURN_SEQUENCES.put(hash, this);
    }

    /**
     * Retrieves the {@code ReturnSequence} associated with the given hash.
     * @param hash the hash linked to the desired {@code ReturnSequence}
     * @return the {@code ReturnSequence} linked to the hash
     */
    public static ReturnSequence getFromHash(final @NonNull String hash) {
        return HASH_AND_RETURN_SEQUENCES.get(hash);
    }

    private String generateHash() {
        final XXHash32 hash = new XXHash32();
        final String input = read.toString() + write + readTargets + writeTargets;
        hash.update(input.getBytes(StandardCharsets.UTF_8));
        return Long.toHexString(hash.getValue());
    }

}
