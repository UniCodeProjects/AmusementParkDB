package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessSettings;

/**
 * The access related to exhibitions.
 */
public interface ExhibitionAccess extends ParkServiceAccess {

    /**
     * The access permission for the {@code Date} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfExhibitionDate();

    /**
     * The access permission for the {@code Time} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfExhibitionTime();

    /**
     * The access permission for the {@code MaxSeats} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfExhibitionMaxSeats();

    /**
     * The access permission for the {@code SpectatorNum} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfExhibitionSpectatorNum();

}
