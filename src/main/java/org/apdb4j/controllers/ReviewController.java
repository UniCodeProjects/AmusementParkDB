package org.apdb4j.controllers;

import org.apdb4j.view.staff.tableview.ReviewTableItem;

import java.util.Collection;

/**
 * An administration controller specifically used for reviews.
 */
public interface ReviewController extends AdministrationController, Filterable {

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
     * @param id the filter
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByRide(String id);

    /**
     * Filters the reviews by the exhibition-type.
     * @param id the filter
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByExhibition(String id);

    /**
     * Filters the reviews by the shop-type.
     * @param id the filter
     * @param <T> the {@code ReviewTableItem} type
     * @return the filtered reviews
     */
    <T extends ReviewTableItem> Collection<T> filterByShop(String id);

}
