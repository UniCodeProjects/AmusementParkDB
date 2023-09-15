package org.apdb4j.core.permissions.services;

/**
 * The access related to facilities.
 */
public interface FacilityAccess extends ParkServiceAccess {

    /**
     * The access permission for the {@code OpeningAndClosingTimes} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessFacilityOpeningAndClosingTimes();

}
