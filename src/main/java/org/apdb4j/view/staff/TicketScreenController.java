package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.TicketTableItem;

/**
 * The FXML controller for the ticket screen.
 */
public class TicketScreenController extends PopupInitializer {

    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField ownerIDField;
    @FXML
    private DatePicker validOnDatePicker;
    @FXML
    private DatePicker validUntilDatePicker;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static TicketTableItem ticket;

    /**
     * Default constructor.
     */
    public TicketScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        if (!editMode) {
            return;
        }
        validOnDatePicker.setValue(ticket.getValidOn());
        validUntilDatePicker.setValue(ticket.getValidUntil());
        ownerIDField.setText(ticket.getOwnerID());
        categoryChoiceBox.setValue(ticket.getCategory());
    }

}
