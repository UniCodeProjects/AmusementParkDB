package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apdb4j.core.managers.TicketManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.TableItem;
import org.apdb4j.view.staff.tableview.TicketTableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.*;

/**
 * An implementation of a ticket controller.
 */
public class TicketControllerImpl implements TicketController {

    private final Condition withMostRecentValidation = VALIDATIONS.DATE.eq(
            DSL.select(DSL.max(VALIDATIONS.DATE))
                    .from(VALIDATIONS)
                    .where(TICKETS.TICKETID.eq(VALIDATIONS.TICKETID))
    );

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractTicketData(searchQuery(withMostRecentValidation));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final TicketTableItem ticket = (TicketTableItem) item;
        final boolean queryResult = TicketManager.addNewTicket(ticket.getValidOn(),
                ticket.getValidUntil(),
                ticket.getOwnerID(),
                ticket.getCategory(),
                "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while adding a new ticket.");
        }
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        final TicketTableItem ticket = (TicketTableItem) item;
        new QueryBuilder().createConnection()
                .queryAction(db -> db.update(TICKETS.join(ATTRIBUTIONS).on(TICKETS.TICKETID.eq(ATTRIBUTIONS.TICKETID)))
                        .set(TICKETS.PURCHASEDATE, ticket.getPurchaseDate())
                        .set(TICKETS.VALIDON, ticket.getValidOn())
                        .set(TICKETS.VALIDUNTIL, ticket.getValidUntil())
                        .set(TICKETS.OWNERID, ticket.getOwnerID())
                        .set(ATTRIBUTIONS.CATEGORY, ticket.getCategory())
                        .where(TICKETS.TICKETID.eq(ticket.getTicketID()))
                        .execute())
                .closeConnection();
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if the query fails
     */
    @Override
    public TicketTableItem punchTicket(final TicketTableItem ticket) {
        final boolean queryResult = TicketManager.punchTicket(ticket.getTicketID(), "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while punching the ticket " + ticket.getTicketID());
        }
        return (TicketTableItem) extractTicketData(searchQuery(TICKETS.TICKETID.eq(ticket.getTicketID()))).stream()
                .findFirst()
                .orElseThrow(() -> new DataAccessException("Could not find " + ticket.getTicketID() + " in the database."));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public Collection<TicketTableItem> filterByPurchaseDate(final LocalDate date) {
        return extractTicketData(searchQuery(withMostRecentValidation.and(TICKETS.PURCHASEDATE.eq(date))));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public Collection<TicketTableItem> filterByPunchDate(final LocalDate date) {
        return extractTicketData(searchQuery(withMostRecentValidation.and(VALIDATIONS.DATE.eq(date))));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public Collection<TicketTableItem> filterBySingleDayTicket() {
        return extractTicketData(searchQuery(withMostRecentValidation.and(ATTRIBUTIONS.TYPE.containsIgnoreCase("single day"))));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public Collection<TicketTableItem> filterBySeasonTicket() {
        return extractTicketData(searchQuery(withMostRecentValidation.and(ATTRIBUTIONS.TYPE.containsIgnoreCase("season"))));
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if the query fails
     */
    @Override
    public Collection<ImmutablePair<LocalDate, Integer>> getDayWithMostVisits(final YearMonth month) {
        return TicketManager.getDayWithMostVisits(month, "");
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String ticketId) {
        return extractTicketData(searchQuery(withMostRecentValidation.and(TICKETS.TICKETID.containsIgnoreCase(ticketId))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

    private Result<Record> searchQuery(final Condition condition) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select()
                        .from(TICKETS.join(ATTRIBUTIONS)
                                .on(TICKETS.TICKETID.eq(ATTRIBUTIONS.TICKETID))
                                .leftJoin(VALIDATIONS)
                                .on(TICKETS.TICKETID.eq(VALIDATIONS.TICKETID)))
                        .where(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> Collection<T> extractTicketData(final Result<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new TicketTableItem(record.get(TICKETS.TICKETID),
                record.get(TICKETS.PURCHASEDATE),
                record.get(TICKETS.VALIDON),
                record.get(TICKETS.VALIDUNTIL),
                record.get(TICKETS.REMAININGENTRANCES).intValue(),
                record.get(TICKETS.OWNERID),
                Year.of(record.get(ATTRIBUTIONS.YEAR)),
                record.get(ATTRIBUTIONS.TYPE),
                record.get(ATTRIBUTIONS.CATEGORY),
                record.get(VALIDATIONS.DATE))));
        return data;
    }

}
