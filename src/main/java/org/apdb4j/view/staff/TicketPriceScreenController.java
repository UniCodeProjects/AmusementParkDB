package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.PopupInitializer;

/**
 * The FXML controller for the ticket price screen.
 */
public class TicketPriceScreenController extends PopupInitializer {

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
    public TicketPriceScreenController() {
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