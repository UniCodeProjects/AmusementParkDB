package org.apdb4j.core.permissions.facilities;

import org.apdb4j.core.permissions.Permission;

/**
 * The permissions related to pictures.
 */
public interface PictureAccess extends Permission {

    /**
     * The access permission for the {@code Path} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessPicturePath();

}
