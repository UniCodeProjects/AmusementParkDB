package org.apdb4j.controllers.guests;

import lombok.AccessLevel;
import lombok.Getter;

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

    Order(final String name) {
        this.name = name;
    }
}
