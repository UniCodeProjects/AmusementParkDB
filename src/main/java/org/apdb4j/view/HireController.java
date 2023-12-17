package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import org.apdb4j.view.tableview.EmployeeTableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the employee hiring screen.
 */
public class HireController implements Initializable {

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
    private TextField emailField;

    @Setter
    private static boolean editMode;
    @Setter
    private static EmployeeTableView employee;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            final var stage = safeCastToStage(gridPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setHeight(gridPane.getHeight());
            if (editMode) {
                nationalIDField.setText(employee.getNationalID());
                nameField.setText(employee.getName());
                surnameField.setText(employee.getSurname());
                dobPicker.setValue(employee.getDob());
                birthplaceField.setText(employee.getBirthplace());
                genderChoiceBox.setValue(employee.getGender());
                roleField.setText(employee.getRole());
                adminRadioBtn.setSelected(employee.isAdmin());
                employeeRadioBtn.setSelected(!employee.isAdmin());
                emailField.setText(employee.getEmail());
            }
        });
    }

    private Stage safeCastToStage(final Window window) {
        Stage stage;
        if (window instanceof Stage) {
            stage = (Stage) window;
        } else {
            throw new IllegalStateException("Failed cast: the given window is not a Stage instance");
        }
        return stage;
    }

}
