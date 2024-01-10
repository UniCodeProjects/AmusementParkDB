package org.apdb4j.view.guests;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.apdb4j.view.BackableAbstractFXMLController;

/**
 * FXML controller for the screen that allows the user to buy either tickets or season tickets, or both.
 */
public class UserTicketsChooserController extends BackableAbstractFXMLController {

    @FXML
    private ListView<Label> cart;
    @FXML
    private VBox ticketsAndSeasonTicketsContainer;
    @FXML
    private Label totalPrice;
}
