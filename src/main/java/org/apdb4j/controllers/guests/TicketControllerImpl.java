package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.core.managers.Manager;
import org.apdb4j.core.managers.TicketManager;
import org.apdb4j.util.IDGenerationUtils;
import org.apdb4j.util.QueryBuilder;

import java.time.LocalDate;
import java.time.Year;
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
                                .and(TICKET_TYPES.TYPE.eq(ticketType))
                                .and(TICKET_TYPES.YEAR.eq(Year.now().getValue())))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, TICKET_TYPES.PRICE).doubleValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyTicket(final @NonNull String ticketType,
                             final @NonNull LocalDate validOnOrValidUntil,
                             final @NonNull String customerCategory,
                             final int quantity) {
        for (int i = 0; i < quantity; i++) {
            final var ticketAdded = TicketManager.addNewTicket(IDGenerationUtils.generateTicketID(),
                    "Single day ticket".equals(ticketType) ? validOnOrValidUntil : null,
                    "Season ticket".equals(ticketType) ? validOnOrValidUntil : null,
                    SessionManager.getSessionManager().getSession().personID(),
                    customerCategory,
                    "REMOVE ME");
            if (!ticketAdded) {
                return false;
            }
        }
        return true;
    }
}
