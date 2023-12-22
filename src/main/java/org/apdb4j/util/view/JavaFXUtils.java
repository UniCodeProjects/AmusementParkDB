package org.apdb4j.util.view;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class for JavaFX.
 */
public final class JavaFXUtils {

    private JavaFXUtils() {
    }

    // TODO: add parameters showLoading and removeFocus
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

}
