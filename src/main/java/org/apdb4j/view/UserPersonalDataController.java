package org.apdb4j.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see and edit their personal data.
 */
public class UserPersonalDataController implements Initializable {
    private static final String EDIT_BUTTON_IMAGE_PATH = "img/edit_button.png";
    private static final double EDIT_BUTTON_IMAGE_HEIGHT = 15;
    private static final double EDIT_BUTTON_IMAGE_WIDTH = 15;

    @FXML
    private Button backButton;

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
    private HBox emailContainer;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private HBox nameContainer;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private BorderPane pane;

    @FXML
    private HBox passwordContainer;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private VBox personalDataContainer;

    @FXML
    private Label personalDataLabel;

    @FXML
    private HBox surnameContainer;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField surnameTextField;

    @FXML
    private HBox usernameContainer;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTextField;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final ImageView editNameImage = new ImageView(EDIT_BUTTON_IMAGE_PATH);
        editNameImage.setFitWidth(EDIT_BUTTON_IMAGE_WIDTH);
        editNameImage.setFitHeight(EDIT_BUTTON_IMAGE_HEIGHT);
        final ImageView editSurnameImage = new ImageView(EDIT_BUTTON_IMAGE_PATH);
        editSurnameImage.setFitWidth(EDIT_BUTTON_IMAGE_WIDTH);
        editSurnameImage.setFitHeight(EDIT_BUTTON_IMAGE_HEIGHT);
        final ImageView editUsernameImage = new ImageView(EDIT_BUTTON_IMAGE_PATH);
        editUsernameImage.setFitWidth(EDIT_BUTTON_IMAGE_WIDTH);
        editUsernameImage.setFitHeight(EDIT_BUTTON_IMAGE_HEIGHT);
        final ImageView editEmailImage = new ImageView(EDIT_BUTTON_IMAGE_PATH);
        editEmailImage.setFitWidth(EDIT_BUTTON_IMAGE_WIDTH);
        editEmailImage.setFitHeight(EDIT_BUTTON_IMAGE_HEIGHT);
        final ImageView editPasswordImage = new ImageView(EDIT_BUTTON_IMAGE_PATH);
        editPasswordImage.setFitWidth(EDIT_BUTTON_IMAGE_WIDTH);
        editPasswordImage.setFitHeight(EDIT_BUTTON_IMAGE_HEIGHT);
        editNameButton.setGraphic(editNameImage);
        editSurnameButton.setGraphic(editSurnameImage);
        editUsernameButton.setGraphic(editUsernameImage);
        editEmailButton.setGraphic(editEmailImage);
        editPasswordButton.setGraphic(editPasswordImage);
        confirmNewNameButton.setOnMouseClicked(event -> {
            confirmNewNameButton.setVisible(false);
            nameTextField.setEditable(false);
        });
        confirmNewSurnameButton.setOnMouseClicked(event -> {
            confirmNewSurnameButton.setVisible(false);
            surnameTextField.setEditable(false);
        });
        confirmNewUsernameButton.setOnMouseClicked(event -> {
            confirmNewUsernameButton.setVisible(false);
            usernameTextField.setEditable(false);
        });
        confirmNewEmailButton.setOnMouseClicked(event -> {
            confirmNewEmailButton.setVisible(false);
            emailTextField.setEditable(false);
        });
    }

    /**
     * Allows the user to edit the name in their account.
     * @param event the click on the edit name button.
     */
    @FXML
    void onEditNameButtonClick(final MouseEvent event) {
        nameTextField.setEditable(true);
        confirmNewNameButton.setVisible(true);
    }

    /**
     * Allows the user to edit the surname in their account.
     * @param event the click on the edit surname button.
     */
    @FXML
    void onEditSurnameButtonClick(final MouseEvent event) {
        surnameTextField.setEditable(true);
        confirmNewSurnameButton.setVisible(true);
    }

    /**
     * Allows the user to edit the username of their account.
     * @param event the click on the edit username button.
     */
    @FXML
    void onEditUsernameButtonClick(final MouseEvent event) {
        usernameTextField.setEditable(true);
        confirmNewUsernameButton.setVisible(true);
    }

    /**
     * Allows the user to edit the email of their account.
     * @param event the click on the edit email button.
     */
    @FXML
    void onEditEmailButtonClick(final MouseEvent event) {
        emailTextField.setEditable(true);
        confirmNewEmailButton.setVisible(true);
    }

    /**
     * Allows the user to edit the password of their account.
     * @param event the click on the edit password button.
     */
    @FXML
    void onEditPasswordButtonClick(final MouseEvent event) {
        LoadFXML.fromEventAsPopup(event, "layouts/change-password-popup.fxml", "Change your password");
    }
}
