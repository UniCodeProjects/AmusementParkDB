package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.TicketTableItem;

import java.time.LocalDate;
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
     * @param <T> the {@code TicketTableItem} type
     * @return a collection of filtered ticket table items
     */
    <T extends TicketTableItem> Collection<T> filterByPurchaseDate(LocalDate date);

    /**
     * Filters tickets by punch date.
     * @param date the date
     * @param <T> the {@code TicketTableItem} type
     * @return a collection of filtered ticket table items
     */
    <T extends TicketTableItem> Collection<T> filterByPunchDate(LocalDate date);

    /**
     * Filters tickets by the 'single day' ticket type.
     * @param <T> the {@code TicketTableItem} type
     * @return a collection of filtered ticket table items
     */
    <T extends TicketTableItem> Collection<T> filterBySingleDayTicket();

    /**
     * Filters tickets by the 'season' ticket type.
     * @param <T> the {@code TicketTableItem} type
     * @return a collection of filtered ticket table items
     */
    <T extends TicketTableItem> Collection<T> filterBySeasonTicket();

}
