package org.apdb4j.core.managers;

import lombok.NonNull;

import java.time.LocalTime;

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
     */
    public static void addNewRide(final @NonNull String rideID,
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
        throw new UnsupportedOperationException();
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
     */
    public static void addNewRideWithDescription(final @NonNull String rideID,
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
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that changes the intensity for the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param newIntensity the new intensity of the provided ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void changeIntensity(final @NonNull String rideID, final @NonNull String newIntensity,
                                       final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that changes the duration of the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param newDuration the new duration of the ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void changeDuration(final @NonNull String rideID, final @NonNull LocalTime newDuration,
                                      final @NonNull String account) {
        throw new UnsupportedOperationException();
    }


    /**
     * Performs the SQL query that changes the maximum number of seats for the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param newMaxSeats the new maximum number of seats.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void changeMaxSeats(final @NonNull String rideID, final int newMaxSeats, final @NonNull String account) {
        throw new UnsupportedOperationException();
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
     */
    public static void changeHeightRequirements(final @NonNull String rideID, final int minHeight,  final int maxHeight,
                                                final @NonNull String account) {
        throw new UnsupportedOperationException();
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
     */
    public static void changeWeightRequirements(final @NonNull String rideID, final int minWeight, final int maxWeight,
                                                final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that adds the given estimated wait time for the provided ride.
     * @param rideID the ride identifier. If the value of this parameter is not the identifier of a ride,
     *               the query will not be executed.
     * @param estimatedWaitTime the estimated wait time to get in the ride.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void addEstimatedWaitTime(final @NonNull String rideID, final @NonNull LocalTime estimatedWaitTime,
                                            final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Replaces the actual status of a ride with the given one.
     * @param rideID the ride. If the value of this parameter is not present in the database, the query will not be executed.
     * @param newStatus the new status of the provided ride. If the value of this parameter is not valid, the query
     *                  will not be executed.
     * @param account the account that is performing this operation. If the account has not the permissions to
     *                accomplish the operation, the query will not be executed.
     */
    public static void changeRideStatus(final @NonNull String rideID, final char newStatus, final @NonNull String account) {
        throw new UnsupportedOperationException();
    }
    // Note that if the new status is 'C', the estimatedWaitTime of the provided ride must be set to null!
    // This operation must be done every day when the park closes.

    // Collection<Record> viewAllRidesInfo(String account);
    // This method can return not only all the static information of the rides, but also their dynamic information,
    // by joining the values of the table RIDES and RIDE_DETAILS.

    // Collection<Record> viewRidesEstimatedWaitTime(String account);

}
