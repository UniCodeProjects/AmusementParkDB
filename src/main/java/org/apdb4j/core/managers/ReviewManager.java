package org.apdb4j.core.managers;

import lombok.NonNull;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Reviews} table.
 */
public class ReviewManager {

    /**
     * Performs the SQL query that adds a review for the provided park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a park service,
     *                      the query will not be executed.
     * @param rating the rating of the review.
     * @param description the possible description of the review.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    static void addReview(@NonNull String parkServiceID, int rating, String description, @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
