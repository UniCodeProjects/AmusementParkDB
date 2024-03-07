package org.apdb4j.controllers.guests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/**
 * Enum that represents all the possible ticket types.
 */
public enum TicketType {
    /**
     * The value that represents a ticket that can be validated only once.
     */
    SINGLE_DAY_TICKET("single day ticket"),
    /**
     * The value that represents a ticket that can be validated multiple times.
     */
    SEASON_TICKET("season ticket");

    @Getter(AccessLevel.PUBLIC)
    private final String name;

    TicketType(final @NonNull String name) {
        this.name = name;
    }
}
