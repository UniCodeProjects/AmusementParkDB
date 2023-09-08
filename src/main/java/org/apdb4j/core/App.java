package org.apdb4j.core;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 *
 */
public class App extends Application {

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) {
        final Button btn = new Button();
        final TextField text = new TextField();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> text.setText("Hello World!"));

        final FlowPane root = new FlowPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(btn, text);

        final Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
