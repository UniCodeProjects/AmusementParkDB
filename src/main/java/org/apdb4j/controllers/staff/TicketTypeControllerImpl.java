package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.TableItem;

import java.util.Collection;
import java.util.Optional;

import static org.apdb4j.db.Tables.TICKET_TYPES;

/**
 * Implementation of a ticket type controller.
 */
public class TicketTypeControllerImpl implements TicketTypeController {

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String s) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
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
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

}
