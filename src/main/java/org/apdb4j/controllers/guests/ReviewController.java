package org.apdb4j.controllers.guests;

import java.util.List;
import java.util.Map;

/**
 * MVC controller for reviews.
 */
public interface ReviewController {

    /**
     * Retrieves the maximum rating that a user can choose for a review.
     * @return the maximum rating for a review.
     */
    static int getMaxRating() {
        return 5;
    }

    /**
     * Retrieves all the reviews for the park service referred by this instance.
     * @return all the reviews for the park service referred by this instance.
     */
    List<Map<String, String>> getReviews();

    /**
     * Retrieves the average rating of the park service referred by this instance.
     * @return the average rating of the park service referred by this instance.
     */
    double getAverageRating();

    /**
     * Retrieves the number of reviews of the park service referred by this instance.
     * @return the number of reviews of the park service referred by this instance.
     */
    int getNumberOfReviews();

    /**
     * Adds a new review with the provided {@code rating} and {@code description}
     * for the park service referred by this instance.
     * @param rating the rating of the review.
     * @param description the description of the review.
     * @return {@code true} whether the insertion is successful, {@code false} otherwise.
     */
    boolean addReview(int rating, String description);
}
