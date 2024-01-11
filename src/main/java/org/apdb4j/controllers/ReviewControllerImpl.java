package org.apdb4j.controllers;

import lombok.NonNull;
import org.apdb4j.core.managers.ReviewManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.ReviewTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.*;

/**
 * Implementation of a review controller.
 */
public class ReviewControllerImpl implements ReviewController {

    private String errorMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractReviewData(searchQuery(DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T addData(final T item) throws SQLException {
        final ReviewTableItem review = (ReviewTableItem) item;
        final boolean querySuccessful = ReviewManager.addReview(review.getServiceID(),
                Math.toIntExact(Math.round(review.getRating())),
                review.getDescription(),
                "");
        if (!querySuccessful) {
            errorMessage = "Data could not be added";
            throw new SQLException(errorMessage);
        }
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String parkServiceId) {
        return extractReviewData(searchQuery(REVIEWS.PARKSERVICEID.containsIgnoreCase(parkServiceId)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ReviewTableItem> Collection<T> filterByRating(final int rating) {
        return extractReviewData(searchQuery(REVIEWS.RATING.eq((byte) rating)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ReviewTableItem> Collection<T> filterByRatingRange(final int end) {
        return extractReviewData(searchQuery(REVIEWS.RATING.between((byte) 1, (byte) end)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ReviewTableItem> Collection<T> filterByRide() {
        return extractReviewData(searchQueryJoin(RIDES, RIDES.RIDEID.eq(REVIEWS.PARKSERVICEID), DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ReviewTableItem> Collection<T> filterByExhibition() {
        return extractReviewData(searchQueryJoin(PARK_SERVICES,
                PARK_SERVICES.PARKSERVICEID.eq(REVIEWS.PARKSERVICEID),
                PARK_SERVICES.ISEXHIBITION.isTrue()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ReviewTableItem> Collection<T> filterByShop() {
        return extractReviewData(searchQueryJoin(FACILITIES,
                FACILITIES.FACILITYID.eq(REVIEWS.PARKSERVICEID),
                FACILITIES.ISSHOP.isTrue()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    private Result<Record> searchQuery(final Condition condition) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select()
                        .from(REVIEWS)
                        .where(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    private Result<Record> searchQueryJoin(final TableLike<?> joinTable, final Condition on, final Condition condition) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select()
                        .from(REVIEWS)
                        .join(joinTable)
                        .on(on)
                        .where(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> Collection<T> extractReviewData(final Result<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new ReviewTableItem(record.get(REVIEWS.PARKSERVICEID),
                record.get(REVIEWS.RATING),
                record.get(REVIEWS.DESCRIPTION))));
        return data;
    }

}
