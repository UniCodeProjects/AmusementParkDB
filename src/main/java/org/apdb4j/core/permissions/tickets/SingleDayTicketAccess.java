package org.apdb4j.core.permissions.tickets;

/**
 * The access related to single day tickets.
 */
public interface SingleDayTicketAccess extends TicketAccess {

    /**
     * The access permission for the {@code ValidOn} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessTicketValidOn();

}
