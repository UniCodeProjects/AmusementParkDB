package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.TicketTypeTableItem;

/**
 * The FXML controller for the ticket type screen.
 */
public class TicketTypeScreenController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField typeField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField yearField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private Spinner<Integer> durationSpinner;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static TicketTypeTableItem ticketType;
    @Setter
    private static TableView<TicketTypeTableItem> tableView;

    /**
     * Default constructor.
     */
    public TicketTypeScreenController() {
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
        // TODO
        throw new UnsupportedOperationException();
    }

}
