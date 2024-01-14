package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.staff.ExhibitionController;
import org.apdb4j.controllers.staff.ExhibitionControllerImpl;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.AttractionTableItem;
import org.apdb4j.view.staff.tableview.ExhibitionTableItem;
import org.jooq.exception.DataAccessException;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * The FXML controller for the exhibition screen.
 */
public class ExhibitionScreenController extends PopupInitializer {

    private static final ExhibitionController CONTROLLER = new ExhibitionControllerImpl();
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label dateLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label timeLabel;
    @FXML
    private Label timeLabel2;
    @FXML
    private Spinner<Integer> timeHourSpinner;
    @FXML
    private Spinner<Integer> timeMinuteSpinner;
    @FXML
    private Label maxSeatsLabel;
    @FXML
    private Spinner<Integer> maxSeatsSpinner;
    @FXML
    private Label spectatorsLabel;
    @FXML
    private Spinner<Integer> spectatorsSpinner;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static ExhibitionTableItem exhibition;
    @Setter
    private static TableView<AttractionTableItem> tableView;

    /**
     * Default constructor.
     */
    public ExhibitionScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * Adds/edits an entry in the DB.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        // TODO: use id generator.
        final ExhibitionTableItem exhibitionItem = new ExhibitionTableItem(editMode ? exhibition.getId() : "EX-000",
                nameField.getText(),
                typeComboBox.getValue(),
                descriptionTextArea.getText(),
                datePicker.getValue(),
                LocalTime.of(timeHourSpinner.getValue(), timeMinuteSpinner.getValue()),
                maxSeatsSpinner.getValue(),
                spectatorsSpinner.getValue(),
                editMode ? exhibition.getAverageRating() : 0.0,
                editMode ? exhibition.getRatings() : 0);
        final ObservableList<AttractionTableItem> tableItems = tableView.getItems();
        if (!editMode) {
            Platform.runLater(() -> {
                try {
                    tableItems.add(CONTROLLER.addData(exhibitionItem));
                } catch (final DataAccessException e) {
                    new AlertBuilder().setAlertType(Alert.AlertType.ERROR)
                            .setContentText(e.getCause().getMessage())
                            .show();
                }
            });
        } else {
            final int selectedIndex = tableItems.indexOf(exhibition);
            Platform.runLater(() -> {
                try {
                    tableItems.set(selectedIndex, CONTROLLER.editData(exhibitionItem));
                } catch (final DataAccessException | NullPointerException e) {
                    new AlertBuilder().setAlertType(Alert.AlertType.ERROR)
                            .setContentText(e.getMessage())
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
        if (!editMode) {
            List.of(dateLabel,
                    datePicker,
                    timeLabel,
                    timeLabel2,
                    timeHourSpinner,
                    timeMinuteSpinner,
                    maxSeatsLabel,
                    maxSeatsSpinner,
                    spectatorsLabel,
                    spectatorsSpinner).forEach(control -> control.setDisable(true));
            return;
        }
        nameField.setText(exhibition.getName());
        typeComboBox.setValue(exhibition.getType());
        descriptionTextArea.setText(exhibition.getDescription());
        datePicker.setValue(exhibition.getDate());
        if (Objects.nonNull(exhibition.getTime())) {
            timeHourSpinner.getValueFactory().setValue(exhibition.getTime().getHour());
            timeMinuteSpinner.getValueFactory().setValue(exhibition.getTime().getMinute());
        }
        maxSeatsSpinner.getValueFactory().setValue(exhibition.getMaxSeats());
        spectatorsSpinner.getValueFactory().setValue(exhibition.getSpectators());
    }

}
