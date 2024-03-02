package org.apdb4j.controllers.guests;

import org.apdb4j.view.guests.TicketTableItem;

import java.util.Collection;

/**
 * MVC controller that handles all the tickets bought by the currently logged user (if it is a guest).
 */
public interface BoughtTicketsController {

    /**
     * Retrieves all the information about the tickets bought by the currently logged user whose type
     * is the one handled by this controller (e.g. if the controller handles single day tickets,
     * then all the information about the single day tickets bought so far will be retrieved).
     * @return all the information about the tickets bought by the currently logged user whose type
     * is the one handled by this controller.
     * @see BoughtTicketsControllerImpl#BoughtTicketsControllerImpl(TicketType)
     */
    Collection<TicketTableItem> getAllData();
}
