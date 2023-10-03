package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSettings;

/**
 * The access related to pictures.
 */
public interface PictureAccess extends Access {

    /**
     * The access permission for the {@code Path} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfPicturePath();

}
