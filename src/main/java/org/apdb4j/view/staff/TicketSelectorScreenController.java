package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Setter;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.TicketTableItem;

import java.util.Objects;

/**
 * The FXML controller for the selector in the Ticket tab that
 * appears after an event is triggered (e.g. a button press).
 */
public class TicketSelectorScreenController extends PopupInitializer {

    @FXML
    private Button ticketBtn;
    @FXML
    private Button ticketTypeBtn;
    @FXML
    private Button priceListBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static TicketTableItem ticket;

    /**
     * Default constructor.
     */
    public TicketSelectorScreenController() {
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
            TicketScreenController.setEditMode(true);
            if (Objects.isNull(ticket)) {
                StaffScreenController.showAlertForUnselectedRowInTableView("ticket");
                return;
            }
            TicketScreenController.setTicket(ticket);
        } else {
            TicketScreenController.setEditMode(false);
        }
        LoadFXML.fromEventAsPopup(event, "layouts/ticket-form.fxml", "Add ticket");
    }

    /**
     * Opens the scene to allow the insertion of a new ticket type.
     * @param event the event
     */
    @FXML
    void onTicketTypeBtnPress(final ActionEvent event) {
        if (editMode) {
            TicketTypeScreenController.setEditMode(true);
            // TODO: complete
            throw new UnsupportedOperationException();
        } else {
            TicketTypeScreenController.setEditMode(false);
        }
        LoadFXML.fromEventAsPopup(event, "layouts/ticket-type-form.fxml", "Add ticket type");
    }

    /**
     * Opens the scene to allow the insertion of a new price list.
     * @param event the event
     */
    @FXML
    void onPriceListBtnPress(final ActionEvent event) {
        if (editMode) {
            TicketPriceScreenController.setEditMode(true);
            // TODO: complete
            throw new UnsupportedOperationException();
        } else {
            TicketPriceScreenController.setEditMode(false);
        }
        LoadFXML.fromEventAsPopup(event, "layouts/ticket-price-form.fxml", "Add ticket price");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
    }

}
