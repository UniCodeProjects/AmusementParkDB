package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.ContractTableItem;
import org.apdb4j.view.staff.tableview.EmployeeTableItem;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.BiConsumer;

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
    private static boolean fromEmployeeScreen;
    private static BiConsumer<EmployeeTableItem, ContractTableItem> update;
    private static EmployeeTableItem partialEmployee;

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
     * Prepares the contract screen when it is called from the employee screen.
     * @param employee the partial employee object. It holds an invalid salary value.
     * @param action the action that adds the employee into the DB and updates the GUI.
     */
    public static void setFromEmployeeMode(final EmployeeTableItem employee,
                                           final BiConsumer<EmployeeTableItem, ContractTableItem> action) {
        fromEmployeeScreen = true;
        partialEmployee = new EmployeeTableItem(employee);
        update = action;
    }

    /**
     * Adds the new contract to the DB and displays it in the table view.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        // TODO

        if (fromEmployeeScreen) {
            if (Objects.isNull(partialEmployee)) {
                throw new IllegalStateException("Employee table item is null.");
            }
            partialEmployee.setSalary(salarySpinner.getValue());
            final ContractTableItem contract = new ContractTableItem("C-000",
                    employeeNIDField.getText(),
                    employerNIDField.getText(),
                    signedDatePicker.getValue(),
                    beginDatePicker.getValue(),
                    endDatePicker.getValue(),
                    salarySpinner.getValue());
            update.accept(partialEmployee, contract);
        }
        gridPane.getScene().getWindow().hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        beginDatePicker.setDayCellFactory(param -> new FirstDayDateCell());
        endDatePicker.setDayCellFactory(param -> new LastDayDateCell());
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

    /**
     * A date cell that enables only the first day of the month.
     */
    static class FirstDayDateCell extends DateCell {
        /**
         * {@inheritDoc}
         */
        @Override
        public void updateItem(final LocalDate item, final boolean empty) {
            super.updateItem(item, empty);
            setDisable(item != null && item.getDayOfMonth() != 1);
        }
    }

    /**
     * A date cell that enables only the last day of the month.
     */
    static class LastDayDateCell extends DateCell {
        /**
         * {@inheritDoc}
         */
        @Override
        public void updateItem(final LocalDate item, final boolean empty) {
            super.updateItem(item, empty);
            setDisable(item != null && item.getDayOfMonth() != item.lengthOfMonth());
        }
    }

}
