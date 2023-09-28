package org.apdb4j.view;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import lombok.AccessLevel;
import lombok.Getter;
import org.apdb4j.controllers.LoginControllerImpl;

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
