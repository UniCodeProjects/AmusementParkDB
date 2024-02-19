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

    /**
     * Returns the MVC controller that handles park services of the provided type.
     * Each time this method is called a new instance is returned.
     * @param parkServiceType the park service type
     * @return the MVC controller for the provided park service type
     */
    // TODO: update switch statement with new controllers
    public static ParkServiceController getController(final ParkServiceType parkServiceType) {
        return switch (parkServiceType) {
            case RIDE -> new RideController();
            default -> throw new IllegalArgumentException("Controller for provided parkServiceType not implemented yet");
        };
    }
}
