package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.core.managers.Manager;
import org.apdb4j.util.QueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.TICKET_TYPES;

/**
 * Implementation of {@link TicketController}.
 */
public class TicketControllerImpl implements TicketController {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getTicketTypes() {
        return Manager.viewPartialInfoFromTable(TICKET_TYPES, "REMOVE ME", TICKET_TYPES.TYPE)
                .stream()
                .map(record -> record.get(TICKET_TYPES.TYPE))
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getCustomerCategories() {
        return Manager.viewPartialInfoFromTable(TICKET_TYPES, "REMOVE ME", TICKET_TYPES.CATEGORY)
                .stream()
                .map(record -> record.get(TICKET_TYPES.CATEGORY))
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPriceForTicket(final @NonNull String ticketType, final @NonNull String customerCategory) {
        return new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.select(TICKET_TYPES.PRICE)
                        .from(TICKET_TYPES)
                        .where(TICKET_TYPES.CATEGORY.eq(customerCategory)
                                .and(TICKET_TYPES.TYPE.eq(ticketType)))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, TICKET_TYPES.PRICE).doubleValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyTicket(final @NonNull String accountUsername,
                             final @NonNull String ticketType,
                             final @NonNull String customerCategory,
                             final int quantity) {
        throw new UnsupportedOperationException("Not implemented yet, decide how accountUsername is retrieved");
    }
}
