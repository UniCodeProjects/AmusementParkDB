package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import org.apdb4j.view.tableview.RideTableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the add ride screen.
 */
public class AddRideController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nameField;
    @FXML
    private TextField openingHourField;
    @FXML
    private TextField openingMinuteField;
    @FXML
    private TextField closingHourField;
    @FXML
    private TextField closingMinuteField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ChoiceBox<String> intensityChoiceBox;
    @FXML
    private TextField durationField;
    @FXML
    private Spinner<Integer> maxSeatsSpinner;
    @FXML
    private TextField descriptionField;
    @FXML
    private Spinner<Integer> minHeightSpinner;
    @FXML
    private Spinner<Integer> maxHeightSpinner;
    @FXML
    private Spinner<Double> minWeightSpinner;
    @FXML
    private Spinner<Double> maxWeightSpinner;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static RideTableView ride;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            final var stage = safeCastToStage(gridPane.getScene().getWindow());
            stage.setResizable(false);
            if (editMode) {
                nameField.setText(ride.getName());
                openingHourField.setText(String.valueOf(ride.getOpeningTime().getHour()));
                openingMinuteField.setText(String.valueOf(ride.getOpeningTime().getMinute()));
                closingHourField.setText(String.valueOf(ride.getClosingTime().getHour()));
                closingMinuteField.setText(String.valueOf(ride.getClosingTime().getMinute()));
                typeChoiceBox.setValue(ride.getType());
                intensityChoiceBox.setValue(ride.getIntensity());
                durationField.setText(String.valueOf(ride.getDuration()));
                maxSeatsSpinner.getValueFactory().setValue(ride.getMaxSeats());
                descriptionField.setText(ride.getDescription());
                minHeightSpinner.getValueFactory().setValue(ride.getMinHeight());
                maxHeightSpinner.getValueFactory().setValue(ride.getMaxHeight());
                minWeightSpinner.getValueFactory().setValue(ride.getMinWeight());
                maxWeightSpinner.getValueFactory().setValue(ride.getMaxWeight());
                statusChoiceBox.setValue(ride.getStatus());
            }
        });
    }

    private Stage safeCastToStage(final Window window) {
        Stage stage;
        if (window instanceof Stage) {
            stage = (Stage) window;
        } else {
            throw new IllegalStateException("Failed cast: the given window is not a Stage instance");
        }
        return stage;
    }

}
