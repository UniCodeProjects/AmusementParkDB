package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSetting;

/**
 * The access related to park services.
 */
public interface ParkServiceAccess extends Access {

    /**
     * The access permission for the {@code ParkServiceID} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfParkServiceID();

    /**
     * The access permission for the {@code Name} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfParkServiceName();

    /**
     * The access permission for the {@code Type} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfParkServiceType();

    /**
     * The access permission for the {@code Description} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfParkServiceDescription();

}
