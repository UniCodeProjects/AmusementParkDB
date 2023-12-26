package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.ContractTableItem;

/**
 * The FXML controller for the maintenance screen.
 */
public class ContractScreenController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField employeeNIDField;
    @FXML
    private TextField employerNIDField;
    @FXML
    private DatePicker signedDatePicker;
    @FXML
    private DatePicker beginDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Spinner<Double> salarySpinner;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static ContractTableItem contract;

    /**
     * Default constructor.
     */
    public ContractScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * Adds the new contract to the DB and displays it in the table view.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        // TODO
        gridPane.getScene().getWindow().hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void editMode() {
        if (!editMode) {
            return;
        }
        employeeNIDField.setText(contract.getEmployeeNID());
        employerNIDField.setText(contract.getEmployerNID());
        signedDatePicker.setValue(contract.getSignedDate());
        beginDatePicker.setValue(contract.getBeginDate());
        endDatePicker.setValue(contract.getEndDate());
        salarySpinner.getValueFactory().setValue(contract.getSalary());
    }

}
