package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to single day tickets.
 */
public interface SingleDayTicketAccess extends TicketAccess {

    /**
     * The access permission for the {@code ValidOn} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessTicketValidOn();

}
