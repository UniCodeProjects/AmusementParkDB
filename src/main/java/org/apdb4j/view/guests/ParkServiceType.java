package org.apdb4j.view.guests;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * All the possible types of a park service.
 */
public enum ParkServiceType {
    /**
     * The ride type.
     */
    RIDE("Ride"),
    /**
     * The exhibition type.
     */
    EXHIBITION("Exhibition"),
    /**
     * The shop type.
     */
    SHOP("Shop"),
    /**
     * The restaurant type.
     */
    RESTAURANT("Restaurant");

    @Getter(AccessLevel.PUBLIC)
    private final String name;

    ParkServiceType(final String name) {
        this.name = name;
    }
}
