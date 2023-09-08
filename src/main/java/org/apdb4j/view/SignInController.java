package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apdb4j.util.JavaFXUtils;
import org.apdb4j.util.LoadFXML;

import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the sign-in scene.
 */
public class SignInController implements Initializable {

    @FXML
    private PasswordField password;

    @FXML
    private Button signInBtn;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private TextField username;

    /**
     * Handles the sign-in.
     * @param event the event
     */
    @FXML
    void signIn(final ActionEvent event) {
        // todo: check user type, add validator.
        if (!username.getText().isBlank() || !password.getText().isBlank()) {
            JavaFXUtils.setStageTitle(event, username.getText());
            LoadFXML.fromEvent(event, "layouts/staff-screen.fxml", false);
        }
    }

    /**
     * Opens the sign-up scene.
     * @param event the event
     */
    @FXML
    void signUp(final ActionEvent event) {
        LoadFXML.fromEvent(event, "layouts/signup-screen.fxml", true);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final var screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        final var width = screenDimensions.getWidth() * 0.2;
        username.setPrefWidth(width);
        password.setPrefWidth(width);
        signInBtn.setPrefWidth(width);
    }

}
