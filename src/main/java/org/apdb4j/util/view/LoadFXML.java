package org.apdb4j.util.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.NonNull;
import org.apdb4j.view.BackableFXMLController;
import org.apdb4j.view.FXMLController;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides a simple way to load a new scene from a FXML file.
 */
public final class LoadFXML {

    private static final String FXML_LOAD_EXCEPTION_ERROR_MSG = "Could not load scene from FXML file";
    private static final String FXML_FILES_DIRECTORY = "layouts/";
    private static final Set<Package> VIEW_PACKAGES = new Reflections(new ConfigurationBuilder().forPackage("org.apdb4j.view"))
            .getSubTypesOf(FXMLController.class).stream()
            .map(Class::getPackage)
            .collect(Collectors.toSet());
    private static final Object[] NULL_VARARG = null;

    private LoadFXML() {
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
    public static void fromEvent(final @NonNull Event event, final @NonNull String fxml,
                                 final boolean removeFocus, final boolean showLoading,
                                 final boolean setPreviousScene) {
        fromEvent(event, getControllerClass(fxml), removeFocus, showLoading, setPreviousScene, NULL_VARARG);
    }

    /**
     * Loads a scene from the provided  using an event.
     * Used inside event handlers.
     * @param event the event that provides the scene.
     * @param fxmlControllerClass the class of the FXML controller of the next scene.
     * @param removeFocus {@code true} to remove the focus from any visible component
     * @param showLoading if {@code true} shows a loading indicator while loading the FXML
     * @param setPreviousScene {@code true} if the next scene must set its previous scene (e.g. if the next scene has a
     *                                     button to turn back to the previous scene), {@code false} otherwise.
     * @param controllerConstructorArgs the arguments that have to be passed to the FXML controller's constructor.
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public static void fromEvent(final @NonNull Event event, final @NonNull Class<?> fxmlControllerClass,
                                 final boolean removeFocus, final boolean showLoading,
                                 final boolean setPreviousScene, final Object... controllerConstructorArgs) {
        final var stage = JavaFXUtils.getStage(event);
        final var previousSceneStageTitle = stage.getTitle();
        final var stageWidth = stage.getScene().getWidth();
        final var stageHeight = stage.getScene().getHeight();
        final Task<Parent> task = new Task<>() {
            @Override
            protected Parent call() throws IOException {
                final FXMLLoader loader = initializeFXMLLoader(fxmlControllerClass, controllerConstructorArgs);
                final var root = (Parent) loader.load();
                if (setPreviousScene) {
                    final BackableFXMLController controller = loader.getController();
                    controller.setPreviousScene(((Node) event.getSource()).getScene(), previousSceneStageTitle);
                }
                return root;
            }
        };

        task.setOnSucceeded(e -> Platform.runLater(() -> {
            final Parent root = task.getValue();
            final Scene scene = new Scene(root, stageWidth, stageHeight);
            if (removeFocus) {
                root.requestFocus();
            }
            stage.setScene(scene);
        }));

        task.setOnFailed(e -> {
            throw new IllegalStateException(task.getException());
        });

        task.setOnRunning(e -> {
            if (showLoading) {
                Platform.runLater(() -> {
                    final Parent root;
                    try {
                        root = FXMLLoader.load(ClassLoader.getSystemResource(FXML_FILES_DIRECTORY + "loading-screen.fxml"));
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
        fromEventAsPopup(event, fxml, title, 1, 1);
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
        fromEventAsPopup(event, getControllerClass(fxml), title, widthSizeFactor, heightSizeFactor, null, NULL_VARARG);
    }

    /**
     * Loads a scene as popup from its FXML controller using an event.
     * Used inside event handlers.
     * @param event the event that provides the scene
     * @param fxmlControllerClass the class of the FXML controller of the scene to be loaded.
     * @param title the window title.
     * @param widthSizeFactor the size factor for the popup width.
     * @param heightSizeFactor the size factor for the popup height.
     * @param owner the owner of the popup stage. In other words, the window from which this method is called.
     *              It is better to set the owner only when needed. If this information is not necessary, it is better
     *              to set this parameter to {@code null}.
     * @param controllerConstructorArgs the arguments that have to be passed to the FXML controller's constructor.
     */
    public static void fromEventAsPopup(final @NonNull Event event,
                                        final @NonNull Class<?> fxmlControllerClass,
                                        final @NonNull String title,
                                        final double widthSizeFactor,
                                        final double heightSizeFactor,
                                        final Window owner,
                                        final Object... controllerConstructorArgs) {
        Parent root;
        final FXMLLoader loader = initializeFXMLLoader(fxmlControllerClass, controllerConstructorArgs);
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new IllegalStateException(FXML_LOAD_EXCEPTION_ERROR_MSG, e);
        }
        // Creating a new Scene with the loaded FXML and with an adequate size.
        final var window = JavaFXUtils.getStage(event).getScene().getWindow();
        final Scene popupScene = new Scene(root, window.getWidth() * widthSizeFactor, window.getHeight() * heightSizeFactor);
        // Setting the popup stage.
        final Stage popupStage = new Stage();
        popupStage.initOwner(owner);
        popupStage.setScene(popupScene);
        popupStage.setTitle(title);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        // Showing and centering the popup window.
        popupStage.addEventHandler(WindowEvent.WINDOW_SHOWN, windowEvent -> {
            final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            popupStage.setX((screenBounds.getWidth() - popupStage.getWidth()) / 2);
            popupStage.setY((screenBounds.getHeight() - popupStage.getHeight()) / 2);
        });
        root.requestFocus();
        Platform.runLater(popupStage::show);
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
        fromNodeAsPopup(node, getControllerClass(fxml), title, NULL_VARARG);
    }

    /**
     * Loads a scene as popup from its FXML controller using a node.
     * Used inside event handlers.
     * @param node the node that provides the scene.
     * @param fxmlControllerClass the class of the FXML controller of the scene to be loaded.
     * @param title the window title.
     * @param controllerConstructorArgs the arguments that have to be passed to the FXML controller's constructor.
     */
    public static void fromNodeAsPopup(final @NonNull Node node,
                                       final @NonNull Class<?> fxmlControllerClass,
                                       final @NonNull String title,
                                       final Object... controllerConstructorArgs) {
        Parent root;
        final FXMLLoader loader = initializeFXMLLoader(fxmlControllerClass, controllerConstructorArgs);
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new IllegalStateException(FXML_LOAD_EXCEPTION_ERROR_MSG, e);
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

    private static Constructor<?> getConstructor(final Class<?> controllerClass, final int numParameters) {
        final var controllerConstructors = controllerClass.getDeclaredConstructors();
        Constructor<?> selectedConstructor = null;
        for (final var controllerConstructor : controllerConstructors) {
            if (controllerConstructor.getParameterCount() == numParameters) {
                selectedConstructor = controllerConstructor;
            }
            if (!Objects.isNull(selectedConstructor)) {
                break;
            }
        }
        if (selectedConstructor == null) {
            throw new IllegalArgumentException("No available constructor of "
                    + controllerClass + " with " + numParameters + " parameters.");
        } else {
            return selectedConstructor;
        }
    }

    private static String getFXMLFromControllerClass(final Class<?> controllerClass) {
        var controllerClassName = controllerClass.getName();
        controllerClassName = controllerClassName.replace(controllerClass.getPackageName() + ".", "");
        controllerClassName = controllerClassName.replace("Controller", "");
        final char[] controllerClassNameChars = controllerClassName.toCharArray();
        final List<Character> fxmlNameAsList = new ArrayList<>();
        int dashes = 0;
        for (final char c : controllerClassNameChars) {
            if (Character.isUpperCase(c)) {
                fxmlNameAsList.add('-');
                dashes++;
                fxmlNameAsList.add(Character.toLowerCase(c));
            } else {
                fxmlNameAsList.add(c);
            }
        }
        // the first char is a dash, it must be deleted.
        fxmlNameAsList.remove(0);
        // Subtracted 1 because the first char has been removed.
        final var fxmlNameAsArray = new char[controllerClassNameChars.length + dashes - 1];
        int i = 0;
        for (final char c : fxmlNameAsList) {
            fxmlNameAsArray[i] = c;
            i++;
        }
        return new String(fxmlNameAsArray) + ".fxml";
    }

    private static Class<?> getControllerClass(final @NonNull String fxmlPath) {
        final String fxmlFileName = fxmlPath.replace(FXML_FILES_DIRECTORY, "").replace(".fxml", "");
        final var fxmlFileNameArray = fxmlFileName.toCharArray();
        final List<Character> fxmlControllerClassName = new ArrayList<>();
        boolean isNextCharUpper = true; // the first character of the class name has to be uppercase.
        for (final char c : fxmlFileNameArray) {
            if (isNextCharUpper) {
                fxmlControllerClassName.add(Character.toUpperCase(c));
                isNextCharUpper = false;
            } else if (c == '-') {
                isNextCharUpper = true;
            } else {
                fxmlControllerClassName.add(c);
            }
        }
        final StringBuilder builder = new StringBuilder();
        fxmlControllerClassName.forEach(builder::append);
        builder.append("Controller");
        Class<?> fxmlControllerClass = null;
        for (final var viewPackage : VIEW_PACKAGES) {
            builder.insert(0, viewPackage.getName() + ".");
            try {
                fxmlControllerClass = Class.forName(builder.toString());
            } catch (ClassNotFoundException ignored) {
            } finally {
                builder.delete(0, viewPackage.getName().length() + 1);
            }
        }
        if (Objects.isNull(fxmlControllerClass)) {
            throw new IllegalArgumentException("Could not load any fxml controller from the given fxml file: " + fxmlPath);
        }
        return fxmlControllerClass;
    }

    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    private static FXMLLoader initializeFXMLLoader(final @NonNull Class<?> fxmlControllerClass,
                                                   final Object... controllerConstructorArgs) {
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(FXML_FILES_DIRECTORY
                + getFXMLFromControllerClass(fxmlControllerClass)));
        if (!Objects.isNull(controllerConstructorArgs)) {
            loader.setControllerFactory(c -> {
                try {
                    return getConstructor(fxmlControllerClass, controllerConstructorArgs.length)
                            .newInstance(controllerConstructorArgs);
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return loader;
    }
}
