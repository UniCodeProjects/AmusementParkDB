package org.apdb4j.core.permissions.services;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to pictures.
 */
public interface PictureAccess extends Access {

    /**
     * The access permission for the {@code Path} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessPicturePath();

}
