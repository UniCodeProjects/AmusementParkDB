package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.ExhibitionTableView;

/**
 * The FXML controller for the exhibition screen.
 */
public class ExhibitionController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeHourField;
    @FXML
    private TextField timeMinuteField;
    @FXML
    private Spinner<Integer> maxSeatsSpinner;
    @FXML
    private Spinner<Integer> spectatorsSpinner;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static ExhibitionTableView exhibition;

    /**
     * Default constructor.
     */
    public ExhibitionController() {
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
        nameField.setText(exhibition.getName());
        typeChoiceBox.setValue(exhibition.getType());
        descriptionTextArea.setText(exhibition.getDescription());
        datePicker.setValue(exhibition.getDate());
        timeHourField.setText(String.valueOf(exhibition.getTime().getHour()));
        timeMinuteField.setText(String.valueOf(exhibition.getTime().getMinute()));
        maxSeatsSpinner.getValueFactory().setValue(exhibition.getMaxSeats());
        spectatorsSpinner.getValueFactory().setValue(exhibition.getSpectators());
    }

}
