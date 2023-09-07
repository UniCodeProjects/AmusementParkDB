package org.apdb4j.core.permissions.facilities;

/**
 * The permissions related to rides.
 */
public interface RideAccess extends AttractionAccess {

    /**
     * The access permission for the {@code Intensity} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessRideIntensity();

    /**
     * The access permission for the {@code Duration} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessRideDuration();

    /**
     * The access permission for the {@code Height} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessRideHeightValues();

    /**
     * The access permission for the {@code Weight} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessRideWeightValues();

    /**
     * The access permission for the {@code Status} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessRideStatus();

    /**
     * The access permission for the {@code WaitTime} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessRideWaitTime();

}
