package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.EXHIBITION_DETAILS;
import static org.apdb4j.db.Tables.PARK_SERVICES;
import static org.jooq.impl.DSL.avg;

/**
 * Contains all the SQL queries that are related to the entity EXHIBITION.
 */
public final class ExhibitionManager {

    private static final QueryBuilder DB = new QueryBuilder();

    private ExhibitionManager() {
    }

    /**
     * Performs the SQL query that adds a new exhibition without a description, that will be set to {@code null}.
     * @param exhibitionID the identifier of the new exhibition.
     * @param name the name of the new exhibition.
     * @param type the type of the new exhibition.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewExhibition(final @NonNull String exhibitionID,
                                           final @NonNull String name,
                                           final @NonNull String type,
                                           final @NonNull String account) {
        return addNewExhibitionWithDescription(exhibitionID, name, type, null, account);
    }

    /**
     * Performs the SQL query that adds a new exhibition.
     * @param exhibitionID the identifier of the new exhibition.
     * @param name the name of the new exhibition.
     * @param type the type of the new exhibition.
     * @param description the description of the new exhibition. If it is {@code null} the behavior of this method
     *                    is the same of
     *                    {@link ExhibitionManager#addNewExhibition(String, String, String, String)}.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewExhibitionWithDescription(final @NonNull String exhibitionID,
                                                          final @NonNull String name,
                                                          final @NonNull String type,
                                                          final String description,
                                                          final @NonNull String account) {
        final int insertedTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(PARK_SERVICES,
                                PARK_SERVICES.PARKSERVICEID,
                                PARK_SERVICES.NAME,
                                PARK_SERVICES.TYPE,
                                PARK_SERVICES.DESCRIPTION,
                                PARK_SERVICES.AVGRATING,
                                PARK_SERVICES.NUMREVIEWS,
                                PARK_SERVICES.ISEXHIBITION)
                        .values(exhibitionID,
                                name,
                                type,
                                description,
                                BigDecimal.ZERO,
                                UInteger.valueOf(0),
                                (byte) 1)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return insertedTuples == 1;
    }

    /**
     * Performs the SQL query that plans a new exhibition, so inserts a new tuple with the
     * provided values in the {@link org.apdb4j.db.tables.ExhibitionDetails} table.
     * @param exhibitionID the ID of the planned exhibition. If the value of this parameter is not the identifier
     *                     of an exhibition, the query will not be executed.
     * @param date the date on which the exhibition will be performed.
     * @param time the hour on which the exhibition will be performed.
     * @param maxSeats the maximum number of seats of the exhibition venue.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean planNewExhibition(final @NonNull String exhibitionID,
                                            final @NonNull LocalDate date, final @NonNull LocalTime time,
                                            final int maxSeats,
                                            final @NonNull String account) {
        final int insertedTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(EXHIBITION_DETAILS)
                        .values(exhibitionID, date, time, maxSeats, null)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return insertedTuples == 1;
    }

    /**
     * Performs the SQL query that changes the maximum number of seats for the provided exhibition.
     * @param exhibitionID the ID of the exhibition. If the value of this parameter is not the identifier of an
     *                     exhibition, the query will not be executed.
     * @param date the date on which the exhibition will be performed. The date needs to be in the future, otherwise
     *             the query will not be executed.
     * @param time the time on which the exhibition will be performed.
     *             The time needs to be in the future if {@code date} is the current day. Otherwise, the query will not
     *             be executed.
     * @param newMaxSeats the new maximum number of seats.
     * @param account the account that is performing this operation. If the account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean changeMaxSeats(final @NonNull String exhibitionID,
                                         final @NonNull LocalDate date, final @NonNull LocalTime time,
                                         final int newMaxSeats,
                                         final @NonNull String account) {
        final int updatedTuples;
        if (date.equals(LocalDate.now())) {
            updatedTuples = DB.createConnection()
                    .queryAction(db -> db.update(EXHIBITION_DETAILS)
                            .set(EXHIBITION_DETAILS.MAXSEATS, newMaxSeats)
                            .where(EXHIBITION_DETAILS.EXHIBITIONID.eq(exhibitionID))
                            .and(EXHIBITION_DETAILS.DATE.eq(date))
                            .and(EXHIBITION_DETAILS.TIME.eq(time))
                            .and(EXHIBITION_DETAILS.TIME.greaterThan(LocalTime.now()))
                            .execute())
                    .closeConnection()
                    .getResultAsInt();
        } else {
            updatedTuples = DB.createConnection()
                    .queryAction(db -> db.update(EXHIBITION_DETAILS)
                            .set(EXHIBITION_DETAILS.MAXSEATS, newMaxSeats)
                            .where(EXHIBITION_DETAILS.EXHIBITIONID.eq(exhibitionID))
                            .and(EXHIBITION_DETAILS.DATE.eq(date))
                            .and(EXHIBITION_DETAILS.DATE.greaterThan(LocalDate.now()))
                            .and(EXHIBITION_DETAILS.TIME.eq(time))
                            .execute())
                    .closeConnection()
                    .getResultAsInt();
        }
        return updatedTuples == 1;
    }

    /**
     * Performs the SQL query that adds the number of spectators for an ended exhibition.
     * @param exhibitionID the ID of the exhibition. If the value of this parameter is not the identifier
     *                     of an exhibition, the query will not be executed.
     * @param date the date on which the exhibition was performed. The date has to be in the past, or at least
     *             the current date. Otherwise, the query will not be executed.
     * @param time the hour on which the exhibition was performed. The time has to be in the past if {@code date} is the
     *             current date, otherwise the query will not be executed.
     * @param spectators the number of people that watched the exhibition.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean addSpectatorsNum(final @NonNull String exhibitionID,
                                           final @NonNull LocalDate date, final @NonNull LocalTime time,
                                           final int spectators,
                                           final @NonNull String account) {
        final int updatedTuples;
        if (date.equals(LocalDate.now())) {
            updatedTuples = DB.createConnection()
                    .queryAction(db -> db.update(EXHIBITION_DETAILS)
                            .set(EXHIBITION_DETAILS.SPECTATORS, UInteger.valueOf(spectators))
                            .where(EXHIBITION_DETAILS.EXHIBITIONID.eq(exhibitionID))
                            .and(EXHIBITION_DETAILS.DATE.eq(date))
                            .and(EXHIBITION_DETAILS.DATE.lessOrEqual(LocalDate.now()))
                            .and(EXHIBITION_DETAILS.TIME.eq(time))
                            .and(EXHIBITION_DETAILS.TIME.lessOrEqual(LocalTime.now()))
                            .execute())
                    .closeConnection()
                    .getResultAsInt();
        } else {
            updatedTuples = DB.createConnection()
                    .queryAction(db -> db.update(EXHIBITION_DETAILS)
                            .set(EXHIBITION_DETAILS.SPECTATORS, UInteger.valueOf(spectators))
                            .where(EXHIBITION_DETAILS.EXHIBITIONID.eq(exhibitionID))
                            .and(EXHIBITION_DETAILS.DATE.eq(date))
                            .and(EXHIBITION_DETAILS.DATE.lessOrEqual(LocalDate.now()))
                            .and(EXHIBITION_DETAILS.TIME.eq(time))
                            .execute())
                    .closeConnection()
                    .getResultAsInt();
        }
        return updatedTuples == 1;
    }

    /**
     * Performs the SQL query that computes the average number of spectators for each
     * exhibition type.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return a collection in which each exhibition type is paired with its average number of spectators.
     */
    public static @NonNull Collection<Pair<String, Integer>> getAverageSpectatorsForType(final @NonNull String account) {
        final Result<Record> exhibitionTypes = DB.createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.TYPE)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.ISEXHIBITION.eq(UByte.valueOf(1).byteValue()))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        final Set<Pair<String, Integer>> result = new HashSet<>();
        exhibitionTypes.forEach(type -> {
            final MutablePair<String, Integer> typeAndAvgValues = new MutablePair<>();
            typeAndAvgValues.setLeft(type.get(0, String.class));
            final int average = DB.createConnection()
                    .queryAction(db -> db.select(avg(EXHIBITION_DETAILS.SPECTATORS))
                            .from(EXHIBITION_DETAILS)
                            .join(PARK_SERVICES)
                            .on(PARK_SERVICES.PARKSERVICEID.eq(EXHIBITION_DETAILS.EXHIBITIONID))
                            .where(EXHIBITION_DETAILS.SPECTATORS.isNotNull())
                            .and(PARK_SERVICES.TYPE.eq(type.get(0, String.class)))
                            .fetchOne(0, int.class))
                    .closeConnection()
                    .getResultAsInt();
            typeAndAvgValues.setRight(average);
            result.add(typeAndAvgValues);
        });
        return result;
    }

    /**
     * Performs the SQL query that computes the percentage of exhibitions that were sold-out until now.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return the percentage of sold-out exhibitions.
     */
    public static double computePercentageOfSoldOutExhibitions(final @NonNull String account) {
        final int soldOutAmount = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(EXHIBITION_DETAILS)
                        .where(EXHIBITION_DETAILS.SPECTATORS.eq(EXHIBITION_DETAILS.MAXSEATS.cast(UInteger.class)))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
        return (double) (soldOutAmount * 100) / DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(EXHIBITION_DETAILS)
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
    }

    /**
     * Realises the SQL query that retrieves all the exhibitions that are going to be performed in the future.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return all the exhibitions that are planned for the future.
     */
     public static @NonNull Collection<Record> viewAllPlannedExhibitions(final @NonNull String account) {
         return DB.createConnection()
                 .queryAction(db -> db.select()
                         .from(EXHIBITION_DETAILS
                                 .join(PARK_SERVICES)
                                 .on(PARK_SERVICES.PARKSERVICEID.eq(EXHIBITION_DETAILS.EXHIBITIONID)))
                         .where(EXHIBITION_DETAILS.DATE.greaterThan(LocalDate.now()))
                         .or(EXHIBITION_DETAILS.DATE.equal(LocalDate.now())
                                 .and(EXHIBITION_DETAILS.TIME.greaterThan(LocalTime.now())))
                         .fetch())
                 .closeConnection()
                 .getResultAsRecords().stream()
                 .collect(Collectors.toUnmodifiableSet());
     }

}
