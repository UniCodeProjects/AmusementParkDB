package org.apdb4j.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the selector in the Ticket tab that
 * appears after an event is triggered (e.g. a button press).
 */
public class TicketSelectorController implements Initializable {

    @FXML
    private Button ticketBtn;
    @FXML
    private Button ticketTypeBtn;
    @FXML
    private Button priceListBtn;
    @Setter
    @Getter
    private static boolean editMode;

    /**
     * Opens the scene to allow the insertion of a new ticket.
     * @param event the event
     */
    @FXML
    void onTicketBtnPress(final ActionEvent event) {
        LoadFXML.fromEventAsPopup(event, "layouts/add-ticket.fxml", "Add ticket", 1.2, 1.3);
    }

    /**
     * Opens the scene to allow the insertion of a new ticket type.
     * @param event the event
     */
    @FXML
    void onTicketTypeBtnPress(final ActionEvent event) {
        LoadFXML.fromEventAsPopup(event, "layouts/add-ticket-type.fxml", "Add ticket type", 1.2, 1.3);
    }

    /**
     * Opens the scene to allow the insertion of a new price list.
     * @param event the event
     */
    @FXML
    void onPriceListBtnPress(final ActionEvent event) {
        LoadFXML.fromEventAsPopup(event, "layouts/add-ticket-price.fxml", "Add ticket price", 1.2, 1.3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            final var scene = safeCastToStage(ticketBtn.getScene().getWindow());
            scene.setResizable(false);
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
