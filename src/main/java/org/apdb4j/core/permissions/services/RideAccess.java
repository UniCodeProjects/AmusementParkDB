package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to rides.
 */
public interface RideAccess extends FacilityAccess {

    /**
     * The access permission for the {@code Intensity} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfRideIntensity();

    /**
     * The access permission for the {@code Duration} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfRideDuration();

    /**
     * The access permission for the {@code MaxSeats} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfRideMaxSeats();

    /**
     * The access permission for the {@code Height} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfRideHeightValues();

    /**
     * The access permission for the {@code Weight} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfRideWeightValues();

    /**
     * The access permission for the {@code Status} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfRideStatus();

    /**
     * The access permission for the {@code WaitTime} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfRideWaitTime();

}
