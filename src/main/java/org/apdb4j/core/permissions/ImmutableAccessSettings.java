package org.apdb4j.core.permissions;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.Set;

/**
 * An immutable AccessSettings. Set methods will throw an UnsupportedOperationException.
 */
public class ImmutableAccessSettings implements AccessSettings {

    private final Pair<
            Pair<AccessType.Read, Set<Class<? extends Access>>>,
            Pair<AccessType.Write, Set<Class<? extends Access>>>> pair;

    /**
     * Creates an immutable access settings object.
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets.
     */
    public ImmutableAccessSettings(final @NonNull Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                                   final @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        pair = new ImmutablePair<>(
                new ImmutablePair<>(
                        read.getLeft(),
                        read.getRight()),
                new ImmutablePair<>(
                        write.getLeft(),
                        write.getRight()));
    }

    /**
     * Creates an immutable access settings object.
     * @param read a pair containing the {@code READ} or {@code NONE} access type.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type.
     */
    public ImmutableAccessSettings(final AccessType.Read read,
                                   final AccessType.Write write) {
        this(Pair.of(read, Collections.emptySet()), Pair.of(write, Collections.emptySet()));
    }

    /**
     * Creates an immutable access settings object.
     * @param read a pair containing the {@code READ} or {@code NONE} access type.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets.
     */
    public ImmutableAccessSettings(final AccessType.Read read,
                                   final Pair<AccessType.Write, Set<Class<? extends Access>>> write) {
        this(Pair.of(read, Collections.emptySet()), write);
    }

    /**
     * Creates an immutable access settings object.
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type.
     */
    public ImmutableAccessSettings(final Pair<AccessType.Read, Set<Class<? extends Access>>> read,
                                   final AccessType.Write write) {
        this(read, Pair.of(write, Collections.emptySet()));
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

}
