package org.apdb4j.core.permissions.tickets;

/**
 * The access related to season tickets.
 */
public interface SeasonTicketAccess extends TicketAccess {

    /**
     * The access permission for the {@code ValidUntil} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessTicketValidUntil();

    /**
     * The access permission for the {@code RemainingEntrances} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessTicketRemainingEntrances();

}
