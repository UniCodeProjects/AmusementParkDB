package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.exception.DataAccessException;

import java.util.Collection;
import java.util.Optional;

/**
 * Implementation of a ticket price list controller.
 */
public class TicketPriceListControllerImpl implements TicketPriceListController {

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

}
