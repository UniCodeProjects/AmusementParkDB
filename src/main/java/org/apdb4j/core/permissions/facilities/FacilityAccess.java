package org.apdb4j.core.permissions.facilities;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to facilities.
 */
public interface FacilityAccess extends Access {

    /**
     * The access permission for the {@code FacilityID} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessFacilityID();

    /**
     * The access permission for the {@code Name} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessFacilityName();

    /**
     * The access permission for the {@code OpeningAndClosingTimes} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessFacilityOpeningAndClosingTimes();

    /**
     * The access permission for the {@code Type} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessFacilityType();

}
