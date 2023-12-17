package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import org.apdb4j.view.tableview.TicketTableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the add ticket screen.
 */
public class AddTicketController implements Initializable {

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
    private static TicketTableView ticket;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            final var stage = safeCastToStage(gridPane.getScene().getWindow());
            stage.setResizable(false);
            if (editMode) {
                validOnDatePicker.setValue(ticket.getValidOn());
                validUntilDatePicker.setValue(ticket.getValidUntil());
                ownerIDField.setText(ticket.getOwnerID());
                categoryChoiceBox.setValue(ticket.getCategory());
            }
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
