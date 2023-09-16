package org.apdb4j.core.permissions.facilities;

import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to pictures.
 */
public interface PictureAccess extends Access {

    /**
     * The access permission for the {@code Path} attribute.
     * @return the type of access
     */
    AccessType canAccessPicturePath();

}
