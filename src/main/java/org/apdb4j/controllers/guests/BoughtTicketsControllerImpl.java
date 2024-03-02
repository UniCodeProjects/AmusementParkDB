package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.guests.TicketTableItem;

import java.time.Year;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.ATTRIBUTIONS;
import static org.apdb4j.db.Tables.TICKETS;

/**
 * Implementation of {@link BoughtTicketsController}.
 */
public class BoughtTicketsControllerImpl implements BoughtTicketsController {

    private final TicketType ticketType;

    /**
     * Creates a new instance of this class that will handle only tickets of the provided type.
     * @param ticketType the ticket type that this instance will handle.
     */
    public BoughtTicketsControllerImpl(final @NonNull TicketType ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<TicketTableItem> getAllData() {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(TICKETS.TICKETID,
                        TICKETS.PURCHASEDATE,
                        TICKETS.VALIDON,
                        TICKETS.VALIDUNTIL,
                        TICKETS.REMAININGENTRANCES,
                        ATTRIBUTIONS.YEAR,
                        ATTRIBUTIONS.CATEGORY)
                        .from(TICKETS).join(ATTRIBUTIONS).on(TICKETS.TICKETID.eq(ATTRIBUTIONS.TICKETID))
                        .where(TICKETS.OWNERID.eq(SessionManager.getSessionManager().getSession().personID()))
                        .and(ATTRIBUTIONS.TYPE.equalIgnoreCase(ticketType.getName()))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .stream()
                .map(ticket -> new TicketTableItem(ticket.get(TICKETS.TICKETID),
                        ticket.get(TICKETS.PURCHASEDATE),
                        ticket.get(TICKETS.VALIDON),
                        ticket.get(TICKETS.VALIDUNTIL),
                        ticket.get(TICKETS.REMAININGENTRANCES).intValue(),
                        Year.of(ticket.get(ATTRIBUTIONS.YEAR)),
                        ticket.get(ATTRIBUTIONS.CATEGORY)))
                .collect(Collectors.toList());
    }
}
