package org.apdb4j.view.guests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import org.apache.commons.lang3.StringUtils;
import org.apdb4j.controllers.guests.TicketController;
import org.apdb4j.controllers.guests.TicketControllerImpl;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see the price list of the current year.
 */
public class UserPriceListController extends BackableAbstractFXMLController {
    private static final String PRICE_LIST_PERIOD_BASE_TEXT = " tickets price list";
    private static final String EURO_SYMBOL = "\u20AC";
    private static final String PRICE_FORMAT_SPECIFIER = "%.2f";
    private static final int TICKET_LABEL_FONT_SIZE = 24;
    private final TicketController controller = new TicketControllerImpl();

    @FXML
    private Label priceListPeriod;
    @FXML
    private ListView<Label> ticketsAndSeasonTickets;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        priceListPeriod.setText(LocalDate.now().getYear() + PRICE_LIST_PERIOD_BASE_TEXT);
        controller.getTicketTypes().forEach(ticketType -> controller.getCustomerCategories()
                .forEach(category -> {
                    final Label ticketLabel = new Label(StringUtils.capitalize(category)
                            + " " + ticketType + ": " + EURO_SYMBOL
                            + String.format(PRICE_FORMAT_SPECIFIER, controller.getPriceForTicket(ticketType, category)));
                    ticketLabel.setFont(new Font(TICKET_LABEL_FONT_SIZE));
                    ticketsAndSeasonTickets.getItems().add(ticketLabel);
                }));
    }

    /**
     * Opens the screen that allows the user to buy new tickets.
     * @param event the click on the "buy tickets" button.
     */
    @FXML
    void onBuyTicketsButtonPressed(final ActionEvent event) {
        LoadFXML.fromEvent(event, UserTicketsChooserController.class, true, true, true, controller);
        JavaFXUtils.setStageTitle(event, "Buy tickets", true);
    }

}
