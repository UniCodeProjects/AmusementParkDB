package org.apdb4j.controllers.guests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/**
 * Enum that represents the sorting order.
 */
public enum Order {

    /**
     * The ascending sorting order.
     */
    ASCENDING("ascending"),
    /**
     * The descending sorting order.
     */
    DESCENDING("descending");

    @Getter(AccessLevel.PACKAGE)
    private final String name;

    Order(final @NonNull String name) {
        this.name = name;
    }
}
