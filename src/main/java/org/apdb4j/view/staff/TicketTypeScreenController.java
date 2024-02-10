package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.staff.TicketTypeController;
import org.apdb4j.controllers.staff.TicketTypeControllerImpl;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.view.FXMLController;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.TicketTypeTableItem;
import org.jooq.exception.DataAccessException;

import java.time.Year;

/**
 * The FXML controller for the ticket type screen.
 */
public class TicketTypeScreenController extends PopupInitializer implements FXMLController {

    @FXML
    private GridPane gridPane;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private Spinner<Double> priceSpinner;
    @FXML
    private Spinner<Integer> yearSpinner;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private Spinner<Integer> durationSpinner;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static TicketTypeTableItem ticketType;
    @Setter
    private static TableView<TicketTypeTableItem> tableView;
    private final TicketTypeController controller = new TicketTypeControllerImpl();

    /**
     * Default constructor.
     */
    public TicketTypeScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * Adds/edits the ticket type entry in the DB.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        final TicketTypeTableItem newTicketType = new TicketTypeTableItem(typeChoiceBox.getValue(),
                categoryChoiceBox.getValue(),
                Year.of(yearSpinner.getValue()),
                priceSpinner.getValue(),
                durationSpinner.getValue());
        if (!editMode) {
            final TicketTypeTableItem added;
            try {
                added = controller.addData(newTicketType);
            } catch (final DataAccessException e) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText(e.getMessage())
                        .show();
                return;
            }
            Platform.runLater(() -> tableView.getItems().add(added));
        } else {
            final TicketTypeTableItem edited;
            try {
                edited = controller.editData(newTicketType);
            } catch (final DataAccessException e) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText(e.getMessage())
                        .show();
                return;
            }
            final int index = tableView.getItems().indexOf(ticketType);
            Platform.runLater(() -> tableView.getItems().set(index, edited));
        }
        gridPane.getScene().getWindow().hide();
    }

    /**
     * Disables the duration spinner based on the chosen ticket type.
     * @param event the event
     */
    @FXML
    void onTypeSelect(final ActionEvent event) {
        switch (typeChoiceBox.getValue()) {
            case "single day ticket" -> {
                durationSpinner.setDisable(true);
                durationSpinner.getValueFactory().setValue(1);
            }
            case "season ticket" -> durationSpinner.setDisable(false);
            default -> throw new IllegalStateException("Unknown type was chosen.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        typeChoiceBox.getItems().addAll(controller.getAllTicketTypes());
        categoryChoiceBox.getItems().addAll(controller.getAllTicketTypeCategories());
        if (!editMode) {
            return;
        }
        typeChoiceBox.setDisable(true);
        yearSpinner.setDisable(true);
        categoryChoiceBox.setDisable(true);
        durationSpinner.setDisable(true);
        typeChoiceBox.setValue(ticketType.getType());
        priceSpinner.getValueFactory().setValue(ticketType.getPrice());
        yearSpinner.getValueFactory().setValue(ticketType.getYear().getValue());
        categoryChoiceBox.setValue(ticketType.getCategory());
        durationSpinner.getValueFactory().setValue(ticketType.getDuration());
    }

}
