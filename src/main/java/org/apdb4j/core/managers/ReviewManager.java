package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apache.commons.codec.digest.XXHash32;
import org.apdb4j.util.QueryBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.apdb4j.db.Tables.REVIEWS;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Reviews} table.
 */
public final class ReviewManager {

    private ReviewManager() {
    }

    /**
     * Performs the SQL query that adds a review for the provided park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a park service,
     *                      the query will not be executed.
     * @param rating the rating of the review.
     * @param description the possible description of the review.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the review is added successfully, {@code false} otherwise.
     */
    public static boolean addReview(final @NonNull String parkServiceID, final byte rating, final String description,
                                    final @NonNull String account) {
        final LocalDate currentDate = LocalDate.now();
        final LocalTime currentTime = LocalTime.now();
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.insertInto(REVIEWS)
                        .values(computeHash(currentDate.toString() + currentTime.toString()),
                                rating,
                                currentDate,
                                currentTime,
                                description,
                                account,
                                parkServiceID)
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    private static String computeHash(final String str) {
        final var hash = new XXHash32();
        hash.update(str.getBytes(StandardCharsets.UTF_8));
        return Long.toHexString(hash.getValue());
    }

}
