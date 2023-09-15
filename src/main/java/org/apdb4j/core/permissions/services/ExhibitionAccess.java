package org.apdb4j.core.permissions.services;

/**
 * The access related to exhibitions.
 */
public interface ExhibitionAccess extends ParkServiceAccess {

    /**
     * The access permission for the {@code Date} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessExhibitionDate();

    /**
     * The access permission for the {@code Time} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessExhibitionTime();

    /**
     * The access permission for the {@code MaxSeats} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessExhibitionMaxSeats();

    /**
     * The access permission for the {@code SpectatorNum} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessExhibitionSpectatorNum();

}
