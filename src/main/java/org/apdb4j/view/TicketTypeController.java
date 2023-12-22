package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;

/**
 * The FXML controller for the ticket type screen.
 */
public class TicketTypeController extends PopupInitializer {

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

    /**
     * Default constructor.
     */
    public TicketTypeController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void editMode() {
        if (!editMode) {
            return;
        }
        // TODO
        throw new UnsupportedOperationException();
    }

}
