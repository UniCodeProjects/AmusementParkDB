package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import org.apdb4j.view.tableview.ExhibitionTableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML controller for the add-exhibition screen.
 */
public class AddExhibitionController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextField descriptionField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeHourField;
    @FXML
    private TextField timeMinuteField;
    @FXML
    private Spinner<Integer> maxSeatsSpinner;
    @FXML
    private Spinner<Integer> spectatorsSpinner;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static ExhibitionTableView exhibition;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        Platform.runLater(() -> {
            final var stage = safeCastToStage(gridPane.getScene().getWindow());
            stage.setResizable(false);
            if (editMode) {
                // TODO
                throw new UnsupportedOperationException();
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
