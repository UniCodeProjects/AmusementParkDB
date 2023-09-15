package org.apdb4j.core.permissions.services;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to park services.
 */
public interface ParkServiceAccess extends Access {

    /**
     * The access permission for the {@code ParkServiceID} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessParkServiceID();

    /**
     * The access permission for the {@code Name} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessParkServiceName();

    /**
     * The access permission for the {@code Type} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessParkServiceType();

    /**
     * The access permission for the {@code Description} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessParkServiceDescription();

}
