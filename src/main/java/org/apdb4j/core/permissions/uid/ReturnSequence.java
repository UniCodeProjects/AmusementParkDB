package org.apdb4j.core.permissions.uid;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSetting;
import org.apdb4j.core.permissions.AccessType;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the UID section that contains the {@code AccessSetting} information.
 */
@Getter
@ToString
public final class ReturnSequence extends HashableSequence implements Sequence {

    private final String hash;
    private final Optional<TableField<Record, ?>> attribute;
    private final AccessType.Read read;
    private final AccessType.Write write;
    private final Set<Class<? extends Access>> readTargets;
    private final Set<Class<? extends Access>> writeTargets;

    /**
     * Creates a new sequence by collecting the contents of the given {@code AccessSetting}.
     * @param setting the given requiredPermission setting
     */
    public ReturnSequence(final @NonNull AccessSetting setting) {
        attribute = setting.getAttribute();
        read = setting.getReadAccess().getLeft();
        readTargets = setting.getReadAccess().getRight();
        write = setting.getWriteAccess().getLeft();
        writeTargets = setting.getWriteAccess().getRight();
        hash = generateHash(this, attribute.toString() + read + write + readTargets + writeTargets);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReturnSequence that = (ReturnSequence) o;
        return Objects.equals(hash, that.hash) && Objects.equals(attribute, that.attribute)
                && read == that.read && write == that.write && Objects.equals(readTargets, that.readTargets)
                && Objects.equals(writeTargets, that.writeTargets);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(hash, attribute, read, write, readTargets, writeTargets);
    }

}
