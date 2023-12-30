package org.apdb4j.view.guests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import org.apdb4j.view.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see more information on the tickets bought.
 */
public class TicketsInfoController implements FXMLController, Initializable {
    private static final double TICKETS_INFO_FONT_SIZE = 20;

    @FXML
    private Label dateInfo;

    @FXML
    private ListView<BorderPane> listView;

    @FXML
    private BorderPane pane;


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        dateInfo.setText("tickets valid on 25/04");
        final var label1 = new Label("Ticket ID: ID1");
        final var label2 = new Label("purchase date: 23/04");
        final var label3 = new Label("adult single day ticket, 2023");
        label1.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        label2.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        label3.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        final var label4 = new Label("Ticket ID: ID2");
        final var label5 = new Label("purchase date: 24/04");
        final var label6 = new Label("adult single day ticket, 2023");
        label4.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        label5.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        label6.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        final var label7 = new Label("Ticket ID: ID3");
        final var label8 = new Label("purchase date: 22/04");
        final var label9 = new Label("senior single day ticket, 2023");
        label7.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        label8.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        label9.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        final var label10 = new Label("Ticket ID: ID4");
        final var label11 = new Label("purchase date: 25/04");
        final var label12 = new Label("kids single day ticket, 2023");
        label10.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        label11.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        label12.setFont(new Font(TICKETS_INFO_FONT_SIZE));
        final var borderPane1 = new BorderPane();
        addTicketInfo(borderPane1, label1, label2, label3);
        final var borderPane2 = new BorderPane();
        addTicketInfo(borderPane2, label4, label5, label6);
        final var borderPane3 = new BorderPane();
        addTicketInfo(borderPane3, label7, label8, label9);
        final var borderPane4 = new BorderPane();
        addTicketInfo(borderPane4, label10, label11, label12);

        listView.getItems().add(borderPane1);
        listView.getItems().add(borderPane2);
        listView.getItems().add(borderPane3);
        listView.getItems().add(borderPane4);
    }

    private void addTicketInfo(final BorderPane pane, final Label ticketID, final Label purchaseDate, final Label ticketType) {
        pane.setLeft(ticketID);
        pane.setRight(purchaseDate);
        pane.setBottom(ticketType);
    }
}

