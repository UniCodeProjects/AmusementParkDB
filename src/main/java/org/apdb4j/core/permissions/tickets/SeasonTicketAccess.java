package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessSetting;

/**
 * The access related to season tickets.
 */
public interface SeasonTicketAccess extends TicketAccess {

    /**
     * The access permission for the {@code ValidUntil} attribute.
     * @return the type of access
     */
    @NonNull AccessSetting getAccessOfSeasonTicketValidUntil();

}
