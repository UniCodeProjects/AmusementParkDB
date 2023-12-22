package org.apdb4j.view;

import javafx.scene.Scene;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Objects;

/**
 * Abstract class for FXML controllers.
 */
public abstract class AbstractFXMLController implements FXMLController {
    private final MutablePair<Scene, String> previousSceneWithStageTitle = new MutablePair<>();

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
     * Retrieves the previous scene.
     * @return the previous scene.
     */
    protected Scene getPreviousScene() {
        return this.previousSceneWithStageTitle.getLeft();
    }

    /**
     * Retrieves the stage title that has to be set when the previous scene is shown.
     * @return the stage title associated with the previous scene.
     */
    protected String getPreviousSceneTitle() {
        return this.previousSceneWithStageTitle.getRight();
    }
}
