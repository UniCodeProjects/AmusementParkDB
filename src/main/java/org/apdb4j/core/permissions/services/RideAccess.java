package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessSetting;

/**
 * The access related to rides.
 */
public interface RideAccess extends FacilityAccess {

    /**
     * The access permission for the {@code Intensity} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfRideIntensity();

    /**
     * The access permission for the {@code Duration} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfRideDuration();

    /**
     * The access permission for the {@code MaxSeats} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfRideMaxSeats();

    /**
     * The access permission for the {@code Height} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfRideHeightValues();

    /**
     * The access permission for the {@code Weight} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfRideWeightValues();

    /**
     * The access permission for the {@code Status} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfRideStatus();

    /**
     * The access permission for the {@code WaitTime} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfRideWaitTime();

}
