package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.staff.TicketTypeControllerImpl;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.TicketTypeTableItem;

/**
 * The FXML controller for the ticket type screen.
 */
public class TicketTypeScreenController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private Spinner<Double> priceSpinner;
    @FXML
    private Spinner<Integer> yearSpinner;
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
    private final TicketTypeControllerImpl controller = new TicketTypeControllerImpl();

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
        typeChoiceBox.getItems().addAll(controller.getAllTicketTypes());
        categoryChoiceBox.getItems().addAll(controller.getAllTicketTypeCategories());
        if (!editMode) {
            return;
        }
        typeChoiceBox.setValue(ticketType.getType());
        priceSpinner.getValueFactory().setValue(ticketType.getPrice());
        yearSpinner.getValueFactory().setValue(ticketType.getYear().getValue());
        categoryChoiceBox.setValue(ticketType.getCategory());
        durationSpinner.getValueFactory().setValue(ticketType.getDuration());
    }

}
