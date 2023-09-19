package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to park services.
 */
public interface ParkServiceAccess extends Access {

    /**
     * The access permission for the {@code ParkServiceID} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfParkServiceID();

    /**
     * The access permission for the {@code Name} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfParkServiceName();

    /**
     * The access permission for the {@code Type} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfParkServiceType();

    /**
     * The access permission for the {@code Description} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfParkServiceDescription();

}
