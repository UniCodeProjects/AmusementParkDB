package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to season tickets.
 */
public interface SeasonTicketAccess extends TicketAccess {

    /**
     * The access permission for the {@code ValidUntil} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfSeasonTicketValidUntil();

    /**
     * The access permission for the {@code RemainingEntrances} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfSeasonTicketDuration();

}
