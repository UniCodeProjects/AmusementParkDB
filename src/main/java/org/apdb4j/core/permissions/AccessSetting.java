package org.apdb4j.core.permissions;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.Set;

/**
 * Represents the set values for the access types of {@code READ},
 * {@code WRITE} and {@code NONE}.
 * @see AccessType
 */
public interface AccessSetting {

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                            final @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        return new ImmutableAccessSetting(read, write);
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param read a pair containing the {@code READ} or {@code NONE} access type
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final AccessType.Read read,
                            final AccessType.Write write) {
        return new ImmutableAccessSetting(Pair.of(read, Collections.emptySet()), Pair.of(write, Collections.emptySet()));
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param read a pair containing the {@code READ} or {@code NONE} access type
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final AccessType.Read read,
                            final Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        return new ImmutableAccessSetting(Pair.of(read, Collections.emptySet()), write);
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                            final AccessType.Write write) {
        return new ImmutableAccessSetting(read, Pair.of(write, Collections.emptySet()));
    }

    /**
     * Sets the access setting for {@code READ}. This can also be set to {@code NONE}.
     * @param type {@code READ} or {@code NONE}
     * @param targets the account types to apply the access setting on
     */
    void setReadAccess(AccessType.Read type, Set<Class<? extends Access>> targets);

    /**
     * Sets the access setting for {@code WRITE}. This can also be set to {@code NONE}.
     * @param type {@code WRITE} or {@code NONE}
     * @param targets the account types to apply the access setting on
     */
    void setWriteAccess(AccessType.Write type, Set<Class<? extends Access>> targets);

    /**
     * Retrieves the access setting for {@code READ} and its targets.
     * @return a pair containing the access type and its targets.<br>
     *         {@code READ} if set, {@code NONE} otherwise.
     */
    @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> getReadAccess();

    /**
     * Retrieves the access setting for {@code WRITE} and its targets.
     * @return a pair containing the access type and its targets.<br>
     *         {@code WRITE} if set, {@code NONE} otherwise.
     */
    @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> getWriteAccess();

}
