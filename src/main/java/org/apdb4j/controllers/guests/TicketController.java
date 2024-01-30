package org.apdb4j.controllers.guests;

import org.apdb4j.controllers.Controller;

import java.time.LocalDate;
import java.util.Collection;

/**
 * MVC controller that provides information on tickets.
 */
public interface TicketController extends Controller {

    /**
     * Retrieves all the ticket types' names.
     * All the names will be retrieved in lowercase.
     * @return the ticket types' names.
     */
    Collection<String> getTicketTypes();

    /**
     * Retrieves all the categories of customers.
     * All the categories' names will be retrieved in lowercase.
     * @return all the categories of customers.
     */
    Collection<String> getCustomerCategories();

    /**
     * Retrieves the price of the provided ticket.
     * @param ticketType the ticket type.
     * @param customerCategory the customer category.
     * @return the price for the provided ticket.
     */
    double getPriceForTicket(String ticketType, String customerCategory);

    /**
     * Allows the logged user to buy the provided ticket type, with the provided quantity.
     * @param ticketType the ticket type.
     * @param validOnOrValidUntil the date of validity of the ticket if the ticket is a
     *                            single day ticket; otherwise, if the ticket is
     *                            a season ticket, this parameter represents the
     *                            date until which the season ticket can be used.
     * @param customerCategory the customer category to which the ticket is addressed.
     * @param quantity the number of tickets that the user wants to buy.
     * @return {@code true} if the purchase is successful, {@code false} otherwise.
     */
    boolean buyTicket(String ticketType,
                      LocalDate validOnOrValidUntil,
                      String customerCategory,
                      int quantity);
}
