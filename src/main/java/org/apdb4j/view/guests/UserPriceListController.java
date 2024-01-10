package org.apdb4j.view.guests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    }

    /**
     * Opens the screen that allows the user to buy new tickets.
     * @param event the click on the "buy tickets" button.
     */
    @FXML
    void onBuyTicketsButtonPressed(final ActionEvent event) {
        LoadFXML.fromEvent(event, "layouts/user-tickets-chooser.fxml", true, true, true);
        JavaFXUtils.setStageTitle(event, "Buy tickets");
    }

}
