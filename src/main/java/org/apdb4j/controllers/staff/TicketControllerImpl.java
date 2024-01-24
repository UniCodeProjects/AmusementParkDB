package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.core.managers.TicketManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.TableItem;
import org.apdb4j.view.staff.tableview.TicketTableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.TableOnConditionStep;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.*;

/**
 * An implementation of a ticket controller.
 */
public class TicketControllerImpl implements TicketController {

    private final TableOnConditionStep<Record> joinedTable = TICKETS.join(ATTRIBUTIONS)
            .on(TICKETS.TICKETID.eq(ATTRIBUTIONS.TICKETID))
            .leftJoin(VALIDATIONS)
            .on(TICKETS.TICKETID.eq(VALIDATIONS.TICKETID))
            .join(TICKET_TYPES)
            .on(ATTRIBUTIONS.YEAR.eq(TICKET_TYPES.YEAR)
                    .and(ATTRIBUTIONS.TYPE.eq(TICKET_TYPES.TYPE))
                    .and(ATTRIBUTIONS.CATEGORY.eq(TICKET_TYPES.CATEGORY)));

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractTicketData(searchQuery(DSL.condition(true)));
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
                .queryAction(db -> db.update(joinedTable)
                        .set(TICKETS.VALIDON, ticket.getValidOn())
                        .set(TICKETS.VALIDUNTIL, ticket.getValidUntil())
                        .set(TICKETS.OWNERID, ticket.getOwnerID())
                        .set(ATTRIBUTIONS.CATEGORY, ticket.getCategory())
                        .execute())
                .closeConnection();
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TicketTableItem> Collection<T> filterByPurchaseDate(final LocalDate date) {
        return extractTicketData(searchQuery(TICKETS.PURCHASEDATE.eq(date)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TicketTableItem> Collection<T> filterByPunchDate(final LocalDate date) {
        return extractTicketData(searchQuery(VALIDATIONS.DATE.eq(date)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TicketTableItem> Collection<T> filterBySingleDayTicket() {
        return extractTicketData(searchQuery(ATTRIBUTIONS.TYPE.containsIgnoreCase("single day")));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TicketTableItem> Collection<T> filterBySeasonTicket() {
        return extractTicketData(searchQuery(ATTRIBUTIONS.TYPE.containsIgnoreCase("season")));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String ticketId) {
        return extractTicketData(searchQuery(TICKETS.TICKETID.containsIgnoreCase(ticketId)));
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
                        .from(joinedTable)
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
