package org.apdb4j.core.permissions;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

/**
 * Represents the set values for the access types of {@code READ},
 * {@code WRITE} and {@code NONE}.
 * @see AccessType
 */
public interface AccessSettings {

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
