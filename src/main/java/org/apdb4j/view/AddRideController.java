package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.tableview.RideTableView;

/**
 * The FXML controller for the add ride screen.
 */
public class AddRideController extends PopupInitializer {

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
    private TextArea descriptionTextArea;
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
     * Default constructor.
     */
    public AddRideController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void editMode() {
        if (!editMode) {
            return;
        }
        nameField.setText(ride.getName());
        openingHourField.setText(String.valueOf(ride.getOpeningTime().getHour()));
        openingMinuteField.setText(String.valueOf(ride.getOpeningTime().getMinute()));
        closingHourField.setText(String.valueOf(ride.getClosingTime().getHour()));
        closingMinuteField.setText(String.valueOf(ride.getClosingTime().getMinute()));
        typeChoiceBox.setValue(ride.getType());
        intensityChoiceBox.setValue(ride.getIntensity());
        durationField.setText(String.valueOf(ride.getDuration()));
        maxSeatsSpinner.getValueFactory().setValue(ride.getMaxSeats());
        descriptionTextArea.setText(ride.getDescription());
        minHeightSpinner.getValueFactory().setValue(ride.getMinHeight());
        maxHeightSpinner.getValueFactory().setValue(ride.getMaxHeight());
        minWeightSpinner.getValueFactory().setValue(ride.getMinWeight());
        maxWeightSpinner.getValueFactory().setValue(ride.getMaxWeight());
        statusChoiceBox.setValue(ride.getStatus());
    }

}
