package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessSetting;

/**
 * The access related to exhibitions.
 */
public interface ExhibitionAccess extends ParkServiceAccess {

    /**
     * The access permission for the {@code Date} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfExhibitionDate();

    /**
     * The access permission for the {@code Time} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfExhibitionTime();

    /**
     * The access permission for the {@code MaxSeats} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfExhibitionMaxSeats();

    /**
     * The access permission for the {@code SpectatorNum} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfExhibitionSpectatorNum();

}
