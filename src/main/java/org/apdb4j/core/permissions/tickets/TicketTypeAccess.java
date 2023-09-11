package org.apdb4j.core.permissions.tickets;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to ticket types.
 */
public interface TicketTypeAccess extends Access {

    /**
     * The access permission for the {@code Price} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessTicketTypePrice();

    /**
     * The access permission for the {@code Type} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessTicketType();

}
