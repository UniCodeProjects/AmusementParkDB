package org.apdb4j.controllers.guests;

import java.util.Collection;
import java.util.Map;

/**
 * MVC controller that provides only partial information
 * about all the park services of a specific {@link ParkServiceType}.
 */
public interface ParkServiceOverviewController extends ParkServiceController {

    /**
     * Returns an overview of all the park services handled by this controller (e.g. in a {@link RideOverviewController}
     * is returned an overview of all the rides).
     * In this method the names of the attributes and their values are formatted for the view.
     * @return a partial set of information about all the park services handled by the controller.
     */
    Collection<Map<String, String>> getOverview();

    /**
     * Filters the shown park services according to the values of {@code maxRating} and {@code ranged}.
     * @param maxRating if {@code ranged} is true, the value of this parameter is the maximum average rating allowed.
     *                  Otherwise, it is the exact average rating allowed.
     * @param ranged if {@code true}, are returned all the rides whose average rating is between the minimum
     *               rating and {@code maxRating}.
     *               If {@code false}, are returned all the rides whose average rating is equal to {@code maxRating}.
     * @return the park services that satisfy the specified average rating constraint.
     */
    Collection<Map<String, String>> filterByAverageRating(int maxRating, boolean ranged);
}
