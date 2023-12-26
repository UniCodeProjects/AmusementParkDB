package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.EmployeeControllerImpl;
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
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private TextField roleField;
    @FXML
    private ToggleGroup accountTypeToggleGroup;
    @FXML
    private RadioButton adminRadioBtn;
    @FXML
    private RadioButton employeeRadioBtn;
    @FXML
    private Spinner<Double> salarySpinner;
    @FXML
    private TextField emailField;
    @FXML
    private Button acceptAndCloseBtn;

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
        final var employee = new EmployeeTableItem(
                "B-002",    // TODO: use the ID generator.
                nationalIDField.getText(),
                nameField.getText(),
                surnameField.getText(),
                dobPicker.getValue(),
                birthplaceField.getText(),
                genderChoiceBox.getValue(),
                roleField.getText(),
                adminRadioBtn.isSelected(),
                salarySpinner.getValue(),
                emailField.getText()
        );
        Platform.runLater(() -> tableView.getItems().add(new EmployeeControllerImpl().addData(employee)));
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
        nationalIDField.setText(employee.getNationalID());
        nameField.setText(employee.getName());
        surnameField.setText(employee.getSurname());
        dobPicker.setValue(employee.getDob());
        birthplaceField.setText(employee.getBirthplace());
        genderChoiceBox.setValue(employee.getGender());
        roleField.setText(employee.getRole());
        adminRadioBtn.setSelected(employee.isAdmin());
        employeeRadioBtn.setSelected(!employee.isAdmin());
        salarySpinner.getValueFactory().setValue(employee.getSalary());
        emailField.setText(employee.getEmail());
    }

}
