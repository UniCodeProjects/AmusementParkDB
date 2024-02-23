package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.apdb4j.db.Tables.ACCOUNTS;
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
     * @param reviewID the identifier of the new review.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a park service,
     *                      the query will not be executed.
     * @param rating the rating of the review.
     * @param description the possible description of the review.
     * @param account the account that is leaving a review.
     * @return {@code true} if the review is added successfully.
     */
    public static boolean addReview(final @NonNull String reviewID,
                                    final @NonNull String parkServiceID,
                                    final int rating,
                                    final String description,
                                    final @NonNull String account) {
        final LocalDate currentDate = LocalDate.now();
        final LocalTime currentTime = LocalTime.now();
        new QueryBuilder().createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        final var dslContext = configuration.dsl();
                        dslContext.insertInto(REVIEWS)
                                .values(reviewID,
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

    /**
     * Returns the average rating of the park service with the provided name, if in the database is present
     * a park service with the provided name.
     * Otherwise, an {@link IllegalArgumentException} will be thrown.
     * @param parkServiceName the name of the park service.
     * @return the average rating of the provided park service.
     */
    public static double getAverageRating(final @NonNull String parkServiceName) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.AVGRATING)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.PARKSERVICEID.eq(ParkServiceManager.getParkServiceID(parkServiceName)))
                        .fetch())
                .closeConnection()
                .getResultAsRecords().getValue(0, PARK_SERVICES.AVGRATING).doubleValue();
    }

    /**
     * Retrieves the number of reviews written for the park service with the provided name, if in the database is present
     * a park service with the provided name.
     * Otherwise, an {@link IllegalArgumentException} will be thrown.
     * @param parkServiceName the name of the park service.
     * @return the number of reviews written for the provided park service.
     */
    public static int getNumberOfReviews(final @NonNull String parkServiceName) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NUMREVIEWS)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.PARKSERVICEID.eq(ParkServiceManager.getParkServiceID(parkServiceName)))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
    }

    /**
     * Returns all the reviews concerning the park service with the provided name, if in the database exists a park service
     * with the provided name.
     * Otherwise, an {@link IllegalArgumentException} will be thrown.
     * @param parkServiceName the name of the park service.
     * @return the reviews for the provided park service.
     */
    public static List<Record> getReviews(final @NonNull String parkServiceName) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(REVIEWS.RATING,
                        REVIEWS.DATE,
                        REVIEWS.TIME,
                        REVIEWS.DESCRIPTION,
                        ACCOUNTS.USERNAME)
                        .from(REVIEWS).join(ACCOUNTS).on(REVIEWS.ACCOUNT.eq(ACCOUNTS.EMAIL))
                        .where(REVIEWS.PARKSERVICEID.eq(ParkServiceManager.getParkServiceID(parkServiceName)))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    private static BigDecimal calculateNewAvgRating(final Record parkServiceOldStats, final int newReviewRating) {
        final double oldAvgRating = parkServiceOldStats.get(PARK_SERVICES.AVGRATING).doubleValue();
        final int oldNumReviews = parkServiceOldStats.get(PARK_SERVICES.NUMREVIEWS).intValue();
        return BigDecimal.valueOf((oldAvgRating * oldNumReviews + newReviewRating) / (oldNumReviews + 1));
    }
}
