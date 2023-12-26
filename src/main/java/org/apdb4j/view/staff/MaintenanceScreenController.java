package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.MaintenanceTableItem;

/**
 * The FXML controller for the maintenance screen.
 */
public class MaintenanceScreenController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private Spinner<Double> priceSpinner;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea employeeIDsTextArea;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static MaintenanceTableItem maintenance;

    /**
     * Default constructor.
     */
    public MaintenanceScreenController() {
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
        priceSpinner.getValueFactory().setValue(maintenance.getPrice());
        descriptionTextArea.setText(maintenance.getDescription());
        datePicker.setValue(maintenance.getDate());
        employeeIDsTextArea.setText(maintenance.getEmployeeIDs());
    }

}
