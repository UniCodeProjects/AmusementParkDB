package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.HashUtils;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.apdb4j.db.Tables.PARK_SERVICES;
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
     * @return {@code true} if the review is added successfully.
     */
    public static boolean addReview(final @NonNull String parkServiceID, final int rating, final String description,
                                    final @NonNull String account) {
        final LocalDate currentDate = LocalDate.now();
        final LocalTime currentTime = LocalTime.now();
        new QueryBuilder().createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        final var dslContext = configuration.dsl();
                        dslContext.insertInto(REVIEWS)
                                .values(HashUtils.generate(currentDate, currentTime),
                                        rating,
                                        currentDate,
                                        currentTime,
                                        description,
                                        account,
                                        parkServiceID)
                                .execute();
                        final var parkServiceOldStats = dslContext.select(PARK_SERVICES.AVGRATING, PARK_SERVICES.NUMREVIEWS)
                                .from(PARK_SERVICES)
                                .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                                .fetchOne();
                        if (parkServiceOldStats != null) {
                            dslContext.update(PARK_SERVICES)
                                    .set(PARK_SERVICES.NUMREVIEWS, PARK_SERVICES.NUMREVIEWS.plus(1))
                                    .set(PARK_SERVICES.AVGRATING, calculateNewAvgRating(parkServiceOldStats, rating))
                                    .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                                    .execute();
                        }
                    });
                    return 1;
                }).closeConnection();
        return true;
    }

    private static BigDecimal calculateNewAvgRating(final Record parkServiceOldStats, final int newReviewRating) {
        final double oldAvgRating = parkServiceOldStats.get(PARK_SERVICES.AVGRATING).doubleValue();
        final int oldNumReviews = parkServiceOldStats.get(PARK_SERVICES.NUMREVIEWS).intValue();
        return BigDecimal.valueOf((oldAvgRating * oldNumReviews + newReviewRating) / (oldNumReviews + 1));
    }
}
