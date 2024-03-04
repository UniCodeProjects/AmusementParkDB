package org.apdb4j.controllers.guests;

import lombok.NonNull;
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

    /**
     * Retrieves the tickets (of the type handled by this instance) bought by the currently logged user
     * whose category is the provided one.
     * @param category the category.
     * @return the tickets bought by the currently logged user whose category matches with the provided one.
     */
    Collection<TicketTableItem> filterByCategory(@NonNull String category);

    /**
     * Retrieves the tickets (of the type handled by this instance) bought by the currently logged user
     * that are still valid (meaning that allow at least one entrance and their date has not expired).
     * @return the tickets bought by the currently logged user that are still valid.
     */
    Collection<TicketTableItem> getValidTickets();

    /**
     * Retrieves the expired tickets (of the type handled by this instance) bought by the currently logged user.
     * A ticket is expired when it does not allow any entrance or when its date has expired.
     * @return the expired tickets bought by the currently logged user.
     */
    Collection<TicketTableItem> getExpiredTickets();

    /**
     * Retrieves all the price list years.
     * @return all the price list years.
     */
    Collection<Integer> getPriceListYears();

    /**
     * Retrieves all the tickets (of the type handled by this instance) bought by the currently logged user
     * whose price list year is the provided one.
     * @param year a price list year.
     * @return all the tickets bought by the currently logged user that have been bought in the year {@code year}.
     */
    Collection<TicketTableItem> filterByPriceListYear(int year);
}
