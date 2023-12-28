package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apdb4j.util.view.JavaFXUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Abstract class for FXML controllers that have a "back" button.
 */
public abstract class BackableAbstractFXMLController implements BackableFXMLController, Initializable {
    private static final double BACK_BUTTON_IMAGE_HEIGHT = 25;
    private static final double BACK_BUTTON_IMAGE_WIDTH = 25;
    private static final String BACK_BUTTON_IMAGE_PATH = "img/left_arrow.png";
    private final MutablePair<Scene, String> previousSceneWithStageTitle = new MutablePair<>();
    @FXML
    private Button backButton;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.setLabeledImage(backButton, BACK_BUTTON_IMAGE_PATH, BACK_BUTTON_IMAGE_WIDTH, BACK_BUTTON_IMAGE_HEIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPreviousScene(final Scene previousScene, final String previousSceneTitle) {
        if (Objects.isNull(previousSceneWithStageTitle.getLeft())) {
            previousSceneWithStageTitle.setLeft(previousScene);
            previousSceneWithStageTitle.setRight(previousSceneTitle);
        }
    }

    /**
     * Returns to the previous scene.
     * @param event the click on the "back" button.
     */
    @FXML
    protected void onBackButtonPressed(final ActionEvent event) {
        JavaFXUtils.setSceneFromEvent(event, getPreviousScene(), getPreviousSceneTitle());
    }

    private Scene getPreviousScene() {
        return this.previousSceneWithStageTitle.getLeft();
    }

    private String getPreviousSceneTitle() {
        return this.previousSceneWithStageTitle.getRight();
    }
}
