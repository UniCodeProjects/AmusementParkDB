package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.TicketTypeTableItem;

import java.time.Year;
import java.util.Collection;

/**
 * An administration controller specifically used for ticket types.
 */
public interface TicketTypeController extends AdministrationController, Filterable {

    /**
     * Filters the ticket types by year.
     * @param year the year
     * @param <T> the {@code TicketTypeTableItem}
     * @return a collection containing the filtered ticket types
     */
    <T extends TicketTypeTableItem> Collection<T> filterByYear(Year year);

    /**
     * Filters the ticket types by category.
     * @param category the category
     * @param <T> the {@code TicketTypeTableItem}
     * @return a collection containing the filtered ticket types
     */
    <T extends TicketTypeTableItem> Collection<T> filterByCategory(String category);

    /**
     * Returns all the ticket type categories present in the DB.
     * @return a collection of ticket type categories
     */
    Collection<String> getAllTicketTypeCategories();

    /**
     * Returns all the ticket types present in the DB.
     * @return a collection of ticket types
     */
    Collection<String> getAllTicketTypes();

}
