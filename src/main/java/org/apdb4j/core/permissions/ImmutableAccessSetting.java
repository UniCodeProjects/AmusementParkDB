package org.apdb4j.core.permissions;

import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * An immutable AccessSettings. Set methods will throw an UnsupportedOperationException.
 */
@ToString
public class ImmutableAccessSetting implements AccessSetting {

    private final Set<TableField<Record, ?>> attributes;
    private final Pair<
            Pair<AccessType.Read, Set<Class<? extends Access>>>,
            Pair<AccessType.Write, Set<Class<? extends Access>>>> pair;

    /**
     * Creates an immutable access settings object.
     * @param attribute the attribute related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets.
     */
    public ImmutableAccessSetting(final @NonNull TableField<Record, ?> attribute,
                                  final @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                                  final @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        attributes = Set.of(attribute);
        pair = new ImmutablePair<>(
                new ImmutablePair<>(
                        read.getLeft(),
                        Set.copyOf(read.getRight())),
                new ImmutablePair<>(
                        write.getLeft(),
                        Set.copyOf(write.getRight())));
    }

    /**
     * Creates an immutable access settings object.
     * @param attributes the attributes related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets.
     */
    public ImmutableAccessSetting(final @NonNull Set<TableField<Record, ?>> attributes,
                                  final @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                                  final @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        this.attributes = Set.copyOf(attributes);
        pair = new ImmutablePair<>(
                new ImmutablePair<>(
                        read.getLeft(),
                        Set.copyOf(read.getRight())),
                new ImmutablePair<>(
                        write.getLeft(),
                        Set.copyOf(write.getRight())));
    }

    /**
     * Creates an immutable access settings object.
     * @param attribute the attribute related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type.
     */
    public ImmutableAccessSetting(final TableField<Record, ?> attribute,
                                  final AccessType.Read read,
                                  final AccessType.Write write) {
        this(attribute, Pair.of(read, Collections.emptySet()), Pair.of(write, Collections.emptySet()));
    }

    /**
     * Creates an immutable access settings object.
     * @param attributes the attributes related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type.
     */
    public ImmutableAccessSetting(final Set<TableField<Record, ?>> attributes,
                                  final AccessType.Read read,
                                  final AccessType.Write write) {
        this(attributes, Pair.of(read, Collections.emptySet()), Pair.of(write, Collections.emptySet()));
    }

    /**
     * Creates an immutable access settings object.
     * @param attribute the attribute related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets.
     */
    public ImmutableAccessSetting(final TableField<Record, ?> attribute,
                                  final AccessType.Read read,
                                  final Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        this(attribute, Pair.of(read, Collections.emptySet()), write);
    }

    /**
     * Creates an immutable access settings object.
     * @param attributes the attributes related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets.
     */
    public ImmutableAccessSetting(final Set<TableField<Record, ?>> attributes,
                                  final AccessType.Read read,
                                  final Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        this(attributes, Pair.of(read, Collections.emptySet()), write);
    }

    /**
     * Creates an immutable access settings object.
     * @param attribute the attribute related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type.
     */
    public ImmutableAccessSetting(final TableField<Record, ?> attribute,
                                  final Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                                  final AccessType.Write write) {
        this(attribute, read, Pair.of(write, Collections.emptySet()));
    }

    /**
     * Creates an immutable access settings object.
     * @param attributes the attributes related to the access setting
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type.
     */
    public ImmutableAccessSetting(final Set<TableField<Record, ?>> attributes,
                                  final Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                                  final AccessType.Write write) {
        this(attributes, read, Pair.of(write, Collections.emptySet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReadAccess(final AccessType.Read type, final Set<Class<? extends Access>> targets) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWriteAccess(final AccessType.Write type, final Set<Class<? extends Access>> targets) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> getReadAccess() {
        final var read = pair.getLeft();
        return new ImmutablePair<>(read.getLeft(), read.getRight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> getWriteAccess() {
        final var write = pair.getRight();
        return new ImmutablePair<>(write.getLeft(), write.getRight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<TableField<Record, ?>> getAttribute() {
        return attributes.size() == 1 ? Optional.of(attributes.iterator().next()) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Set<TableField<Record, ?>>> getAttributes() {
        return Optional.of(Set.copyOf(attributes));
    }

}
