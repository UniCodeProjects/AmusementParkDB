package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.core.managers.TicketManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.TableItem;
import org.apdb4j.view.staff.tableview.TicketTypeTableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.TICKET_TYPES;

/**
 * Implementation of a ticket type controller.
 */
public class TicketTypeControllerImpl implements TicketTypeController {

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractData(searchQuery(DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final TicketTypeTableItem ticketType = (TicketTypeTableItem) item;
        final boolean queryResult1 = TicketManager.addNewPriceList(ticketType.getYear().getValue(), "");
        final boolean queryResult2 = TicketManager.addNewTicketType(ticketType.getType(),
                ticketType.getPrice(),
                ticketType.getYear().getValue(),
                ticketType.getCategory(),
                ticketType.getDuration(),
                "");
        if (!queryResult1 || !queryResult2) {
            throw new DataAccessException("Something went wrong while adding a new ticket type.");
        }
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        final TicketTypeTableItem ticketType = (TicketTypeTableItem) item;
        final boolean queryResult = TicketManager.updateTicketTypePrice(ticketType.getType(),
                ticketType.getCategory(),
                ticketType.getPrice(),
                "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while updating ticket type price: " + ticketType.getPrice());
        }
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String type) {
        return extractData(searchQuery(TICKET_TYPES.TYPE.containsIgnoreCase(type)));
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TicketTypeTableItem> Collection<T> filterByYear(final Year year) {
        return extractData(searchQuery(TICKET_TYPES.YEAR.eq(year.getValue())));
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TicketTypeTableItem> Collection<T> filterByCategory(final String category) {
        return extractData(searchQuery(TICKET_TYPES.CATEGORY.equalIgnoreCase(category)));
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public Collection<String> getAllTicketTypeCategories() {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(TICKET_TYPES.CATEGORY)
                        .from(TICKET_TYPES)
                        .fetch())
                .closeConnection()
                .getResultAsRecords().stream()
                .map(record -> record.get(TICKET_TYPES.CATEGORY))
                .toList();
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public Collection<String> getAllTicketTypes() {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(TICKET_TYPES.TYPE)
                        .from(TICKET_TYPES)
                        .fetch())
                .closeConnection()
                .getResultAsRecords().stream()
                .map(record -> record.get(TICKET_TYPES.TYPE))
                .toList();
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
                        .from(TICKET_TYPES)
                        .where(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> Collection<T> extractData(final Result<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new TicketTypeTableItem(record.get(TICKET_TYPES.TYPE),
                record.get(TICKET_TYPES.CATEGORY),
                Year.of(record.get(TICKET_TYPES.YEAR)),
                record.get(TICKET_TYPES.PRICE).doubleValue(),
                record.get(TICKET_TYPES.DURATION))));
        return data;
    }

}
