package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The FXML controller for the staff UI.
 */
public class StaffScreenController {

    @FXML
    private MenuBar menuBar;

    /**
     * Opens a popup window to change the user's password.
     * @param event the event
     */
    @FXML
    void changePasswordAction(final ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/change-password-popup.fxml"));
        } catch (final IOException e) {
            throw new IllegalStateException("Could not load scene from FXML file", e);
        }
        // Creating a new Scene with the loaded FXML and with an adequate size.
        final var window = menuBar.getScene().getWindow();
        final var sizeFactor = 0.3;
        final Scene popupScene = new Scene(root, window.getWidth() * sizeFactor, window.getHeight() * sizeFactor);
        // Setting the popup stage.
        final Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.setTitle("Change password");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        // Showing the popup window
        root.requestFocus();
        popupStage.show();
    }

}
