package org.apdb4j.core.permissions.facilities;

import org.apdb4j.core.permissions.Permission;

/**
 * The permissions related to maintenances.
 */
public interface MaintenanceAccess extends Permission {

    /**
     * The access permission for the {@code Price} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessMaintenancePrice();

    /**
     * The access permission for the {@code Description} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessMaintenanceDescription();

    /**
     * The access permission for the {@code Date} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessMaintenanceDate();

}
