package org.apdb4j.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the password changing popup scene.
 */
public class PasswordPopupController implements Initializable {

    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Button confirmBtn;

    /**
     * Closes the popup window after confirming the password change.
     * @param event the event
     */
    @FXML
    void onConfirm(final ActionEvent event) {
        // todo: checks needed.
        final Stage stage = safeCastToStage(confirmBtn.getScene().getWindow());
        stage.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            final var scene = safeCastToStage(confirmBtn.getScene().getWindow());
            final var width = scene.getWidth() * 0.5;
            oldPasswordField.setPrefWidth(width);
            newPasswordField.setPrefWidth(width);
        });
    }

    private Stage safeCastToStage(final Window window) {
        Stage stage;
        if (window instanceof Stage) {
            stage = (Stage) window;
        } else {
            throw new IllegalStateException("Failed cast: the given window is not a Stage instance");
        }
        return stage;
    }

}
