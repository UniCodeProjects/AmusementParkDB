package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.core.managers.ParkServiceManager;
import org.apdb4j.core.managers.ReviewManager;
import org.apdb4j.util.IDGenerationUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of interface {@link ReviewController}.
 */
public class ReviewControllerImpl implements ReviewController {

    private final String parkServiceName;

    /**
     * Creates a new instance of this class that refers to the park service with the provided name.
     * @param parkServiceName the name of the park service.
     */
    public ReviewControllerImpl(final @NonNull String parkServiceName) {
        this.parkServiceName = parkServiceName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, String>> getReviews() {
        return ReviewManager.getReviews(parkServiceName).stream()
                .map(review -> Arrays.stream(review.fields())
                        .map(field -> new ImmutablePair<>(field.getName(),
                                Objects.isNull(review.get(field)) ? "" : review.get(field).toString()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAverageRating() {
        return ReviewManager.getAverageRating(parkServiceName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfReviews() {
        return ReviewManager.getNumberOfReviews(parkServiceName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addReview(final int rating, final String description) {
        return ReviewManager.addReview(IDGenerationUtils.generateReviewID(),
                ParkServiceManager.getParkServiceID(parkServiceName),
                rating,
                description,
                SessionManager.getSessionManager().getSession().email());
    }
}
