package org.apdb4j.core.permissions;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.Optional;
import java.util.Set;

/**
 * An immutable access setting with all {@code AccessTypes} set to {@code GLOBAL}.
 * @see AccessType
 */
public class ImmutableGlobalAccessSetting implements AccessSetting {

    private final AccessType.Read read;
    private final AccessType.Write write;

    /**
     * Creates an immutable access setting with all {@link AccessType}s set to {@code GLOBAL}.
     */
    public ImmutableGlobalAccessSetting() {
        this.read = AccessType.Read.GLOBAL;
        this.write = AccessType.Write.GLOBAL;
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
        return Pair.of(read, Set.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Pair<AccessType.Write, Set<Class<? extends Access>>> getWriteAccess() {
        return Pair.of(write, Set.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<TableField<Record, ?>> getAttribute() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Set<TableField<Record, ?>>> getAttributes() {
        return Optional.empty();
    }

}
