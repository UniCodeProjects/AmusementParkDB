package org.apdb4j.core.permissions.tickets;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to tickets.
 */
public interface TicketAccess extends Access {

    /**
     * The access permission for the {@code TicketID} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessTicketID();

    /**
     * The access permission for the {@code PurchaseDate} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessTicketPurchaseDate();

    /**
     * The access permission for the {@code PunchDate} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessTicketPunchDate();

}
