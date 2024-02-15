package org.apdb4j.controllers.guests;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * All the possible types of park services.
 */
public enum ParkServiceType {

    /**
     * The ride park service type.
     */
    RIDE("Ride"),
    /**
     * The exhibition park service type.
     */
    EXHIBITION("Exhibition"),
    /**
     * The shop park service type.
     */
    SHOP("Shop"),
    /**
     * The restaurant park service type.
     */
    RESTAURANT("Restaurant");

    @Getter(AccessLevel.PUBLIC)
    private final String name;

    ParkServiceType(final String name) {
        this.name = name;
    }
}
