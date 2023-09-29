package org.apdb4j.core.managers;

import lombok.NonNull;
import org.jooq.Record;

import java.util.List;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.ParkServices} table.
 */
public class ParkServiceManager {

    /**
     * Performs the SQL query that changes the name of the given park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newName the new name of the provided park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    static void changeName(@NonNull String parkServiceID, @NonNull String newName, @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that modifies the average rating of the provided park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newAverageRating the new average rating of the given park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    static void editAverageRating(@NonNull String parkServiceID, double newAverageRating, @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that increments of one unit the number of ratings for the provided park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    static void incrementRatings(@NonNull String parkServiceID, @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that changes the type of the given park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newType the new type of the provided park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    static void changeType(@NonNull String parkServiceID, @NonNull String newType, @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
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
    static void addDescription(@NonNull String parkServiceID,
                        @NonNull String description,
                        @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that retrieves the park's best services according to their average rating.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return the park's best services.
     */
    static @NonNull List<Record> getBestParkServices(@NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
