package org.apdb4j.core.managers;

import lombok.NonNull;
import org.jooq.Record;

import java.util.List;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.ParkServices} table.
 */
public final class ParkServiceManager {

    private ParkServiceManager() {
    }

    /**
     * Performs the SQL query that changes the name of the given park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newName the new name of the provided park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void changeName(final @NonNull String parkServiceID, final @NonNull String newName,
                                  final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that modifies the average rating of the provided park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newAverageRating the new average rating of the given park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void editAverageRating(final @NonNull String parkServiceID, final double newAverageRating,
                                         final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that increments of one unit the number of ratings for the provided park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void incrementRatings(final @NonNull String parkServiceID, final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that changes the type of the given park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newType the new type of the provided park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void changeType(final @NonNull String parkServiceID, final @NonNull String newType,
                                  final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that adds a description for the provided park service. If the park service
     * already has a description, this one will be overwritten by the given one.
     * @param parkServiceID the park service. If the value of this parameter is not the identifier of a park service,
     *                      the query will not be executed.
     * @param description the description to add for the provided park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void addDescription(final @NonNull String parkServiceID,
                                      final @NonNull String description,
                                      final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs the SQL query that retrieves the park's best services according to their average rating.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return the park's best services.
     */
    public static @NonNull List<Record> getBestParkServices(final @NonNull String account) {
        throw new UnsupportedOperationException();
    }

}
