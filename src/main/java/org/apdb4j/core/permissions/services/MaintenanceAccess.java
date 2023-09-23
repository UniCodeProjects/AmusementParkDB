package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to maintenances.
 */
public interface MaintenanceAccess extends Access {

    /**
     * The access permission for the {@code Price} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfMaintenancePrice();

    /**
     * The access permission for the {@code Description} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfMaintenanceDescription();

    /**
     * The access permission for the {@code Date} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfMaintenanceDate();

}
