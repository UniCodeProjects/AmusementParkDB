package org.apdb4j.controllers.guests;

import org.apdb4j.controllers.Controller;

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
     * Allows the user whose account has the provided username to buy the provided ticket.
     * @param accountUsername the username of the account that wants to buy the provided ticket.
     * @param ticketType the ticket type.
     * @param customerCategory the customer category to which the ticket is addressed.
     * @param quantity the number of tickets that the user wants to buy.
     * @return {@code true} if the purchase is successful, {@code false} otherwise.
     */
    boolean buyTicket(String accountUsername, String ticketType, String customerCategory, int quantity);
}
