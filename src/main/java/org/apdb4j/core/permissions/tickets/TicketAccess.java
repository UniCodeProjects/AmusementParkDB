package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSetting;

/**
 * The access related to tickets.
 */
public interface TicketAccess extends Access {

    /**
     * The access permission for the {@code TicketID} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfTicketID();

    /**
     * The access permission for the {@code PurchaseDate} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfTicketPurchaseDate();

    /**
     * The access permission for the {@code PunchDate} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfTicketPunchDate();

    /**
     * The access permission for the {@code RemainingEntrances} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfTicketRemainingEntrances();

}
