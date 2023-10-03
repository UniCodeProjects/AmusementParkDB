package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessSettings;

/**
 * The access related to season tickets.
 */
public interface SeasonTicketAccess extends TicketAccess {

    /**
     * The access permission for the {@code ValidUntil} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfSeasonTicketValidUntil();

    /**
     * The access permission for the {@code RemainingEntrances} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfSeasonTicketDuration();

}
