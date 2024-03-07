package org.apdb4j.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

/**
 * GUI entry point.
 */
public class App extends Application {

    private static final double WIDTH_FACTOR = 0.8;
    private static final double HEIGHT_FACTOR = 0.8;
    private static final double MIN_WIDTH_FACTOR = 0.7;
    private static final double MIN_HEIGHT_FACTOR = 0.7;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/signin-screen.fxml"));
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final var width = screenSize.getWidth() * WIDTH_FACTOR;
        final var height = screenSize.getHeight() * HEIGHT_FACTOR;
        final Scene scene = new Scene(root, width, height);
        root.requestFocus();

        primaryStage.setMinWidth(width * MIN_WIDTH_FACTOR);
        primaryStage.setMinHeight(height * MIN_HEIGHT_FACTOR);
        primaryStage.setTitle("APDB4J");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
