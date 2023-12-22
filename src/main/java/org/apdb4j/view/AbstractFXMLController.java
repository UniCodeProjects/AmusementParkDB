package org.apdb4j.view;

import javafx.scene.Scene;

/**
 * Abstract class for FXML controllers.
 */
public abstract class AbstractFXMLController implements FXMLController {
    private Scene previousScene;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPreviousScene(final Scene previousScene) {
        this.previousScene = previousScene;
    }

    /**
     * Retrieves the previous scene.
     * @return the previous scene.
     */
    protected Scene getPreviousScene() {
        return this.previousScene;
    }
}
