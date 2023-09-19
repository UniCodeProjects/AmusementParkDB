package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to tickets.
 */
public interface TicketAccess extends Access {

    /**
     * The access permission for the {@code TicketID} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfTicketID();

    /**
     * The access permission for the {@code PurchaseDate} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfTicketPurchaseDate();

    /**
     * The access permission for the {@code PunchDate} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfTicketPunchDate();

}
