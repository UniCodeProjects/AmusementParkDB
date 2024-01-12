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
import org.apdb4j.view.staff.tableview.ExhibitionTableItem;

/**
 * The FXML controller for the exhibition screen.
 */
public class ExhibitionScreenController extends PopupInitializer {

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
    private Spinner<Integer> timeHourSpinner;
    @FXML
    private Spinner<Integer> timeMinuteSpinner;
    @FXML
    private Spinner<Integer> maxSeatsSpinner;
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
        if (!editMode) {
            return;
        }
        nameField.setText(exhibition.getName());
        typeChoiceBox.setValue(exhibition.getType());
        descriptionTextArea.setText(exhibition.getDescription());
        datePicker.setValue(exhibition.getDate());
        timeHourSpinner.getValueFactory().setValue(exhibition.getTime().getHour());
        timeMinuteSpinner.getValueFactory().setValue(exhibition.getTime().getMinute());
        maxSeatsSpinner.getValueFactory().setValue(exhibition.getMaxSeats());
        spectatorsSpinner.getValueFactory().setValue(exhibition.getSpectators());
    }

}
