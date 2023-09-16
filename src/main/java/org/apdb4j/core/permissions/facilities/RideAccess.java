package org.apdb4j.core.permissions.facilities;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to rides.
 */
public interface RideAccess extends AttractionAccess {

    /**
     * The access permission for the {@code Intensity} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessRideIntensity();

    /**
     * The access permission for the {@code Duration} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessRideDuration();

    /**
     * The access permission for the {@code Height} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessRideHeightValues();

    /**
     * The access permission for the {@code Weight} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessRideWeightValues();

    /**
     * The access permission for the {@code Status} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessRideStatus();

    /**
     * The access permission for the {@code WaitTime} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessRideWaitTime();

}
