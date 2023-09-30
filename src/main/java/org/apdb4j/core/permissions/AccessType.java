package org.apdb4j.core.permissions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum that represents the access type of the database.<br>
 * It provides access to an attribute on two levels:
 * <ul>
 *     <li>{@code GLOBAL}: allows the user to access the attribute all across every tuple that contains it.</li>
 *     <li>{@code LOCAL}: allows the user to access only its own attribute.</li>
 * </ul>
 */
@Getter
@AllArgsConstructor
public enum AccessType {
    /**
     * The user can read this database's specific attribute on a global level.
     */
    GLOBAL_READ('R'),
    /**
     * The user can read this database's specific attribute on a local level.
     */
    LOCAL_READ('r'),
    /**
     * The user can write on this database's specific attribute on a global level.
     */
    GLOBAL_WRITE('W'),
    /**
     * The user can write on this database's specific attribute on a local level.
     */
    LOCAL_WRITE('w'),
    /**
     * The user can read and write on this database's specific attribute on a global level.
     */
    GLOBAL_ALL('A'),
    /**
     * The user can read and write on this database's specific attribute on a local level.
     */
    LOCAL_ALL('a'),
    /**
     * The user has no access for this database's specific attribute on a global level.
     */
    NONE('N');

    private final char type;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(type);
    }

}
