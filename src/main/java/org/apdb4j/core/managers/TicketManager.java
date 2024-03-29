package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static org.apdb4j.db.Tables.*;

/**
 * Contains all the SQL queries that are related to the ticket management.
 */
public final class TicketManager {

    private TicketManager() {
    }

    /**
     * Performs the SQL query that adds a new ticket type.
     * @param type the ticket type.
     * @param price the price of the new ticket type.
     * @param year the year in which the provided ticket type is valid. The year cannot be in the past,
     *             otherwise the query will not be executed.
     * @param category the category of people to whom the ticket is addressed. If the value of this parameter is
     *                 not a valid category, the query will not be executed.
     * @param duration the number of times that a ticket of this type can be validated.
     * @return {@code true} if the insertion is successful, {@code false} otherwise.
     */
    public static boolean addNewTicketType(final @NonNull String type,
                                           final double price,
                                           final int year,
                                           final @NonNull String category,
                                           final int duration) {
        if (year < LocalDate.now().getYear()) {
            return false;
        } else {
            return new QueryBuilder().createConnection()
                    .queryAction(db -> db.insertInto(TICKET_TYPES)
                            .values(year, price, type, category, duration)
                            .execute())
                    .closeConnection()
                    .getResultAsInt() == 1;
        }
    }

    /**
     * Performs the SQL query that modifies the price of the provided ticket type from the current year's price list.
     * @param type the ticket type. If the value of this parameter is not a ticket type,
     *             the query will not be executed.
     * @param category the category of people to whom the ticket is addressed. If the value of this parameter
     *                 is not valid, the query will not be executed.
     * @param newPrice the new price of the provided ticket type.
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    public static boolean updateTicketTypePrice(final @NonNull String type, final @NonNull String category,
                                                final double newPrice) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(TICKET_TYPES)
                        .set(TICKET_TYPES.PRICE, BigDecimal.valueOf(newPrice))
                        .where(TICKET_TYPES.YEAR.eq(LocalDate.now().getYear()))
                        .and(TICKET_TYPES.CATEGORY.eq(category))
                        .and(TICKET_TYPES.TYPE.eq(type))
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that adds a new ticket.
     * @param ticketID the identifier of the new ticket.
     * @param validOn if the ticket is a single-day ticket, this parameter represents the date on which the ticket
     *                is valid and can be punched. Otherwise, if the ticket is a season ticket, this parameter
     *                has to be {@code null}.
     * @param validUntil if the ticket is a season ticket, this parameter represents the last day on which the ticket
     *                   valid. If the ticket is a single-day ticket, this parameter has to be {@code null}.
     * @param ownerID the identifier of the ticket owner. If the value of this parameter is not a guest identifier,
     *                the query will not be executed.
     * @param category the category of the ticket type.
     * @return {@code true} if the insertion is successful, {@code false} otherwise.
     */
    public static boolean addNewTicket(final @NonNull String ticketID,
                                       final LocalDate validOn,
                                       final LocalDate validUntil,
                                       final @NonNull String ownerID,
                                       final @NonNull String category) {
        if (!isGuestId(ownerID)) {
            return false;
        } else {
            final String type = Objects.isNull(validOn) ? "Season ticket" : "Single day ticket";
            final LocalDateTime purchaseDateTime = LocalDateTime.now();
            final int year = purchaseDateTime.getYear();
            final int initialRemainingEntrances = new QueryBuilder().createConnection()
                    .queryAction(db -> db.select(TICKET_TYPES.DURATION)
                            .from(TICKET_TYPES)
                            .where(TICKET_TYPES.YEAR.eq(year))
                            .and(TICKET_TYPES.TYPE.eq(type))
                            .and(TICKET_TYPES.CATEGORY.eq(category))
                            .fetchOne(0, int.class))
                    .closeConnection()
                    .getResultAsInt();
            try {
                new QueryBuilder().createConnection()
                        .queryAction(db -> {
                            db.transaction(configuration -> {
                                final var dslContext = configuration.dsl();
                                dslContext.insertInto(TICKETS)
                                        .values(ticketID,
                                                purchaseDateTime.toLocalDate(),
                                                validOn,
                                                validUntil,
                                                initialRemainingEntrances,
                                                ownerID)
                                        .execute();
                                dslContext.insertInto(ATTRIBUTIONS)
                                        .values(ticketID, year, type, category)
                                        .execute();
                            });
                            return 1;
                        }).closeConnection();
            } catch (final DataAccessException e) {
                return false;
            }
            return true;
        }
    }

    /**
     * Performs the SQL query that retrieves the day(s) of the provided month with the most visits, paired with its number
     * of visits.
     *
     * @param month   the month.
     * @return the day(s) of the provided month with the most visits, paired with its number of visits.
     */
    public static @NonNull Collection<ImmutablePair<LocalDate, Integer>> getDayWithMostVisits(final @NonNull YearMonth month) {
        final String countColumnName = "NumVisitors";
        final Result<Record> daysAndVisits = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(VALIDATIONS.DATE, DSL.count().as(countColumnName))
                        .from(VALIDATIONS)
                        .where(VALIDATIONS.DATE.between(LocalDate.of(month.getYear(), month.getMonth(), 1),
                                LocalDate.of(month.getYear(), month.getMonth(), month.lengthOfMonth())))
                        .groupBy(VALIDATIONS.DATE)
                        .orderBy(DSL.count().desc())
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        if (daysAndVisits.isEmpty()) {
            return Collections.emptyList();
        } else {
            final var maxVisits = daysAndVisits.get(0).get(countColumnName);
            return daysAndVisits.stream()
                    .filter(dayAndVisit -> dayAndVisit.get(countColumnName) == maxVisits)
                    .map(dayAndVisit -> new ImmutablePair<>(dayAndVisit.get(VALIDATIONS.DATE),
                            (Integer) dayAndVisit.get(countColumnName)))
                    .toList();
        }
    }

    /**
     * Performs the SQL query that allows to punch a ticket.
     * Note that the validation date will be automatically initialised with the current date.
     * @param ticketID the identifier of the ticket to be punched. If <ul>
     *                 <li>the value of this parameter is not a ticket identifier, or</li>
     *                 <li>the ticket with the provided identifier is not valid in the current date, or</li>
     *                 <li>the ticket with the provided identifier cannot be punched one more time</li>
     *                 </ul>
     *                 the query will not be executed.
     * @return {@code true} if the insertion is successful.
     */
    public static boolean punchTicket(final @NonNull String ticketID) {
        final LocalDate punchDate = LocalDate.now();
        if (!isTicketID(ticketID) || !isTicketValid(punchDate, ticketID) || getRemainingEntrances(ticketID) == 0) {
            return false;
        } else {
            new QueryBuilder().createConnection()
                    .queryAction(db -> {
                        db.transaction(configuration -> {
                            final var dslContext = configuration.dsl();
                            dslContext.update(TICKETS)
                                    .set(TICKETS.REMAININGENTRANCES, TICKETS.REMAININGENTRANCES.minus(1))
                                    .where(TICKETS.TICKETID.eq(ticketID))
                                    .execute();
                            dslContext.insertInto(PUNCH_DATES)
                                    .values(punchDate)
                                    .onDuplicateKeyIgnore()
                                    .execute();
                            dslContext.insertInto(VALIDATIONS)
                                    .values(punchDate, ticketID)
                                    .execute();
                        });
                        return 1;
                    })
                    .closeConnection();
            return true;
        }
    }

    /**
     * Performs the SQL query that adds a new price list.
     * @param year the year in which the price list will be valid. Its value cannot be a year of the past,
     *             otherwise the query will not be executed.
     *             If the year is present, nothing will be done, and {@code true} will be returned.
     * @return {@code true} if the insertion is successful, or the year is already present, {@code false} otherwise.
     */
    public static boolean addNewPriceList(final int year) {
        if (year < LocalDate.now().getYear()) {
            return false;
        }
        final boolean yearIsPresent = new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(DSL.count())
                        .from(PRICE_LISTS)
                        .where(PRICE_LISTS.YEAR.eq(year))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
        if (yearIsPresent) {
            return true;
        }
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.insertInto(PRICE_LISTS)
                        .values(year)
                        .onDuplicateKeyIgnore()
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    private static boolean isGuestId(final @NonNull String personID) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.selectCount()
                        .from(GUESTS)
                        .where(GUESTS.GUESTID.eq(personID))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
    }

    private static boolean isTicketID(final @NonNull String id) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.selectCount()
                        .from(TICKETS)
                        .where(TICKETS.TICKETID.eq(id))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
    }

    // the method does not check the validity of the provided ticketID, so a check must be done before calling this method.
    private static boolean isTicketValid(final @NonNull LocalDate date, final @NonNull String ticketID) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(DSL.count())
                        .from(TICKETS)
                        .where(TICKETS.TICKETID.eq(ticketID))
                        .and(TICKETS.VALIDON.eq(date).or(TICKETS.VALIDUNTIL.greaterOrEqual(date)))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
    }

    private static int getRemainingEntrances(final @NonNull String ticketID) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(TICKETS.REMAININGENTRANCES)
                        .from(TICKETS)
                        .where(TICKETS.TICKETID.eq(ticketID))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
    }
}
