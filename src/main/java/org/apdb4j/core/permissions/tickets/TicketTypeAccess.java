package org.apdb4j.core.permissions.tickets;

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
    AccessType canAccessTicketTypePrice();

    /**
     * The access permission for the {@code Type} attribute.
     * @return the type of access
     */
    AccessType canAccessTicketType();

}
