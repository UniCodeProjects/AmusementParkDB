package org.apdb4j.util.view;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Utility class for JavaFX.
 */
public final class JavaFXUtils {

    private JavaFXUtils() {
    }

    /**
     * Sets the provided scene starting from the provided event.
     * @param event an event.
     * @param scene the scene that has to be shown when {@code event} occurs.
     * @param stageTitle the stage title that has to be set when {@code scene} is shown.
     */
    public static void setSceneFromEvent(final Event event, final Scene scene, final String stageTitle) {
        final var stage = getStage(event);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
    }

    /**
     * Sets the primary stage's title by concatenating the previously
     * set one, with the new one.
     * @param event the event source
     * @param title the new title
     * @param concat {@code true} if the new title should be the previous title concatenated with {@code title},
     *               {@code false} if the new title should be only {@code title}.
     */
    public static void setStageTitle(final Event event, final String title, final boolean concat) {
        final var stage = getStage(event);
        if (concat) {
            stage.setTitle(stage.getTitle() + " - " + title);
        } else {
            stage.setTitle(title);
        }
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
     * Sets the provided image for the given labeled element with the given width and height.
     * @param labeled a labeled element.
     * @param imagePath the image path.
     * @param width the image width in the labeled.
     * @param height the image height in the labeled.
     */
    public static void setLabeledImage(final Labeled labeled,
                                       final String imagePath,
                                       final double width,
                                       final double height) {
        final var labeledImageView = new ImageView(new Image(imagePath));
        labeledImageView.setFitHeight(height);
        labeledImageView.setFitWidth(width);
        labeled.setGraphic(labeledImageView);
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

    /**
     * A {@link DateCell} that disables the days that are in the past.
     */
    public static class PresentAndFutureDaysDateCell extends DateCell {

        /**
         * {@inheritDoc}
         */
        @Override
        public void updateItem(final LocalDate localDate, final boolean b) {
            super.updateItem(localDate, b);
            this.setDisable(localDate.isBefore(LocalDate.now()));
        }
    }
}
