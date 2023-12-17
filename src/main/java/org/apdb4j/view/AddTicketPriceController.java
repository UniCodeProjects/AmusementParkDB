package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the add ticket price screen.
 */
public class AddTicketPriceController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField yearField;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            final var stage = safeCastToStage(gridPane.getScene().getWindow());
            stage.setResizable(false);
            // CPD-OFF
            if (editMode) {
                // TODO
                throw new UnsupportedOperationException();
            }
            // CPD-ON
        });
    }

    private Stage safeCastToStage(final Window window) {
        Stage stage;
        if (window instanceof Stage) {
            stage = (Stage) window;
        } else {
            throw new IllegalStateException("Failed cast: the given window is not a Stage instance");
        }
        return stage;
    }

}
