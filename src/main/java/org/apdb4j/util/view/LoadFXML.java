package org.apdb4j.util.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
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
     * @param showLoading if {@code true} shows a loading indicator while loading the FXML
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public static void fromEvent(final ActionEvent event, final String fxml,
                                 final boolean removeFocus, final boolean showLoading) {
        final var stage = JavaFXUtils.getStage(event);
        final var stageWidth = stage.getScene().getWidth();
        final var stageHeight = stage.getScene().getHeight();
        final Task<Parent> task = new Task<>() {
            @Override
            protected Parent call() throws IOException {
                return FXMLLoader.load(ClassLoader.getSystemResource(fxml));
            }
        };

        task.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                final Parent root = task.getValue();
                final Scene scene = new Scene(root, stageWidth, stageHeight);
                if (removeFocus) {
                    root.requestFocus();
                }
                stage.setScene(scene);
            });
        });

        task.setOnFailed(e -> {
            throw new IllegalStateException(task.getException());
        });

        task.setOnRunning(e -> {
            if (showLoading) {
                Platform.runLater(() -> {
                    final Parent root;
                    try {
                        root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/loading-screen.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    final Scene scene = new Scene(root, stageWidth, stageHeight);
                    stage.setScene(scene);
                });
            }
        });

        final Thread thread = new Thread(task);
        thread.start();
    }

}
