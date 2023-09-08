package org.apdb4j.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        final var node = (Node) event.getSource();
        final var window = node.getScene().getWindow();
        // Checks if it's safe to downcast.
        Stage stage;
        if (window instanceof Stage) {
            stage = (Stage) window;
        } else {
            throw new IllegalStateException("Failed cast: the given window is not a Stage instance");
        }
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
