package org.apdb4j.view;

import javafx.scene.Scene;

/**
 * Interface that represents FXML controllers that have to manage a "back" button.
 */
public interface BackableFXMLController extends FXMLController {

    /**
     * Sets the previous scene and the stage title associated with it.
     * @param previousScene the previous scene.
     * @param previousSceneTitle the stage title that has to be set when {@code previousScene} is shown.
     */
    void setPreviousScene(Scene previousScene, String previousSceneTitle);
}
