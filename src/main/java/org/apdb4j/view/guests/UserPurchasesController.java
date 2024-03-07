package org.apdb4j.view.guests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.apdb4j.controllers.guests.TicketType;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableAbstractFXMLController;

/**
 * FXML controller for the scene that allows the user to see their purchases in the park.
 */
public class UserPurchasesController extends BackableAbstractFXMLController {

    /**
     * Opens the screen that contains all the information about the single day tickets bought by the user so far.
     * @param event the event.
     */
    @FXML
    void onTicketsButtonPressed(final ActionEvent event) {
        LoadFXML.fromEvent(event, TicketsInfoController.class, true, true, true, TicketType.SINGLE_DAY_TICKET);
        JavaFXUtils.setStageTitle(event, "My tickets", true);
    }

    /**
     * Opens the screen that contains all the information about the season tickets bought by the user so far.
     * @param event the event.
     */
    @FXML
    void onSeasonTicketsButtonPressed(final ActionEvent event) {
        LoadFXML.fromEvent(event, TicketsInfoController.class, true, true, true, TicketType.SEASON_TICKET);
        JavaFXUtils.setStageTitle(event, "My season tickets", true);
    }
}
