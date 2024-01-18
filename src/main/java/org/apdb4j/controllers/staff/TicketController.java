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
     * Adds a new ticket type to the DB.
     * @param ticket the ticket
     * @param <T> the {@code TicketTableItem} type
     * @return the new ticket table item
     */
    <T extends TicketTableItem> T addTicketType(T ticket);

    /**
     * Edits a ticket type.
     * @param ticket the ticket
     * @param <T> the {@code TicketTableItem} type
     * @return the edited ticket table item
     */
    <T extends TicketTableItem> T editTicketType(T ticket);

    /**
     * Adds a ticket price list to the DB.
     * @param ticket the ticket
     * @param <T> the {@code TicketTableItem} type
     * @return the new ticket table item
     */
    <T extends TicketTableItem> T addPriceList(T ticket);

    /**
     * Edits a ticket price list.
     * @param ticket the ticket
     * @param <T> the {@code TicketTableItem} type
     * @return the edited ticket table item
     */
    <T extends TicketTableItem> T editPriceList(T ticket);

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
