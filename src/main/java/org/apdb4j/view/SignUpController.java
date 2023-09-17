package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

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
        if (noTextFieldIsBlank()) {
            JavaFXUtils.setStageTitle(event, username.getText());
            LoadFXML.fromEvent(event, "layouts/staff-screen.fxml", false, true);
        }
    }

    /**
     * Allows to sign-up by pressing the enter key.
     * @param event the event
     */
    @FXML
    void onEnterPressed(final KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)
                && noTextFieldIsBlank()) {
            signUpBtn.fire();
        }
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
        signUpBtn.setDisable(true);
        List.of(email, username, password).forEach(textField -> {
            textField.setOnKeyTyped(event -> {
                if (noTextFieldIsBlank()) {
                    signUpBtn.setDisable(false);
                }
            });
            textField.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)
                        && anyTextFieldIsBlank()) {
                    signUpBtn.setDisable(true);
                }
            });
        });
    }

    private boolean anyTextFieldIsBlank() {
        return Stream.of(email, username, password).map(TextField::getText).anyMatch(String::isBlank);
    }

    private boolean noTextFieldIsBlank() {
        return Stream.of(email, username, password).map(TextField::getText).noneMatch(String::isBlank);
    }

}
