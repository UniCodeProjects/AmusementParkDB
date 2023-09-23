package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to ticket types.
 */
public interface TicketTypeAccess extends Access {

    /**
     * The access permission for the {@code Price} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfTicketTypePrice();

    /**
     * The access permission for the {@code Type} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfTicketTypeType();

    /**
     * The access permission for the {@code Target} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfTicketTypeTarget();

    /**
     * The access permission for the {@code Duration} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfTicketTypeDuration();

}
