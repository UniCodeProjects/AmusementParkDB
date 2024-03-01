package org.apdb4j.view.guests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see and edit their personal data.
 */
public class UserPersonalDataController extends BackableAbstractFXMLController {
    private static final String EDIT_BUTTON_IMAGE_PATH = "img/edit_button.png";
    private static final double EDIT_BUTTON_IMAGE_HEIGHT = 15;
    private static final double EDIT_BUTTON_IMAGE_WIDTH = 15;

    @FXML
    private Button confirmNewEmailButton;
    @FXML
    private Button confirmNewNameButton;
    @FXML
    private Button confirmNewSurnameButton;
    @FXML
    private Button confirmNewUsernameButton;
    @FXML
    private Button editEmailButton;
    @FXML
    private Button editNameButton;
    @FXML
    private Button editPasswordButton;
    @FXML
    private Button editSurnameButton;
    @FXML
    private Button editUsernameButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField usernameTextField;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        JavaFXUtils.setLabeledImage(editNameButton,
                EDIT_BUTTON_IMAGE_PATH,
                EDIT_BUTTON_IMAGE_WIDTH,
                EDIT_BUTTON_IMAGE_HEIGHT);
        JavaFXUtils.setLabeledImage(editSurnameButton,
                EDIT_BUTTON_IMAGE_PATH,
                EDIT_BUTTON_IMAGE_WIDTH,
                EDIT_BUTTON_IMAGE_HEIGHT);
        JavaFXUtils.setLabeledImage(editUsernameButton,
                EDIT_BUTTON_IMAGE_PATH,
                EDIT_BUTTON_IMAGE_WIDTH,
                EDIT_BUTTON_IMAGE_HEIGHT);
        JavaFXUtils.setLabeledImage(editEmailButton,
                EDIT_BUTTON_IMAGE_PATH,
                EDIT_BUTTON_IMAGE_WIDTH,
                EDIT_BUTTON_IMAGE_HEIGHT);
        confirmNewNameButton.setOnAction(event -> {
            confirmNewNameButton.setVisible(false);
            nameTextField.setEditable(false);
            if (!SessionManager.getSessionManager().changeLoggedAccountOwnerName(nameTextField.getText())) {
                new AlertBuilder(Alert.AlertType.ERROR).setContentText("The provided name is not valid").show();
                nameTextField.setText(SessionManager.getSessionManager().getSession().name());
            } else {
                new AlertBuilder(Alert.AlertType.INFORMATION).setContentText("Name modified successfully!").show();
            }
        });
        confirmNewSurnameButton.setOnAction(event -> {
            confirmNewSurnameButton.setVisible(false);
            surnameTextField.setEditable(false);
            if (!SessionManager.getSessionManager().changeLoggedAccountOwnerSurname(surnameTextField.getText())) {
                new AlertBuilder(Alert.AlertType.ERROR).setContentText("The provided surname is not valid").show();
                surnameTextField.setText(SessionManager.getSessionManager().getSession().surname());
            } else {
                new AlertBuilder(Alert.AlertType.INFORMATION).setContentText("Surname modified successfully!").show();
            }
        });
        confirmNewUsernameButton.setOnAction(event -> {
            confirmNewUsernameButton.setVisible(false);
            usernameTextField.setEditable(false);
            if (!SessionManager.getSessionManager().changeLoggedAccountUsername(usernameTextField.getText())) {
                new AlertBuilder(Alert.AlertType.ERROR).setContentText("The provided username is not valid").show();
                usernameTextField.setText(SessionManager.getSessionManager().getSession().username());
            } else {
                new AlertBuilder(Alert.AlertType.INFORMATION).setContentText("Username modified successfully!").show();
            }
        });
        confirmNewEmailButton.setOnAction(event -> {
            confirmNewEmailButton.setVisible(false);
            emailTextField.setEditable(false);
            if (!SessionManager.getSessionManager().changeLoggedAccountEmail(emailTextField.getText())) {
                new AlertBuilder(Alert.AlertType.ERROR).setContentText("The provided email is not valid").show();
                emailTextField.setText(SessionManager.getSessionManager().getSession().email());
            } else {
                new AlertBuilder(Alert.AlertType.INFORMATION).setContentText("Email updated successfully!").show();
            }
        });

        nameTextField.setText(SessionManager.getSessionManager().getSession().name());
        surnameTextField.setText(SessionManager.getSessionManager().getSession().surname());
        usernameTextField.setText(SessionManager.getSessionManager().getSession().username());
        emailTextField.setText(SessionManager.getSessionManager().getSession().email());
    }

    /**
     * Allows the user to edit the name in their account.
     * @param event the click on the edit name button.
     */
    @FXML
    void onEditNameButtonClick(final ActionEvent event) {
        nameTextField.setEditable(true);
        confirmNewNameButton.setVisible(true);
    }

    /**
     * Allows the user to edit the surname in their account.
     * @param event the click on the edit surname button.
     */
    @FXML
    void onEditSurnameButtonClick(final ActionEvent event) {
        surnameTextField.setEditable(true);
        confirmNewSurnameButton.setVisible(true);
    }

    /**
     * Allows the user to edit the username of their account.
     * @param event the click on the edit username button.
     */
    @FXML
    void onEditUsernameButtonClick(final ActionEvent event) {
        usernameTextField.setEditable(true);
        confirmNewUsernameButton.setVisible(true);
    }

    /**
     * Allows the user to edit the email of their account.
     * @param event the click on the edit email button.
     */
    @FXML
    void onEditEmailButtonClick(final ActionEvent event) {
        emailTextField.setEditable(true);
        confirmNewEmailButton.setVisible(true);
    }

    /**
     * Allows the user to edit the password of their account.
     * @param event the click on the edit password button.
     */
    @FXML
    void onEditPasswordButtonClick(final ActionEvent event) {
        LoadFXML.fromNodeAsPopup(editPasswordButton, "layouts/change-password-popup.fxml", "Change your password");
    }
}
