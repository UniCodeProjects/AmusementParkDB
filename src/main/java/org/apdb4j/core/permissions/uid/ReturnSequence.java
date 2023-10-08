package org.apdb4j.core.permissions.uid;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSetting;
import org.apdb4j.core.permissions.AccessType;

import java.util.Set;

/**
 * Represents the UID section that contains the {@code AccessSetting} information.
 */
@Getter
@ToString
public final class ReturnSequence extends HashableSequence implements Sequence {

    private final String hash;
    private final AccessType.Read read;
    private final AccessType.Write write;
    private final Set<Class<? extends Access>> readTargets;
    private final Set<Class<? extends Access>> writeTargets;

    /**
     * Creates a new sequence by collecting the contents of the given {@code AccessSetting}.
     * @param setting the given access setting
     */
    public ReturnSequence(final @NonNull AccessSetting setting) {
        read = setting.getReadAccess().getLeft();
        readTargets = setting.getReadAccess().getRight();
        write = setting.getWriteAccess().getLeft();
        writeTargets = setting.getWriteAccess().getRight();
        hash = generateHash(this, read.toString() + write + readTargets + writeTargets);
    }

}
