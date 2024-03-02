package org.apdb4j.view.guests;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.NonNull;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.controllers.guests.BoughtTicketsController;
import org.apdb4j.controllers.guests.BoughtTicketsControllerImpl;
import org.apdb4j.controllers.guests.TicketType;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see more information on the tickets bought.
 */
public class TicketsInfoController extends BackableAbstractFXMLController {

    private static final int TITLE_FONT_SIZE = 24;
    @FXML
    private Label title;
    @FXML
    private TableView<TicketTableItem> tableView;
    private final BoughtTicketsController controller;
    private final TicketType ticketType;

    /**
     * Default constructor. Creates a new instance of this class that shows all the info about the tickets
     * of the provided type bought by the currently logged user so far.
     * @param ticketType the ticket type.
     */
    public TicketsInfoController(final @NonNull TicketType ticketType) {
        this.ticketType = ticketType;
        controller = new BoughtTicketsControllerImpl(ticketType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        title.setText(SessionManager.getSessionManager().getSession().username() + "'s " + ticketType.getName() + "s");
        title.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, TITLE_FONT_SIZE));
        final TableColumn<TicketTableItem, String> ticketIDColumn = new TableColumn<>("Ticket ID");
        final TableColumn<TicketTableItem, LocalDate> purchaseDateColumn = new TableColumn<>("Purchase date");
        final TableColumn<TicketTableItem, LocalDate> validOnColumn = new TableColumn<>("Valid on");
        final TableColumn<TicketTableItem, LocalDate> validUntilColumn = new TableColumn<>("Valid until");
        final TableColumn<TicketTableItem, Integer> remainingEntrancesColumn = new TableColumn<>("Remaining entrances");
        final TableColumn<TicketTableItem, Year> yearColumn = new TableColumn<>("Price list");
        final TableColumn<TicketTableItem, String> categoryColumn = new TableColumn<>("Category");

        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        validOnColumn.setCellValueFactory(new PropertyValueFactory<>("validOn"));
        validUntilColumn.setCellValueFactory(new PropertyValueFactory<>("validUntil"));
        remainingEntrancesColumn.setCellValueFactory(new PropertyValueFactory<>("remainingEntrances"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        tableView.getColumns().addAll(List.of(ticketIDColumn,
                purchaseDateColumn,
                validOnColumn,
                validUntilColumn,
                remainingEntrancesColumn,
                yearColumn,
                categoryColumn));
        tableView.getItems().addAll(controller.getAllData());
    }
}

