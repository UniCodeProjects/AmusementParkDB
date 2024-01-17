package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.TableItem;
import org.apdb4j.view.staff.tableview.TicketTableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

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
        return null;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        return null;
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
                        .from(TICKETS
                                .join(ATTRIBUTIONS)
                                .on(TICKETS.TICKETID.eq(ATTRIBUTIONS.TICKETID))
                                .join(VALIDATIONS)
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
