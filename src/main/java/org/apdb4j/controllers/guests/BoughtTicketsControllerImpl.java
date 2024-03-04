package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.core.managers.Manager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.guests.TicketTableItem;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.UInteger;

import java.time.LocalDate;
import java.time.Year;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.ATTRIBUTIONS;
import static org.apdb4j.db.Tables.PRICE_LISTS;
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
        return getDataAsTicketTableItem(new QueryBuilder().createConnection()
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
                .getResultAsRecords());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<TicketTableItem> filterByCategory(final @NonNull String category) {
        return getDataAsTicketTableItem(new QueryBuilder().createConnection()
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
                        .and(ATTRIBUTIONS.CATEGORY.equalIgnoreCase(category))
                        .fetch())
                .closeConnection().getResultAsRecords());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<TicketTableItem> getValidTickets() {
        return getDataAsTicketTableItem(new QueryBuilder().createConnection()
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
                        .and(TICKETS.REMAININGENTRANCES.greaterThan(UInteger.valueOf(0)))
                        .and(ticketType.equals(TicketType.SINGLE_DAY_TICKET)
                                ? TICKETS.VALIDON.greaterOrEqual(LocalDate.now())
                                : TICKETS.VALIDUNTIL.greaterOrEqual(LocalDate.now()))
                        .fetch())
                .closeConnection().getResultAsRecords());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<TicketTableItem> getExpiredTickets() {
        // TODO: perform a single query instead?
        final var result = getAllData();
        result.removeAll(getValidTickets());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Integer> getPriceListYears() {
        return Manager.viewAllInfoFromTable(PRICE_LISTS).stream().map(record -> record.get(PRICE_LISTS.YEAR)).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<TicketTableItem> filterByPriceListYear(final int year) {
        return getDataAsTicketTableItem(new QueryBuilder().createConnection()
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
                        .and(ATTRIBUTIONS.YEAR.eq(year))
                        .fetch())
                .closeConnection().getResultAsRecords());
    }

    private Collection<TicketTableItem> getDataAsTicketTableItem(final @NonNull Result<Record> data) {
        return data.stream().map(ticket -> new TicketTableItem(ticket.get(TICKETS.TICKETID),
                        ticket.get(TICKETS.PURCHASEDATE),
                        ticket.get(TICKETS.VALIDON),
                        ticket.get(TICKETS.VALIDUNTIL),
                        ticket.get(TICKETS.REMAININGENTRANCES).intValue(),
                        Year.of(ticket.get(ATTRIBUTIONS.YEAR)),
                        ticket.get(ATTRIBUTIONS.CATEGORY)))
                .collect(Collectors.toList());
    }
}
