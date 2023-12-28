package org.apdb4j.view;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see both tickets and season tickets bought.
 */
public class UserTicketsScreenController extends BackableAbstractFXMLController {

    @FXML
    private ListView<Hyperlink> listView;

    @FXML
    private BorderPane pane;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        addTicket();
    }

    private void addTicket() {
        final var ticket = new Hyperlink("3 tickets, valid on 25/04");
        ticket.setFocusTraversable(false);
        ticket.setOnAction(event ->
                LoadFXML.fromEventAsPopup(event, "layouts/tickets-info.fxml", "tickets valid on 25/04", 0.5, 0.5));
        listView.getItems().add(ticket);
    }
}
