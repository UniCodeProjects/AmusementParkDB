package org.apdb4j.core.permissions.facilities;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to facilities.
 */
public interface FacilityAccess extends Access {

    /**
     * The access permission for the {@code FacilityID} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessFacilityID();

    /**
     * The access permission for the {@code Name} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessFacilityName();

    /**
     * The access permission for the {@code OpeningAndClosingTimes} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessFacilityOpeningAndClosingTimes();

    /**
     * The access permission for the {@code Type} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessFacilityType();

}
