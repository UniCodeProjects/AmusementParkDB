package org.apdb4j.util.view;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Utility class for JavaFX.
 */
public final class JavaFXUtils {

    private JavaFXUtils() {
    }

    /**
     * Sets the primary stage's title by concatenating the previously
     * set one, with the new one.
     * @param event the event source
     * @param title the new title
     */
    public static void setStageTitle(final Event event, final String title) {
        final var stage = getStage(event);
        stage.setTitle(stage.getTitle() + " - " + title);
    }

    /**
     * Returns the primary stage.
     * @param event the event source
     * @return the primary stage
     */
    public static Stage getStage(final Event event) {
        final var node = (Node) event.getSource();
        final var window = node.getScene().getWindow();
        // Checks if it's safe to downcast.
        Stage stage;
        if (window instanceof Stage) {
            stage = (Stage) window;
        } else {
            throw new IllegalStateException("Failed cast: the given window is not a Stage instance");
        }
        return stage;
    }

    /**
     * A date cell that enables only the first day of the month.
     */
    public static class FirstDayDateCell extends DateCell {
        /**
         * {@inheritDoc}
         */
        @Override
        public void updateItem(final LocalDate item, final boolean empty) {
            super.updateItem(item, empty);
            setDisable(item != null && item.getDayOfMonth() != 1);
        }
    }

    /**
     * A date cell that enables only the last day of the month.
     */
    public static class LastDayDateCell extends DateCell {
        /**
         * {@inheritDoc}
         */
        @Override
        public void updateItem(final LocalDate item, final boolean empty) {
            super.updateItem(item, empty);
            setDisable(item != null && item.getDayOfMonth() != item.lengthOfMonth());
        }
    }

}
