package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.util.QueryBuilder;
import org.jooq.impl.DSL;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.GUESTS;
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
    public @NonNull Collection<Pair<LocalDate, Integer>> getNumberOfBoughtTickets() {
        final String loggedGuestID = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(GUESTS.GUESTID)
                        .from(GUESTS)
                        .where(GUESTS.EMAIL.eq(SessionManager.getSessionManager().getSession().email()))
                        .fetch())
                .closeConnection().getResultAsRecords().getValue(0, GUESTS.GUESTID);
        final var datesWithTickets = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(DSL.count(), ticketType.equals(TicketType.SINGLE_DAY_TICKET)
                                ? TICKETS.VALIDON : TICKETS.VALIDUNTIL)
                        .from(TICKETS)
                        .where(TICKETS.OWNERID.eq(loggedGuestID))
                        .and(ticketType.equals(TicketType.SINGLE_DAY_TICKET)
                                ? TICKETS.VALIDON.isNotNull() : TICKETS.VALIDUNTIL.isNotNull())
                        .groupBy(ticketType.equals(TicketType.SINGLE_DAY_TICKET)
                                ? TICKETS.VALIDON : TICKETS.VALIDUNTIL)
                        .fetch())
                .closeConnection().getResultAsRecords();
        return datesWithTickets.stream().map(record -> new ImmutablePair<>((LocalDate) record.get(1), (Integer) record.get(0)))
                .collect(Collectors.toList());
    }
}
