package org.apdb4j.core.permissions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the access type of the database.<br>
 * It provides access to an attribute on three levels:
 * <ul>
 *     <li>{@code GLOBAL}: allows the user to access the attribute all across every tuple that contains it.</li>
 *     <li>{@code LOCAL}: allows the user to access only its own attribute.</li>
 *     <li>{@code NONE}: forbids the user to access the attribute on all access levels.</li>
 *     <li>{@code EMPTY}: the access type does not have any set value.</li>
 * </ul>
 */
public interface AccessType {

    /**
     * Represents the {@code Read AccessType}.
     */
    @Getter
    @AllArgsConstructor
    enum Read {
        /**
         * There is no value set for the read access.
         */
        EMPTY('E'),
        /**
         * The user has no access for this database's specific attribute on all levels.
         */
        NONE('N'),
        /**
         * The user can read this database's specific attribute on a local level.
         */
        LOCAL('r'),
        /**
         * The user can read this database's specific attribute on a global level.
         */
        GLOBAL('R');

        private final char type;

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return String.valueOf(type);
        }

    }

    /**
     * Represents the {@code Write AccessType}.
     */
    @Getter
    @AllArgsConstructor
    enum Write {
        /**
         * There is no value set for the write access.
         */
        EMPTY('E'),
        /**
         * The user has no access for this database's specific attribute on all levels.
         */
        NONE('N'),
        /**
         * The user can write on this database's specific attribute on a local level.
         */
        LOCAL('w'),
        /**
         * The user can write on this database's specific attribute on a global level.
         */
        GLOBAL('W');

        private final char type;

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return String.valueOf(type);
        }

    }

}
