package org.apdb4j.controllers.guests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.jooq.SortOrder;

/**
 * Enum that represents the sorting order.
 */
public enum Order {

    /**
     * The ascending sorting order.
     */
    ASCENDING("ascending", SortOrder.ASC),
    /**
     * The descending sorting order.
     */
    DESCENDING("descending", SortOrder.DESC);

    @Getter(AccessLevel.PACKAGE)
    private final String name;
    @Getter(AccessLevel.PACKAGE)
    private final SortOrder order;

    Order(final @NonNull String name, final @NonNull SortOrder order) {
        this.name = name;
        this.order = order;
    }
}
