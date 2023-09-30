package org.apdb4j.core.managers;

import lombok.NonNull;

import java.time.LocalDate;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Tickets},
 * {@link org.apdb4j.db.tables.TicketTypes} and {@link org.apdb4j.db.tables.PriceLists} tables.
 */
public final class TicketManager {

    private TicketManager() {
    }

    /**
     * Performs the SQL query that adds a new ticket type.
     * @param type the ticket type.
     * @param price the price of the new ticket type.
     * @param year the year in which the provided ticket type is valid.
     * @param category the category of people to whom the ticket is addressed. If the value of this parameter is
     *                 not a valid category, the query will not be executed.
     * @param duration the number of times that a ticket of this ticket type can be validated.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void addNewTicketType(final @NonNull String type,
                                        final double price,
                                        final int year,
                                        final @NonNull String category,
                                        final int duration,
                                        final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that modifies the price of the provided ticket type from the current year's price list.
     * @param type the ticket type. If the value of this parameter is not a ticket type,
     *             the query will not be executed.
     * @param category the category of people to whom the ticket is addressed. If the value of this parameter
     *                 is not valid, the query will not be executed.
     * @param newPrice the new price of the provided ticket type.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void updateTicketTypePrice(final @NonNull String type, final @NonNull String category,
                                             final double newPrice,
                                             final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that adds a new ticket.
     * @param ticketID the identifier of the new ticket.
     * @param purchaseDate the date on which the ticket has been purchased.
     * @param validOn if the ticket is a single-day ticket, this parameter represents the date on which the ticket
     *                is valid and can be punched. Otherwise, if the ticket is a season ticket, this parameter
     *                has to be {@code null}.
     * @param validUntil if the ticket is a season ticket, this parameter represents the last day on which the ticket
     *                   valid. If the ticket is a single-day ticket, this parameter has to be {@code null}.
     * @param ownerID the identifier of the ticket owner. If the value of this parameter is not a guest identifier,
     *                the query will not be executed.
     * @param year the year on which the ticket is valid. If the value of this parameter is in the past, the query
     *             will not be executed.
     * @param type the ticket type. If the value of this parameter is not a ticket type,
     *             the query will not be executed.
     * @param category the category of the ticket type.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void addNewTicket(final @NonNull String ticketID,
                                    final @NonNull LocalDate purchaseDate,
                                    final LocalDate validOn,
                                    final LocalDate validUntil,
                                    final @NonNull String ownerID,
                                    final int year,
                                    final @NonNull String type,
                                    final @NonNull String category,
                                    final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that retrieves the week day of the provided month with the most visits.
     * @param month the month.
     * @param year the year.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return the date of the provided month with the most visits.
     */
    public static @NonNull LocalDate getWeekDayWithMostVisits(final int month, final int year, final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that adds a new tuple in the {@link org.apdb4j.db.tables.Validations} table.
     * Note that the validation date will be automatically initialised with the current date.
     * @param ticketID the identifier of the ticket to be punched. If the value of this parameter is not
     *                 a ticket identifier, the query will not be executed.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void punchTicket(final @NonNull String ticketID, final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that adds a new price list.
     * @param year the year in which the price list will be valid. Its value cannot be a year of the past,
     *             otherwise the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void addNewPriceList(final int year, final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

}
