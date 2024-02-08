package org.apdb4j.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.core.managers.AccountManager;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.util.view.AlertBuilder;
import org.jooq.exception.DataAccessException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the password changing popup scene.
 */
public class ChangePasswordPopupController implements Initializable {

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
        final boolean queryResult;
        try {
            queryResult = AccountManager.updateAccountPassword(SessionManager.getSessionManager().getSession().email(),
                    oldPasswordField.getText().trim(),
                    newPasswordField.getText().trim(),
                    "");
        } catch (final AccessDeniedException | DataAccessException e) {
            new AlertBuilder(Alert.AlertType.ERROR)
                    .setContentText(e.getMessage())
                    .show();
            return;
        }
        if (!queryResult) {
            new AlertBuilder(Alert.AlertType.ERROR)
                    .setContentText("Something went wrong while updating the password.")
                    .show();
            return;
        }
        new AlertBuilder(Alert.AlertType.INFORMATION)
                .setContentText("Password has been updated successfully.")
                .show();
        confirmBtn.getScene().getWindow().hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            final var scene = safeCastToStage(confirmBtn.getScene().getWindow());
            scene.setResizable(false);
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
