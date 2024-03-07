package org.apdb4j.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apdb4j.util.view.LoadFXML;

import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * The FXML controller for the sign-in scene.
 */
public class SignInScreenController extends LoginCommonController implements Initializable {

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
        CompletableFuture.supplyAsync(() -> {
            Platform.runLater(() -> signInBtn.setDisable(true));
            return getController().checkSignIn(username.getText(), password.getText());
        }).thenAcceptAsync(result -> {
            Platform.runLater(() -> signInBtn.setDisable(false));
            if (result) {
                showUserScreen(event, username.getText());
            } else {
                showErrorDialog();
            }
        });
    }

    /**
     * Opens the sign-up scene.
     * @param event the event
     */
    @FXML
    void signUp(final ActionEvent event) {
        LoadFXML.fromEvent(event, "layouts/sign-up-screen.fxml", true, false, false);
    }

    /**
     * Allows to sign-in by pressing the enter key.
     * @param event the event
     */
    @FXML
    void onEnterPressed(final KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) && noTextFieldIsBlank()) {
            signInBtn.fire();
        }
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
        signInBtn.setDisable(true);
        List.of(username, password).forEach(textField -> {
            textField.setOnKeyTyped(event -> {
                if (noTextFieldIsBlank()) {
                    signInBtn.setDisable(false);
                }
            });
            textField.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)
                        && anyTextFieldIsBlank()) {
                    signInBtn.setDisable(true);
                }
            });
        });
    }

    private boolean anyTextFieldIsBlank() {
        return Stream.of(username, password).map(TextField::getText).anyMatch(String::isBlank);
    }

    private boolean noTextFieldIsBlank() {
        return Stream.of(username, password).map(TextField::getText).noneMatch(String::isBlank);
    }

}
