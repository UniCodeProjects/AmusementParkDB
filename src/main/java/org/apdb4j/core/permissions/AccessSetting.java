package org.apdb4j.core.permissions;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the set values for the access types of {@code READ},
 * {@code WRITE} and {@code NONE}.
 * @see AccessType
 */
public interface AccessSetting {

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param attribute the attribute related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull TableField<Record, ?> attribute,
                            final @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                            final @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        return new ImmutableAccessSetting(attribute, read, write);
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param attributes the attributes related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull Set<TableField<Record, ?>> attributes,
                            final @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                            final @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        return new ImmutableAccessSetting(attributes, read, write);
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param attribute the attribute related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull TableField<Record, ?> attribute,
                            final AccessType.Read read,
                            final AccessType.Write write) {
        return new ImmutableAccessSetting(attribute,
                Pair.of(read, Collections.emptySet()),
                Pair.of(write, Collections.emptySet()));
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param attributes the attributes related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull Set<TableField<Record, ?>> attributes,
                            final AccessType.Read read,
                            final AccessType.Write write) {
        return new ImmutableAccessSetting(attributes,
                Pair.of(read, Collections.emptySet()),
                Pair.of(write, Collections.emptySet()));
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param attribute the attribute related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull TableField<Record, ?> attribute,
                            final AccessType.Read read,
                            final Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        return new ImmutableAccessSetting(attribute, Pair.of(read, Collections.emptySet()), write);
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param attributes the attributes related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull Set<TableField<Record, ?>> attributes,
                            final AccessType.Read read,
                            final Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        return new ImmutableAccessSetting(attributes, Pair.of(read, Collections.emptySet()), write);
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param attribute the attribute related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull TableField<Record, ?> attribute,
                            final Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                            final AccessType.Write write) {
        return new ImmutableAccessSetting(attribute, read, Pair.of(write, Collections.emptySet()));
    }

    /**
     * Creates an {@link ImmutableAccessSetting}.
     * @param attributes the attributes related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type
     * @return an immutable AccessSetting
     */
    static AccessSetting of(final @NonNull Set<TableField<Record, ?>> attributes,
                            final Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                            final AccessType.Write write) {
        return new ImmutableAccessSetting(attributes, read, Pair.of(write, Collections.emptySet()));
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

    /**
     * Retrieves the attribute referred to te access setting.
     * @return the attribute
     */
    @NonNull Optional<TableField<Record, ?>> getAttribute();

    /**
     * Retrieves the attributes referred to te access setting.
     * @return the attributes
     */
    @NonNull Optional<Set<TableField<Record, ?>>> getAttributes();

}
