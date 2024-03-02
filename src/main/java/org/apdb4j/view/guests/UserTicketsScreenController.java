package org.apdb4j.view.guests;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import lombok.NonNull;
import org.apdb4j.controllers.guests.BoughtTicketsController;
import org.apdb4j.controllers.guests.BoughtTicketsControllerImpl;
import org.apdb4j.controllers.guests.TicketType;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see both tickets and season tickets bought.
 */
public class UserTicketsScreenController extends BackableAbstractFXMLController {

    @FXML
    private ListView<Hyperlink> listView;
    private final BoughtTicketsController controller;
    private final TicketType ticketType;

    /**
     * Creates a new instance of this class that will refer to the tickets of the provided type.
     * @param ticketType the ticket type that the new instance will be able to handle.
     */
    public UserTicketsScreenController(final @NonNull TicketType ticketType) {
        this.ticketType = ticketType;
        controller = new BoughtTicketsControllerImpl(ticketType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        controller.getNumberOfBoughtTickets().forEach(dateWithNumber -> {
            final var hyperlink = new Hyperlink(dateWithNumber.getRight() + " "
                    + ticketType.getName() + "s, "
                    + (ticketType.equals(TicketType.SINGLE_DAY_TICKET) ? "valid on " : "valid until ")
                    + dateWithNumber.getLeft());
            hyperlink.setFocusTraversable(false);
            listView.getItems().add(hyperlink);
        });
    }
}
