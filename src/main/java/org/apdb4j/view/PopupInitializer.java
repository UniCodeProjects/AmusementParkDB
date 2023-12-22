package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Initialises a popup window with the adequate dimensions based on the window content.
 */
public abstract class PopupInitializer implements Initializable {

    private Stage stage;
    @Setter
    private Parent root;
    private double heightSizeFactor = 1;
    private double widthSizeFactor = 1;

    /**
     * Sets the stage from the given window.
     * @param window the window to get the stage from
     */
    public void setStage(final Window window) {
        this.stage = safeCastToStage(window);
    }

    /**
     * Sets the height size factor.
     * @param heightSizeFactor the factor
     */
    public void setHeightSizeFactor(final double heightSizeFactor) {
        if (isNegativeOrEqualToZero(heightSizeFactor)) {
            throw new NumberFormatException("Height size factor (" + heightSizeFactor + ") is <= 0");
        }
        this.heightSizeFactor = heightSizeFactor;
    }

    /**
     * Sets the width size factor.
     * @param widthSizeFactor the factor
     */
    public void setWidthSizeFactor(final double widthSizeFactor) {
        if (isNegativeOrEqualToZero(widthSizeFactor)) {
            throw new NumberFormatException("Width size factor (" + widthSizeFactor + ") is <= 0");
        }
        this.widthSizeFactor = widthSizeFactor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            if (stage == null || root == null) {
                final String nullObj = stage == null ? "Stage" : "Root";
                throw new IllegalStateException(nullObj + " object is null.");
            }
            stage.setResizable(false);
            // Content bias is null, so the argument needs to be -1 as mentioned by the method doc.
            final double rootPrefHeight = root.prefHeight(-1) * heightSizeFactor;
            final double rootPrefWidth = root.prefWidth(-1) * widthSizeFactor;
            final double padding = 40;
            stage.setHeight(rootPrefHeight + padding);
            stage.setWidth(rootPrefWidth + padding);
            editMode();
        });
    }

    /**
     * Defines the actions for the specific edit mode.
     */
    protected abstract void editMode();

    private Stage safeCastToStage(final Window window) {
        Stage stage;
        if (window instanceof Stage) {
            stage = (Stage) window;
        } else {
            throw new IllegalStateException("Failed cast: the given window is not a Stage instance");
        }
        return stage;
    }

    private boolean isNegativeOrEqualToZero(final double n) {
        return Double.compare(n, 0.0) <= 0;
    }

}
