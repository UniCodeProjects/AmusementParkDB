package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.staff.RideController;
import org.apdb4j.controllers.staff.RideControllerImpl;
import org.apdb4j.util.IDGenerationUtils;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.view.FXMLController;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.AttractionTableItem;
import org.apdb4j.view.staff.tableview.RideTableItem;
import org.jooq.exception.DataAccessException;

import java.time.LocalTime;
import java.util.Locale;

/**
 * The FXML controller for the ride screen.
 */
public class RideScreenController extends PopupInitializer implements FXMLController {

    private static final RideController CONTROLLER = new RideControllerImpl();
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
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<String> intensityComboBox;
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
    private ToggleGroup statusToggleGroup;
    @FXML
    private RadioButton operatingBtn;
    @FXML
    private RadioButton maintenanceBtn;
    @FXML
    private RadioButton closedBtn;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static RideTableItem ride;
    @Setter
    private static TableView<AttractionTableItem> tableView;

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
     * Adds/edits the entry in the DB.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        final RideTableItem rideItem = new RideTableItem(editMode ? ride.getId() : IDGenerationUtils.generateRideID(),
                nameField.getText(),
                LocalTime.of(openingHourSpinner.getValue(), openingMinuteSpinner.getValue()),
                LocalTime.of(closingHourSpinner.getValue(), closingMinuteSpinner.getValue()),
                typeComboBox.getSelectionModel().getSelectedItem(),
                intensityComboBox.getSelectionModel().getSelectedItem(),
                durationSpinner.getValue(),
                maxSeatsSpinner.getValue(),
                descriptionTextArea.getText(),
                minHeightSpinner.getValue(),
                maxHeightSpinner.getValue(),
                minWeightSpinner.getValue(),
                maxWeightSpinner.getValue(),
                getStatus(((RadioButton) statusToggleGroup.getSelectedToggle()).getText()),
                editMode ? ride.getAverageRating() : 0.0,
                editMode ? ride.getRatings() : 0);
        final ObservableList<AttractionTableItem> tableItems = tableView.getItems();
        if (!editMode) {
            Platform.runLater(() -> {
                try {
                    tableItems.add(CONTROLLER.addData(rideItem));
                } catch (final DataAccessException e) {
                    new AlertBuilder(Alert.AlertType.ERROR)
                            .setContentText(e.getCause().getMessage())
                            .show();
                }
            });
        } else {
            final int selectedIndex = tableItems.indexOf(ride);
            Platform.runLater(() -> {
                try {
                    tableItems.set(selectedIndex, CONTROLLER.editData(rideItem));
                } catch (final DataAccessException e) {
                    new AlertBuilder(Alert.AlertType.ERROR)
                            .setContentText(e.getCause().getMessage())
                            .show();
                }
                tableView.getSelectionModel().select(selectedIndex);
            });
        }
        gridPane.getScene().getWindow().hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        typeComboBox.getItems().addAll(CONTROLLER.getExistingTypes());
        intensityComboBox.getItems().addAll(CONTROLLER.getExistingIntensities());
        if (!editMode) {
            return;
        }
        nameField.setText(ride.getName());
        openingHourSpinner.getValueFactory().setValue(ride.getOpeningTime().getHour());
        openingMinuteSpinner.getValueFactory().setValue(ride.getOpeningTime().getMinute());
        closingHourSpinner.getValueFactory().setValue(ride.getClosingTime().getHour());
        closingMinuteSpinner.getValueFactory().setValue(ride.getClosingTime().getMinute());
        typeComboBox.setValue(ride.getType());
        intensityComboBox.setValue(ride.getIntensity());
        durationSpinner.getValueFactory().setValue(ride.getDuration());
        maxSeatsSpinner.getValueFactory().setValue(ride.getMaxSeats());
        descriptionTextArea.setText(ride.getDescription());
        minHeightSpinner.getValueFactory().setValue(ride.getMinHeight());
        maxHeightSpinner.getValueFactory().setValue(ride.getMaxHeight());
        minWeightSpinner.getValueFactory().setValue(ride.getMinWeight());
        maxWeightSpinner.getValueFactory().setValue(ride.getMaxWeight());
        selectStatusRadioButton(ride.getStatus());
    }

    private String getStatus(final String label) {
        return switch (label.toLowerCase(Locale.ROOT)) {
            case "operating" -> "O";
            case "on maintenance" -> "M";
            case "closed" -> "C";
            default -> throw new IllegalArgumentException("Unknown radio button label; " + label);
        };
    }

    private void selectStatusRadioButton(final String status) {
        switch (status) {
            case "O" -> operatingBtn.setSelected(true);
            case "M" -> maintenanceBtn.setSelected(true);
            case "C" -> closedBtn.setSelected(true);
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        }
    }

}
