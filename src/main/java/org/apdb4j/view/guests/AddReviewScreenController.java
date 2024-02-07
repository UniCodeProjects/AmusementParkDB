package org.apdb4j.view.guests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import lombok.NonNull;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to add a new review for a park service.
 */
public class AddReviewScreenController implements Initializable {

    private static final String TITLE_BASE_TEXT = "Add your review for";
    @FXML
    private Button confirmButton;
    @FXML
    private ToggleGroup ratingButtons;
    @FXML
    private TextArea reviewDescription;
    @FXML
    private Label title;
    private final String parkServiceName;

    /**
     * Creates a new instance of this class which refers to the park service {@code parkServiceName}.
     * @param parkServiceName the name of the park service referred by the scene.
     */
    public AddReviewScreenController(final @NonNull String parkServiceName) {
        this.parkServiceName = parkServiceName;
    }

    /**
     * Confirms the review and closes the screen.
     * @param event the click on the confirm button.
     */
    @FXML
    void onConfirmButtonPressed(final ActionEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        title.setText(TITLE_BASE_TEXT + " " + parkServiceName);
    }
}
