package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to exhibitions.
 */
public interface ExhibitionAccess extends ParkServiceAccess {

    /**
     * The access permission for the {@code Date} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessExhibitionDate();

    /**
     * The access permission for the {@code Time} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessExhibitionTime();

    /**
     * The access permission for the {@code MaxSeats} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessExhibitionMaxSeats();

    /**
     * The access permission for the {@code SpectatorNum} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessExhibitionSpectatorNum();

}
