package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;

/**
 * The FXML controller for the ticket price screen.
 */
public class TicketPriceController extends PopupInitializer {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField yearField;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;

    /**
     * Default constructor.
     */
    public TicketPriceController() {
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
