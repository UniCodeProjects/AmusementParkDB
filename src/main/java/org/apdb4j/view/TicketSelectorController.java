package org.apdb4j.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Setter;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.tableview.TicketTableView;

import java.util.Objects;

/**
 * The FXML controller for the selector in the Ticket tab that
 * appears after an event is triggered (e.g. a button press).
 */
public class TicketSelectorController extends PopupInitializer {

    @FXML
    private Button ticketBtn;
    @FXML
    private Button ticketTypeBtn;
    @FXML
    private Button priceListBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static TicketTableView ticket;

    /**
     * Default constructor.
     */
    public TicketSelectorController() {
        Platform.runLater(() -> {
            super.setStage(ticketBtn.getScene().getWindow());
            super.setRoot(ticketBtn.getScene().getRoot());
        });
    }

    /**
     * Opens the scene to allow the insertion of a new ticket.
     * @param event the event
     */
    @FXML
    void onTicketBtnPress(final ActionEvent event) {
        if (editMode) {
            AddTicketController.setEditMode(true);
            if (Objects.isNull(ticket)) {
                StaffScreenController.showAlertForUnselectedRowInTableView("ticket");
                return;
            }
            AddTicketController.setTicket(ticket);
        } else {
            AddTicketController.setEditMode(false);
        }
        LoadFXML.fromEventAsPopup(event, "layouts/add-ticket.fxml", "Add ticket");
    }

    /**
     * Opens the scene to allow the insertion of a new ticket type.
     * @param event the event
     */
    @FXML
    void onTicketTypeBtnPress(final ActionEvent event) {
        if (editMode) {
            AddTicketTypeController.setEditMode(true);
            // TODO: complete
            throw new UnsupportedOperationException();
        } else {
            AddTicketTypeController.setEditMode(false);
        }
        LoadFXML.fromEventAsPopup(event, "layouts/add-ticket-type.fxml", "Add ticket type");
    }

    /**
     * Opens the scene to allow the insertion of a new price list.
     * @param event the event
     */
    @FXML
    void onPriceListBtnPress(final ActionEvent event) {
        if (editMode) {
            AddTicketPriceController.setEditMode(true);
            // TODO: complete
            throw new UnsupportedOperationException();
        } else {
            AddTicketPriceController.setEditMode(false);
        }
        LoadFXML.fromEventAsPopup(event, "layouts/add-ticket-price.fxml", "Add ticket price");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void editMode() {
    }

}
