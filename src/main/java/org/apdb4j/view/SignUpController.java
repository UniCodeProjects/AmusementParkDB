package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apdb4j.util.LoadFXML;

import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the sign-up scene.
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField username;

    /**
     * Handles the sign-up.
     * @param event the event
     */
    @FXML
    void signUp(final ActionEvent event) {
        LoadFXML.fromEvent(event, "layouts/staff-screen.fxml", false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final var screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        final var width = screenDimensions.getWidth() * 0.2;
        email.setPrefWidth(width);
        username.setPrefWidth(width);
        password.setPrefWidth(width);
        signUpBtn.setPrefWidth(width);
    }

}
