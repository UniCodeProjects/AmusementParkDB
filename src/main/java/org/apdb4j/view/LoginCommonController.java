package org.apdb4j.view;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apdb4j.controllers.LoginControllerImpl;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

/**
 * The login fxml controller. Groups all common functionalities
 * between the sign-in and sign-up controllers.
 * @see SignInController
 * @see SignUpController
 */
public class LoginCommonController {

    @Getter(AccessLevel.PACKAGE)
    private final org.apdb4j.controllers.LoginController controller = new LoginControllerImpl();

    /**
     * Loads the correct screen based on the account's permission type.
     * @param event the source's event
     * @param username account's username
     */
    void showUserScreen(final @NonNull Event event, final @NonNull String username) {
        Platform.runLater(() -> {
            JavaFXUtils.setStageTitle(event, username);
            if (getController().isStaff(username)) {
                LoadFXML.fromEvent(event, "layouts/staff-screen.fxml", false, true);
            } else if (getController().isGuest(username)) {
                LoadFXML.fromEvent(event, "layouts/user-screen.fxml", false, true);
            } else {
                throw new IllegalStateException("Unknown permission type for '" + username + "'.");
            }
        });
    }

    /**
     * Shows a simple error dialog window.<br>
     * The shown error message is given by the MVC controller.
     * If no message was given, a simple "Error" text will be displayed.
     */
    void showErrorDialog() {
        Platform.runLater(() -> {
            final var alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An error has occurred.");
            alert.setContentText(controller.getErrorMessage().orElse("Error"));
            alert.show();
        });
    }

}
