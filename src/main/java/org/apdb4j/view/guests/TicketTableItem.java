package org.apdb4j.view.guests;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Year;

/**
 * The ticket representation used in {@link TicketsInfoController}.
 */
@ToString
@EqualsAndHashCode
public class TicketTableItem {

    private final org.apdb4j.view.staff.tableview.TicketTableItem ticket;

    /**
     * Default constructor.
     * @param ticketID the identifier of the ticket.
     * @param purchaseDate the date in which the ticket has been purchased.
     * @param validOn the date in which the ticket is valid.
     * @param validUntil the date until which the ticket is valid.
     * @param remainingEntrances the remaining entrances of the ticket.
     * @param year the year of the price list to which the ticket belongs.
     * @param category the ticket category (e.g. adults, senior...).
     */
    public TicketTableItem(final @NonNull String ticketID,
                           final @NonNull LocalDate purchaseDate,
                           final LocalDate validOn,
                           final LocalDate validUntil,
                           final int remainingEntrances,
                           final @NonNull Year year,
                           final @NonNull String category) {
        ticket = new org.apdb4j.view.staff.tableview.TicketTableItem(ticketID,
                purchaseDate,
                validOn,
                validUntil,
                remainingEntrances,
                "",
                year,
                null,
                category,
                null);
    }

    /**
     * Retrieves the ticket ID.
     * @return the ticket ID.
     */
    public @NonNull String getTicketID() {
        return ticket.getTicketID();
    }

    /**
     * Retrieves the ticket's purchase date.
     * @return the ticket's purchase date.
     */
    public @NonNull LocalDate getPurchaseDate() {
        return ticket.getPurchaseDate();
    }

    /**
     * Retrieves the date in which the ticket is valid.
     * @return the date in which the ticket is valid
     */
    public LocalDate getValidOn() {
        return ticket.getValidOn();
    }

    /**
     * Retrieves the date until which the ticket is valid.
     * @return the date until which the ticket is valid.
     */
    public LocalDate getValidUntil() {
        return ticket.getValidUntil();
    }

    /**
     * Retrieves the ticket's remaining entrances.
     * @return the ticket's remaining entrances.
     */
    public int getRemainingEntrances() {
        return ticket.getRemainingEntrances();
    }

    /**
     * Retrieves the year of the price list to which the ticket belongs.
     * @return the year of the price list to which the ticket belongs.
     */
    public @NonNull Year getYear() {
        return ticket.getYear();
    }

    /**
     * Retrieves the ticket's category.
     * @return the ticket's category.
     */
    public @NonNull String getCategory() {
        return ticket.getCategory();
    }
}
