package org.apdb4j.controllers.guests;

import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Abstract class that contains functionalities that are common to all the {@link ParkServiceController}s.
 */
abstract class AbstractParkServiceController implements ParkServiceController {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Supplier<List<Map<String, String>>>> getSortOptionsWithActions() {
        return Map.of("Average rating (" + Order.ASCENDING.getName() + ")", () -> sortByAverageRating(Order.ASCENDING),
                "Average rating (" + Order.DESCENDING.getName() + ")", () -> sortByAverageRating(Order.DESCENDING),
                "Name (" + Order.ASCENDING.getName() + ")", () -> sortByName(Order.ASCENDING),
                "Name (" + Order.DESCENDING.getName() + ")", () -> sortByName(Order.DESCENDING));
    }

    /**
     * Sorts by name (following the provided {@code order}) all the park services handled by this controller.
     * @param order the sorting order.
     * @return all the park services handled by this controller sorted by their names, following the provided order.
     *         The park services are well-formatted for the GUI.
     */
    protected abstract List<Map<String, String>> sortByName(@NonNull Order order);

    /**
     * Sorts by their average rating (following the provided {@code order}) the park services handled by this controller.
     * @param order the sorting order.
     * @return all the park services handled by this controller sorted by their average rating, following the provided order.
     *         The park services are well-formatted for the GUI.
     */
    protected abstract List<Map<String, String>> sortByAverageRating(@NonNull Order order);
}
