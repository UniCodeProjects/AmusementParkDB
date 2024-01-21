package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;

import java.util.Collection;

/**
 * An administration controller specifically used for ticket types.
 */
public interface TicketTypeController extends AdministrationController, Filterable {

    /**
     * Returns all the ticket type categories present in the DB.
     * @return a collection of ticket type categories.
     */
    Collection<String> getAllTicketTypeCategories();

}
