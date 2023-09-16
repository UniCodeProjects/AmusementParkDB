package org.apdb4j.core.permissions.facilities;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to exhibitions.
 */
public interface ExhibitionAccess extends AttractionAccess {

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
     * The access permission for the {@code SpectatorNum} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessExhibitionSpectatorNum();

}
