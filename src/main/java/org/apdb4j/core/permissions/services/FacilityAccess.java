package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to facilities.
 */
public interface FacilityAccess extends ParkServiceAccess {

    /**
     * The access permission for the {@code OpeningAndClosingTimes} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfFacilityOpeningAndClosingTimes();

}