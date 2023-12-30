package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import lombok.NonNull;
import lombok.Setter;
import org.apdb4j.controllers.EmployeeControllerImpl;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.EmployeeTableItem;

/**
 * The FXML controller for the employee hiring screen.
 */
public class EmployeeScreenController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nationalIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private TextField birthplaceField;
    @FXML
    private ToggleGroup genderToggleGroup;
    @FXML
    private RadioButton maleRadioBtn;
    @FXML
    private RadioButton femaleRadioBtn;
    @FXML
    private TextField roleField;
    @FXML
    private ToggleGroup accountTypeToggleGroup;
    @FXML
    private RadioButton adminRadioBtn;
    @FXML
    private RadioButton employeeRadioBtn;
    @FXML
    private TextField emailField;
    @FXML
    private Button acceptAndCloseBtn;

    @Setter
    private static boolean fromHire;
    @Setter
    private static boolean editMode;
    @Setter
    private static EmployeeTableItem employee;
    @Setter
    private static TableView<EmployeeTableItem> tableView;

    /**
     * Default constructor.
     */
    public EmployeeScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * Adds the new employee into the DB and displays the new row in the table view.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        if (editMode) {
            final var editedEmployee = new EmployeeTableItem(
                    employee.getStaffID(),
                    nationalIDField.getText(),
                    nameField.getText(),
                    surnameField.getText(),
                    dobPicker.getValue(),
                    birthplaceField.getText(),
                    maleRadioBtn.isSelected() ? "M" : "F",
                    roleField.getText(),
                    adminRadioBtn.isSelected(),
                    employee.getSalary(),
                    emailField.getText()
            );
            gridPane.getScene().getWindow().hide();
            Platform.runLater(() -> {
                final EmployeeTableItem currentEmployee = tableView.getItems().get(tableView.getItems().indexOf(employee));
                final int index = tableView.getItems().indexOf(currentEmployee);
                tableView.getItems().remove(currentEmployee);
                tableView.getItems().add(index, new EmployeeControllerImpl().editData(editedEmployee));
                tableView.getSelectionModel().select(editedEmployee);
                tableView.requestFocus();
            });
            return;
        }
        final var newEmployee = new EmployeeTableItem(
                "E-000",    // TODO: use the ID generator.
                nationalIDField.getText(),
                nameField.getText(),
                surnameField.getText(),
                dobPicker.getValue(),
                birthplaceField.getText(),
                maleRadioBtn.isSelected() ? "M" : "F",
                roleField.getText(),
                adminRadioBtn.isSelected(),
                -1,
                emailField.getText()
        );
        gridPane.getScene().getWindow().hide();
        // Opening the add-contract form.
        if (fromHire) {
            ContractScreenController.setEditMode(false);
            ContractScreenController.setFromEmployeeMode(newEmployee, (e, c) -> Platform.runLater(() ->
                    tableView.getItems().add((EmployeeTableItem) new EmployeeControllerImpl().addData(e, c))));
            LoadFXML.fromEventAsPopup(event, "layouts/contract-form.fxml", "Add contract");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        adminRadioBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            roleField.setDisable(adminRadioBtn.isSelected());
            if (roleField.isDisable()) {
                roleField.clear();
            }
        });
        if (!editMode) {
            return;
        }
        nationalIDField.setText(employee.getNationalID());
        nameField.setText(employee.getName());
        surnameField.setText(employee.getSurname());
        dobPicker.setValue(employee.getDob());
        birthplaceField.setText(employee.getBirthplace());
        setGenderToggle(employee.getGender());
        roleField.setText(employee.getRole());
        adminRadioBtn.setSelected(employee.isAdmin());
        employeeRadioBtn.setSelected(!employee.isAdmin());
        emailField.setText(employee.getEmail());
        emailField.setDisable(true);
    }

    private void setGenderToggle(final @NonNull String gender) {
        switch (gender) {
            case "M" -> maleRadioBtn.setSelected(true);
            case "F" -> femaleRadioBtn.setSelected(true);
            default -> throw new IllegalStateException(gender + " is invalid.");
        }
    }

}
