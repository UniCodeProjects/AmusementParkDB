package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.ReviewTableItem;

import java.time.LocalDate;
import java.util.Collection;

/**
 * An administration controller specifically used for reviews.
 */
public interface ReviewController extends AdministrationController, Filterable {

    /**
     * Filters the reviews by date.
     * @param date the filter
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByDate(LocalDate date);

    /**
     * Filters the reviews by rating.
     * @param rating the filter
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByRating(int rating);

    /**
     * Filters the reviews by a rating range.
     * @param end the range end, inclusive
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByRatingRange(int end);

    /**
     * Filters the reviews by the ride-type.
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByRide();

    /**
     * Filters the reviews by the exhibition-type.
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByExhibition();

    /**
     * Filters the reviews by the shop-type.
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByShop();

}
