package org.apdb4j.core.permissions.facilities;

import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to attractions.
 */
public interface AttractionAccess extends FacilityAccess {

    /**
     * The access permission for the {@code MaxSeats} attribute.
     * @return the type of access
     */
    AccessType canAccessAttractionMaxSeats();

    /**
     * The access permission for the {@code Description} attribute.
     * @return the type of access
     */
    AccessType canAccessAttractionDescription();

}
