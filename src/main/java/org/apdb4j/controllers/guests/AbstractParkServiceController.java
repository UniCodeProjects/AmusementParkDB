package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.jooq.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * Abstract class that contains functionalities that are common to all the {@link ParkServiceController}s.
 */
abstract class AbstractParkServiceController implements ParkServiceController {

    private List<Record> actualContent = new ArrayList<>();

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

    private List<Map<String, String>> sortByName(final @NonNull Order order) {
        actualContent = actualContent.stream().sorted((ride1, ride2) -> switch (order) {
            case ASCENDING -> ride1.get(PARK_SERVICES.NAME).compareTo(ride2.get(PARK_SERVICES.NAME));
            case DESCENDING -> ride2.get(PARK_SERVICES.NAME).compareTo(ride1.get(PARK_SERVICES.NAME));
        }).toList();
        return formatActualContent();
    }

    private List<Map<String, String>> sortByAverageRating(final @NonNull Order order) {
        setActualContent(getActualContent().stream().sorted((ride1, ride2) -> switch (order) {
            case ASCENDING -> ride1.get(PARK_SERVICES.AVGRATING).compareTo(ride2.get(PARK_SERVICES.AVGRATING));
            case DESCENDING -> ride2.get(PARK_SERVICES.AVGRATING).compareTo(ride1.get(PARK_SERVICES.AVGRATING));
        }).toList());
        return formatActualContent();
    }

    /**
     * Formats the data that have to be displayed in the view such that the view component can handle them.
     * @return the data well-formatted for the GUI.
     */
    protected List<Map<String, String>> formatActualContent() {
        return Formatter.format(actualContent);
    }

    /**
     * Allows the subclasses to set their actual content.
     * The actual content changes as a result of sorting or filtering actions.
     * @param newContent the data that have to be displayed in the GUI.
     */
    protected void setActualContent(final @NonNull List<Record> newContent) {
        this.actualContent = List.copyOf(newContent);
    }

    /**
     * Retrieves the actually displayed content in a way that it could be handled by the controller.
     * @return the currently displayed data.
     */
    protected @NonNull List<Record> getActualContent() {
        return List.copyOf(actualContent);
    }
}
