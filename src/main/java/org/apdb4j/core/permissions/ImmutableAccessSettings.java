package org.apdb4j.core.permissions;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Set;
import java.util.function.Predicate;

/**
 * An immutable AccessSettings. Set methods will throw an UnsupportedOperationException.
 */
public class ImmutableAccessSettings implements AccessSettings {

    private final Triple<Pair<AccessType, Set<Class<? extends Access>>>,
            Pair<AccessType, Set<Class<? extends Access>>>,
            Pair<AccessType, Set<Class<? extends Access>>>> triple;

    /**
     * Creates an immutable access settings object.
     * @param all a pair containing the {@code ALL} or {@code NONE} access type and its targets.
     * @param read a pair containing the {@code READ} or {@code NONE} access type and its targets.
     * @param write a pair containing the {@code WRITE} or {@code NONE} access type and its targets.
     */
    public ImmutableAccessSettings(final @NonNull Pair<AccessType, Set<Class<? extends Access>>> all,
                                   final @NonNull Pair<AccessType, Set<Class<? extends Access>>> read,
                                   final @NonNull Pair<AccessType, Set<Class<? extends Access>>> write) {
        triple = new ImmutableTriple<>(
                new ImmutablePair<>(
                        putAccessType(all.getLeft(), Set.of(AccessType.GLOBAL_ALL, AccessType.LOCAL_ALL)),
                        all.getRight()),
                new ImmutablePair<>(
                        putAccessType(read.getLeft(), Set.of(AccessType.GLOBAL_READ, AccessType.LOCAL_READ)),
                        read.getRight()),
                new ImmutablePair<>(
                        putAccessType(write.getLeft(), Set.of(AccessType.GLOBAL_WRITE, AccessType.LOCAL_WRITE)),
                        write.getRight()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAllAccess(final AccessType type, final Set<Class<? extends Access>> targets) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReadAccess(final AccessType type, final Set<Class<? extends Access>> targets) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWriteAccess(final AccessType type, final Set<Class<? extends Access>> targets) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Pair<AccessType, Set<Class<? extends Access>>> getAllAccess() {
        final var all = triple.getLeft();
        return new ImmutablePair<>(all.getLeft(), all.getRight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Pair<AccessType, Set<Class<? extends Access>>> getReadAccess() {
        final var read = triple.getMiddle();
        return new ImmutablePair<>(read.getLeft(), read.getRight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Pair<AccessType, Set<Class<? extends Access>>> getWriteAccess() {
        final var write = triple.getRight();
        return new ImmutablePair<>(write.getLeft(), write.getRight());
    }

    private AccessType putAccessType(final AccessType accessType, final Set<AccessType> required) {
        return putOrNone(accessType, a -> required.stream().anyMatch(r -> r.equals(a)));
    }

    private AccessType putOrNone(final AccessType expected, final Predicate<AccessType> condition) {
        return condition.test(expected) ? expected : AccessType.NONE;
    }

}
