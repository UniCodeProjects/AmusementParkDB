package org.apdb4j.view.guests;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import lombok.NonNull;
import org.apdb4j.controllers.guests.ReviewController;
import org.apdb4j.util.view.AlertBuilder;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to add a new review for a park service.
 */
public class AddReviewScreenController implements Initializable {

    private static final String TITLE_BASE_TEXT = "Add your review for ";
    @FXML
    private Button confirmButton;
    @FXML
    private ToggleGroup ratingButtons;
    @FXML
    private TextArea reviewDescription;
    @FXML
    private Label title;
    private final String parkServiceName;
    private final ReviewController controller;

    /**
     * Creates a new instance of this class which refers to the park service {@code parkServiceName} and whose MVC controller
     * is {@code controller}.
     * @param parkServiceName the name of the park service referred by the scene.
     * @param controller the MVC controller.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public AddReviewScreenController(final @NonNull String parkServiceName, final @NonNull ReviewController controller) {
        this.parkServiceName = parkServiceName;
        this.controller = controller;
    }

    /**
     * Confirms the review and closes the screen.
     * @param event the click on the confirm button.
     */
    @FXML
    void onConfirmButtonPressed(final ActionEvent event) {
        final boolean reviewAdded = controller.addReview(
                Integer.parseInt(((RadioButton) ratingButtons.getSelectedToggle()).getText()),
                reviewDescription.getText().isBlank() ? null : reviewDescription.getText());
        if (!reviewAdded) {
            new AlertBuilder(Alert.AlertType.ERROR)
                    .setContentText("An error occurred while inserting your review. Please try again.")
                    .show();
        } else {
            new AlertBuilder(Alert.AlertType.INFORMATION).setContentText("Review added successfully").show();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        title.setText(TITLE_BASE_TEXT + parkServiceName);
        confirmButton.setDisable(true);
        ratingButtons.selectedToggleProperty().addListener((observableValue, oldValue, newValue) ->
                confirmButton.setDisable(newValue == null));
    }
}
