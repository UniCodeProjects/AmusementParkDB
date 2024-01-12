package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.RideTableItem;

/**
 * The FXML controller for the ride screen.
 */
public class RideScreenController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> openingHourSpinner;
    @FXML
    private Spinner<Integer> openingMinuteSpinner;
    @FXML
    private Spinner<Integer> closingHourSpinner;
    @FXML
    private Spinner<Integer> closingMinuteSpinner;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ChoiceBox<String> intensityChoiceBox;
    @FXML
    private Spinner<Integer> durationSpinner;
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
    private static RideTableItem ride;

    /**
     * Default constructor.
     */
    public RideScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
            super.setHeightSizeFactor(1.4);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        if (!editMode) {
            return;
        }
        nameField.setText(ride.getName());
        openingHourSpinner.getValueFactory().setValue(ride.getOpeningTime().getHour());
        openingMinuteSpinner.getValueFactory().setValue(ride.getOpeningTime().getMinute());
        closingHourSpinner.getValueFactory().setValue(ride.getClosingTime().getHour());
        closingMinuteSpinner.getValueFactory().setValue(ride.getClosingTime().getMinute());
        typeChoiceBox.setValue(ride.getType());
        intensityChoiceBox.setValue(ride.getIntensity());
        durationSpinner.getValueFactory().setValue(ride.getDuration());
        maxSeatsSpinner.getValueFactory().setValue(ride.getMaxSeats());
        descriptionTextArea.setText(ride.getDescription());
        minHeightSpinner.getValueFactory().setValue(ride.getMinHeight());
        maxHeightSpinner.getValueFactory().setValue(ride.getMaxHeight());
        minWeightSpinner.getValueFactory().setValue(ride.getMinWeight());
        maxWeightSpinner.getValueFactory().setValue(ride.getMaxWeight());
        statusChoiceBox.setValue(ride.getStatus());
    }

}
