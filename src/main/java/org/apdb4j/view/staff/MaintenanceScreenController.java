package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.staff.MaintenanceController;
import org.apdb4j.controllers.staff.MaintenanceControllerImpl;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.view.FXMLController;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.MaintenanceTableItem;
import org.jooq.exception.DataAccessException;

/**
 * The FXML controller for the maintenance screen.
 */
public class MaintenanceScreenController extends PopupInitializer implements FXMLController {

    @FXML
    private TextField facilityIDField;
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
    @Setter
    private static TableView<MaintenanceTableItem> tableView;

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
     * Adds/edits the new maintenance into the DB and adds/edits the tableview row.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        final MaintenanceTableItem maintenanceItem = new MaintenanceTableItem(
                editMode ? maintenance.getFacilityID() : facilityIDField.getText(),
                priceSpinner.getValue(),
                descriptionTextArea.getText(),
                datePicker.getValue(),
                employeeIDsTextArea.getText());
        final MaintenanceController controller = new MaintenanceControllerImpl();
        if (!editMode) {
            Platform.runLater(() -> {
                try {
                    tableView.getItems().add(controller.addData(maintenanceItem));
                } catch (final DataAccessException e) {
                    new AlertBuilder(Alert.AlertType.ERROR)
                            .setContentText(e.getMessage())
                            .show();
                }
            });
        } else {
            final int selectedIndex = tableView.getItems().indexOf(maintenance);
            try {
                tableView.getItems().set(selectedIndex, controller.editData(maintenanceItem));
            } catch (final DataAccessException e) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText(e.getMessage())
                        .show();
            }
            Platform.runLater(() -> {
                tableView.getItems().clear();
                tableView.getItems().addAll(controller.getData());
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
        if (!editMode) {
            return;
        }
        facilityIDField.setText(maintenance.getFacilityID());
        facilityIDField.setDisable(true);
        priceSpinner.getValueFactory().setValue(maintenance.getPrice());
        descriptionTextArea.setText(maintenance.getDescription());
        datePicker.setValue(maintenance.getDate());
        datePicker.setDisable(true);
        employeeIDsTextArea.setDisable(true);
        employeeIDsTextArea.setText(maintenance.getEmployeeIDs());
    }

}
