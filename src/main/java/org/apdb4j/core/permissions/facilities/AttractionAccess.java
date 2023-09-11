package org.apdb4j.core.permissions.facilities;

/**
 * The access related to attractions.
 */
public interface AttractionAccess extends FacilityAccess {

    /**
     * The access permission for the {@code MaxSeats} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessAttractionMaxSeats();

    /**
     * The access permission for the {@code Description} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessAttractionDescription();

}
