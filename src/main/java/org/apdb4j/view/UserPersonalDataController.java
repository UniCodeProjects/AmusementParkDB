package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

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
    private BorderPane pane;

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
        JavaFXUtils.setLabeledImage(editPasswordButton,
                EDIT_BUTTON_IMAGE_PATH,
                EDIT_BUTTON_IMAGE_WIDTH,
                EDIT_BUTTON_IMAGE_HEIGHT);
        confirmNewNameButton.setOnAction(event -> {
            confirmNewNameButton.setVisible(false);
            nameTextField.setEditable(false);
        });
        confirmNewSurnameButton.setOnAction(event -> {
            confirmNewSurnameButton.setVisible(false);
            surnameTextField.setEditable(false);
        });
        confirmNewUsernameButton.setOnAction(event -> {
            confirmNewUsernameButton.setVisible(false);
            usernameTextField.setEditable(false);
        });
        confirmNewEmailButton.setOnAction(event -> {
            confirmNewEmailButton.setVisible(false);
            emailTextField.setEditable(false);
        });
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
        LoadFXML.fromEventAsPopup(event, "layouts/change-password-popup.fxml", "Change your password");
    }
}
