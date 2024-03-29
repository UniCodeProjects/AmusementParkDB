package org.apdb4j.controllers.staff;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.TicketTableItem;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;

/**
 * An administration controller specifically used for tickets.
 */
public interface TicketController extends AdministrationController, Filterable {

    /**
     * Punches the given ticket.
     * @param ticket the ticket
     * @return the punched ticket
     */
    TicketTableItem punchTicket(TicketTableItem ticket);

    /**
     * Filters tickets by purchase date.
     * @param date the date
     * @return a collection of filtered ticket table items
     */
    Collection<TicketTableItem> filterByPurchaseDate(LocalDate date);

    /**
     * Filters tickets by punch date.
     * @param date the date
     * @return a collection of filtered ticket table items
     */
    Collection<TicketTableItem> filterByPunchDate(LocalDate date);

    /**
     * Filters tickets by the 'single day' ticket type.
     * @return a collection of filtered ticket table items
     */
    Collection<TicketTableItem> filterBySingleDayTicket();

    /**
     * Filters tickets by the 'season' ticket type.
     * @return a collection of filtered ticket table items
     */
    Collection<TicketTableItem> filterBySeasonTicket();

    /**
     * Returns the day(s) with the most visits.
     * @param month the month used to filter the results
     * @return a collection of {@code [date, visits]} pairs
     */
    Collection<ImmutablePair<LocalDate, Integer>> getDayWithMostVisits(YearMonth month);

}
