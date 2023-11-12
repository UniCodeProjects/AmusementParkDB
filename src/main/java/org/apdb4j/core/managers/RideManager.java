package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.types.UInteger;

import java.time.LocalTime;
import java.util.Collection;

import static org.apdb4j.db.Tables.PARK_SERVICES;
import static org.apdb4j.db.Tables.FACILITIES;
import static org.apdb4j.db.Tables.RIDES;
import static org.apdb4j.db.Tables.RIDE_DETAILS;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Rides} table.
 */
public final class RideManager {

    private RideManager() {
    }

    /**
     * Performs the SQL query that adds a new ride in the database without a description,
     * that will be set to {@code null}.
     * @param rideID the identifier of the new ride.
     * @param name the name of the new ride.
     * @param openingTime the opening time of the ride.
     * @param closingTime the closing time of the ride.
     * @param type the type of the ride.
     * @param intensity the intensity of the ride.
     * @param duration the duration of the ride.
     * @param maxSeats the maximum number of seats of the ride.
     * @param minHeight the minimum height required to get in the ride.
     * @param maxHeight the maximum height to get in the ride.
     * @param minWeight the minimum weight required to get in the ride.
     * @param maxWeight the maximum weight to get in the ride.
     * @param status the status of the new ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the ride is added successfully, {@code false} otherwise.
     */
    public static boolean addNewRide(final @NonNull String rideID,
                                     final @NonNull String name,
                                     final @NonNull LocalTime openingTime, final @NonNull LocalTime closingTime,
                                     final @NonNull String type,
                                     final @NonNull String intensity,
                                     final @NonNull LocalTime duration,
                                     final int maxSeats,
                                     final int minHeight, final int maxHeight,
                                     final int minWeight, final int maxWeight,
                                     final char status,
                                     final @NonNull String account) {
        return addNewRide(rideID,
                name,
                openingTime,
                closingTime,
                type,
                intensity,
                duration,
                maxSeats,
                null,
                minHeight,
                maxHeight,
                minWeight,
                maxWeight,
                status,
                account);
    }

    /**
     * Performs the SQL query that adds a new ride in the database, with the provided description.
     * Note that if the description is {@code null} the method will have the same behavior of the method
     * {@link RideManager#addNewRide(String, String, LocalTime, LocalTime, String, String,
     * LocalTime, int, int, int, int, int, char, String)}.
     * @param rideID the identifier of the new ride.
     * @param name the name of the new ride.
     * @param openingTime the opening time of the ride.
     * @param closingTime the closing time of the ride.
     * @param type the type of the ride.
     * @param intensity the intensity of the ride.
     * @param duration the duration of the ride.
     * @param maxSeats the maximum number of seats of the ride.
     * @param description the possible description of the ride.
     * @param minHeight the minimum height required to get in the ride.
     * @param maxHeight the maximum height to get in the ride.
     * @param minWeight the minimum weight required to get in the ride.
     * @param maxWeight the maximum weight to get in the ride.
     * @param status the status of the new ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the ride is added successfully.
     */
    public static boolean addNewRide(final @NonNull String rideID,
                                     final @NonNull String name,
                                     final @NonNull LocalTime openingTime, final @NonNull LocalTime closingTime,
                                     final @NonNull String type,
                                     final @NonNull String intensity,
                                     final @NonNull LocalTime duration,
                                     final int maxSeats,
                                     final String description,
                                     final int minHeight, final int maxHeight,
                                     final int minWeight, final int maxWeight,
                                     final char status,
                                     final @NonNull String account) {
        new QueryBuilder().createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        final var dslContext = configuration.dsl();
                        dslContext.insertInto(PARK_SERVICES)
                                .values(rideID, name, 0, 0, type, description, false)
                                .execute();
                        dslContext.insertInto(FACILITIES)
                                .values(rideID, openingTime, closingTime, false)
                                .execute();
                        dslContext.insertInto(RIDES)
                                .values(rideID, intensity, duration, maxSeats, minHeight, maxHeight, minWeight, maxWeight)
                                .execute();
                        if (status == 'C' || status == 'M') {
                            dslContext.insertInto(RIDE_DETAILS)
                                    .values(rideID, status, null)
                                    .execute();
                        } else {
                            dslContext.insertInto(RIDE_DETAILS)
                                    .values(rideID, status, LocalTime.of(0, 0, 0))
                                    .execute();
                        }
                    });
                    return 1;
                })
                .closeConnection();
        return true;
    }

    /**
     * Performs the SQL query that changes the intensity for the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param newIntensity the new intensity of the provided ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the intensity is modified successfully, {@code false} otherwise.
     */
    public static boolean changeIntensity(final @NonNull String rideID, final @NonNull String newIntensity,
                                          final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(RIDES)
                        .set(RIDES.INTENSITY, newIntensity)
                        .where(RIDES.RIDEID.eq(rideID))
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that changes the duration of the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param newDuration the new duration of the ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the duration is modified successfully, {@code false} otherwise.
     */
    public static boolean changeDuration(final @NonNull String rideID, final @NonNull LocalTime newDuration,
                                         final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(RIDES)
                        .set(RIDES.DURATION, newDuration)
                        .where(RIDES.RIDEID.eq(rideID))
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }


    /**
     * Performs the SQL query that changes the maximum number of seats for the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param newMaxSeats the new maximum number of seats.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    public static boolean changeMaxSeats(final @NonNull String rideID, final int newMaxSeats, final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(RIDES)
                        .set(RIDES.MAXSEATS, newMaxSeats)
                        .where(RIDES.RIDEID.eq(rideID))
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that changes the values of the attributes {@link org.apdb4j.db.tables.Rides#MINHEIGHT},
     * {@link org.apdb4j.db.tables.Rides#MAXHEIGHT} for the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param minHeight the new minimum height requirements for the ride.
     * @param maxHeight the new maximum height requirements for the ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    public static boolean changeHeightRequirements(final @NonNull String rideID, final int minHeight,  final int maxHeight,
                                                   final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(RIDES)
                        .set(RIDES.MINHEIGHT, UInteger.valueOf(minHeight))
                        .set(RIDES.MAXHEIGHT, UInteger.valueOf(maxHeight))
                        .where(RIDES.RIDEID.eq(rideID)).execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that changes the values of the attributes {@link org.apdb4j.db.tables.Rides#MINWEIGHT},
     * {@link org.apdb4j.db.tables.Rides#MAXWEIGHT} for the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param minWeight the new minimum weight requirements for the ride.
     * @param maxWeight the new maximum weight requirements for the ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    public static boolean changeWeightRequirements(final @NonNull String rideID, final int minWeight, final int maxWeight,
                                                   final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(RIDES)
                        .set(RIDES.MINWEIGHT, UInteger.valueOf(minWeight))
                        .set(RIDES.MAXWEIGHT, UInteger.valueOf(maxWeight))
                        .where(RIDES.RIDEID.eq(rideID)).execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that adds the given estimated wait time for the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param estimatedWaitTime the estimated wait time to get in the ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the insertion is successful, {@code false} otherwise.
     */
    public static boolean addEstimatedWaitTime(final @NonNull String rideID, final @NonNull LocalTime estimatedWaitTime,
                                               final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(RIDE_DETAILS)
                        .set(RIDE_DETAILS.ESTIMATEDWAITTIME, estimatedWaitTime)
                        .where(RIDE_DETAILS.RIDEID.eq(rideID)).execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Replaces the actual status of a ride with the given one.
     * @param rideID the ride. If the value of this parameter is not present in the database, the query will not be executed.
     * @param newStatus the new status of the provided ride. If the value of this parameter is not valid, the query
     *                  will not be executed.
     * @param account the account that is performing this operation. If the account has not the permissions to
     *                accomplish the operation, the query will not be executed.
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    public static boolean changeRideStatus(final @NonNull String rideID, final char newStatus, final @NonNull String account) {
        if (newStatus == 'C' || newStatus == 'M') {
            return new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(RIDE_DETAILS)
                            .set(RIDE_DETAILS.STATUS, String.valueOf(newStatus))
                            .set(RIDE_DETAILS.ESTIMATEDWAITTIME, (LocalTime) null)
                            .where(RIDE_DETAILS.RIDEID.eq(rideID))
                            .execute())
                    .closeConnection()
                    .getResultAsInt() == 1;
        } else {
            return new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(RIDE_DETAILS)
                            .set(RIDE_DETAILS.STATUS, String.valueOf(newStatus))
                            .set(RIDE_DETAILS.ESTIMATEDWAITTIME, LocalTime.of(0, 0, 0))
                            .where(RIDE_DETAILS.RIDEID.eq(rideID))
                            .execute())
                    .closeConnection()
                    .getResultAsInt() == 1;
        }
    }
    // This operation must be done every day when the park closes.

    /**
     * Retrieves all the rides paired with their estimated wait time.
     * @param account the account that is performing the operation. If the account has not the permissions to
     *                accomplish the operation, the query will not be executed.
     * @return all the rides with their estimated wait time.
     */
    public static Collection<Record> viewRidesEstimatedWaitTime(final String account) {
        return Manager.viewPartialInfoFromTable(RIDE_DETAILS, account, RIDE_DETAILS.RIDEID, RIDE_DETAILS.ESTIMATEDWAITTIME);
    }

    /**
     * Retrieves the static and dynamic ride info.
     * @param account the account that is performing the operation. If the account has not the permissions to
     *                accomplish the operation, the query will not be executed.
     * @return the static and dynamic ride info.
     */
    public static Collection<Record> viewAllRidesInfo(final String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(RIDES.RIDEID,
                                RIDES.INTENSITY,
                                RIDES.DURATION,
                                RIDES.MAXSEATS,
                                RIDES.MINHEIGHT,
                                RIDES.MAXHEIGHT,
                                RIDES.MINWEIGHT,
                                RIDES.MAXWEIGHT,
                                RIDE_DETAILS.STATUS,
                                RIDE_DETAILS.ESTIMATEDWAITTIME)
                        .from(RIDES)
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }
}
