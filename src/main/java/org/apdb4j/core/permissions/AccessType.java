package org.apdb4j.core.permissions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum that represents the access type of the database.
 */
@Getter
@AllArgsConstructor
public enum AccessType {
    /**
     * The user can read this database's specific attribute.
     */
    READ('R'),
    /**
     * The user can write on this database's specific attribute.
     */
    WRITE('W'),
    /**
     * The user can read and write on this database's specific attribute.
     */
    ALL('A'),
    /**
     * The user has no access for this database's specific attribute.
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
