package org.apdb4j.util.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * This class provides a simple way to load a new scene from a FXML file.
 */
public final class LoadFXML {

    private LoadFXML() {
    }

    /**
     * Loads a scene from a FXML using an event.
     * Used inside event handlers.
     * @param event the event that provides the source
     * @param fxml the FXML path
     * @param removeFocus {@code true} to remove the focus from any visible component
     */
    public static void fromEvent(final ActionEvent event, final String fxml, final boolean removeFocus) {
        Parent root;
        try {
            root = FXMLLoader.load(ClassLoader.getSystemResource(fxml));
        } catch (final IOException e) {
            throw new IllegalStateException("Could not load scene from FXML file", e);
        }
        final var stage = JavaFXUtils.getStage(event);
        // Ensures that the new scene has the same size of the previous one.
        final var width = stage.getScene().getWidth();
        final var height = stage.getScene().getHeight();
        final Scene scene = new Scene(root, width, height);
        if (removeFocus) {
            root.requestFocus();
        }
        stage.setScene(scene);
    }

}
