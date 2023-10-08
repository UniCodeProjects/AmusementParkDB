package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSetting;

/**
 * The access related to ticket types.
 */
public interface TicketTypeAccess extends Access {

    /**
     * The access permission for the {@code Price} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfTicketTypePrice();

    /**
     * The access permission for the {@code Type} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfTicketTypeType();

    /**
     * The access permission for the {@code Target} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfTicketTypeTarget();

    /**
     * The access permission for the {@code Duration} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfTicketTypeDuration();

}
