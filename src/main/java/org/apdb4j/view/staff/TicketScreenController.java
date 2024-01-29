package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.staff.TicketController;
import org.apdb4j.controllers.staff.TicketControllerImpl;
import org.apdb4j.controllers.staff.TicketTypeControllerImpl;
import org.apdb4j.util.IDGenerationUtils;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.TicketTableItem;
import org.jooq.exception.DataAccessException;

import java.time.LocalDate;

/**
 * The FXML controller for the ticket screen.
 */
public class TicketScreenController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private DatePicker purchaseDatePicker;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TextField ownerIDField;
    @FXML
    private DatePicker validOnDatePicker;
    @FXML
    private DatePicker validUntilDatePicker;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static TicketTableItem ticket;
    @Setter
    private static TableView<TicketTableItem> tableView;

    /**
     * Default constructor.
     */
    public TicketScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * Adds/edits the ticket in the DB.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        final TicketTableItem newTicket = new TicketTableItem(editMode
                ? ticket.getTicketID()
                : IDGenerationUtils.generateTicketID(),
                purchaseDatePicker.getValue(),
                validOnDatePicker.getValue(),
                validUntilDatePicker.getValue(),
                -1,
                ownerIDField.getText(),
                null,
                null,
                categoryChoiceBox.getValue(),
                null);
        final TicketController controller = new TicketControllerImpl();
        if (!editMode) {
            try {
                controller.addData(newTicket);
            } catch (final DataAccessException e) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText(e.getMessage())
                        .show();
                return;
            }
        } else {
            try {
                controller.editData(newTicket);
            } catch (final DataAccessException e) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText(e.getMessage())
                        .show();
                return;
            }
        }
        tableView.getItems().clear();
        Platform.runLater(() -> tableView.getItems().addAll(controller.getData()));
        gridPane.getScene().getWindow().hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        validOnDatePicker.getEditor().textProperty().addListener((observableValue, oldValue, newValue) ->
                validUntilDatePicker.setDisable(!newValue.isEmpty()));
        validUntilDatePicker.getEditor().textProperty().addListener((observableValue, oldValue, newValue) ->
                validOnDatePicker.setDisable(!newValue.isEmpty()));
        categoryChoiceBox.getItems().addAll(new TicketTypeControllerImpl().getAllTicketTypeCategories());
        if (!editMode) {
            purchaseDatePicker.setDisable(true);
            purchaseDatePicker.setValue(LocalDate.now());
            return;
        }
        purchaseDatePicker.setValue(ticket.getPurchaseDate());
        validOnDatePicker.setValue(ticket.getValidOn());
        validUntilDatePicker.setValue(ticket.getValidUntil());
        ownerIDField.setText(ticket.getOwnerID());
        categoryChoiceBox.setValue(ticket.getCategory());
    }

}
