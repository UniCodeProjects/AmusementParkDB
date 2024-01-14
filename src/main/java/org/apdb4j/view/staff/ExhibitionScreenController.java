package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.staff.ExhibitionControllerImpl;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.ExhibitionTableItem;

import java.util.List;

/**
 * The FXML controller for the exhibition screen.
 */
public class ExhibitionScreenController extends PopupInitializer {

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
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        typeComboBox.getItems().addAll(new ExhibitionControllerImpl().getExistingTypes());
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
        timeHourSpinner.getValueFactory().setValue(exhibition.getTime().getHour());
        timeMinuteSpinner.getValueFactory().setValue(exhibition.getTime().getMinute());
        maxSeatsSpinner.getValueFactory().setValue(exhibition.getMaxSeats());
        spectatorsSpinner.getValueFactory().setValue(exhibition.getSpectators());
    }

}
