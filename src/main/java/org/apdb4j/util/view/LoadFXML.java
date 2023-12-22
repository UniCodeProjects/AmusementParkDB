package org.apdb4j.util.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.NonNull;
import org.apdb4j.view.FXMLController;

import java.io.IOException;

/**
 * This class provides a simple way to load a new scene from a FXML file.
 */
public final class LoadFXML {

    private LoadFXML() {
    }

    // TODO: change name and add parameters showLoading and removeFocus. Move this method in JavaFXUtils
    /**
     * Sets the provided scene from the provided event.
     * @param event an event.
     * @param scene the scene that has to be shown when {@code event} occurs.
     * @param stageTitle the stage title that has to be set when {@code scene} is shown.
     */
    public static void fromEvent(final Event event, final Scene scene, final String stageTitle) {
        final var stage = JavaFXUtils.getStage(event);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
    }

    /**
     * Loads a scene from a FXML using an event.
     * Used inside event handlers.
     * @param event the event that provides the scene
     * @param fxml the FXML path
     * @param removeFocus {@code true} to remove the focus from any visible component
     * @param showLoading if {@code true} shows a loading indicator while loading the FXML
     * @param setPreviousScene {@code true} if the next scene must set its previous scene (e.g. if the scene has a
     *                                     button to turn back to the previous scene), {@code false} otherwise.
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public static void fromEvent(final Event event, final String fxml,
                                 final boolean removeFocus, final boolean showLoading,
                                 final boolean setPreviousScene) {
        final var stage = JavaFXUtils.getStage(event);
        final var previousSceneStageTitle = stage.getTitle();
        final var stageWidth = stage.getScene().getWidth();
        final var stageHeight = stage.getScene().getHeight();
        final Task<Parent> task = new Task<>() {
            @Override
            protected Parent call() throws IOException {
                final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(fxml));
                final var root = (Parent) loader.load();
                if (setPreviousScene) {
                    final FXMLController controller = loader.getController();
                    controller.setPreviousScene(((Node) event.getSource()).getScene(), previousSceneStageTitle);
                }
                return root;
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

    /**
     * Loads a scene as popup from a FXML using an event.
     * Used inside event handlers.
     * @param event the event that provides the scene
     * @param fxml the FXML path
     * @param title the window title
     */
    public static void fromEventAsPopup(final @NonNull Event event,
                                        final @NonNull String fxml,
                                        final @NonNull String title) {
        fromEventAsPopup(event, fxml, title, 0.3, 0.3);
    }

    /**
     * Loads a scene as popup from a FXML using an event.
     * Used inside event handlers.
     * @param event the event that provides the scene
     * @param fxml the FXML path
     * @param title the window title
     * @param widthSizeFactor the size factor for the popup width
     * @param heightSizeFactor the size factor for the popup height
     */
    public static void fromEventAsPopup(final @NonNull Event event,
                                        final @NonNull String fxml,
                                        final @NonNull String title,
                                        final double widthSizeFactor,
                                        final double heightSizeFactor) {
        Parent root;
        try {
            root = FXMLLoader.load(ClassLoader.getSystemResource(fxml));
        } catch (final IOException e) {
            throw new IllegalStateException("Could not load scene from FXML file", e);
        }
        // Creating a new Scene with the loaded FXML and with an adequate size.
        final var window = JavaFXUtils.getStage(event).getScene().getWindow();
        final Scene popupScene = new Scene(root, window.getWidth() * widthSizeFactor, window.getHeight() * heightSizeFactor);
        // Setting the popup stage.
        final Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.setTitle(title);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        // Showing the popup window
        root.requestFocus();
        popupStage.show();
    }

    /**
     * Loads a scene as popup from a FXML using a node.
     * Used inside event handlers.
     * @param node the node that provides the scene
     * @param fxml the FXML path
     * @param title the window title
     */
    public static void fromNodeAsPopup(final @NonNull Node node,
                                        final @NonNull String fxml,
                                        final @NonNull String title) {
        Parent root;
        try {
            root = FXMLLoader.load(ClassLoader.getSystemResource(fxml));
        } catch (final IOException e) {
            throw new IllegalStateException("Could not load scene from FXML file", e);
        }
        // Creating a new Scene with the loaded FXML and with an adequate size.
        final var window = node.getScene().getWindow();
        final var sizeFactor = 0.3;
        final Scene popupScene = new Scene(root, window.getWidth() * sizeFactor, window.getHeight() * sizeFactor);
        // Setting the popup stage.
        final Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.setTitle(title);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        // Showing the popup window
        root.requestFocus();
        popupStage.show();
    }

}
