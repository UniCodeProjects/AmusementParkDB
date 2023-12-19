package org.apdb4j.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

/**
 * FXML controller for the scene that allows the user to see their purchases in the park.
 */
public class UserPurchasesController {

    @FXML
    private Button backButton;

    @FXML
    private VBox pane;

    @FXML
    private Button seasonTicketsButton;

    @FXML
    private Button ticketsButton;

    /**
     * Opens the screen containing all the tickets bought by the user so far.
     * @param event the event.
     */
    @FXML
    void onTicketsButtonPressed(final MouseEvent event) {
        JavaFXUtils.setStageTitle(event, "My tickets");
        LoadFXML.fromEvent(event, "layouts/user-tickets-screen.fxml", true, true);
    }

    /**
     * Opens the screen containing all the season tickets bought by the user so far.
     * @param event the event.
     */
    @FXML
    void onSeasonTicketsButtonPressed(final MouseEvent event) {
        JavaFXUtils.setStageTitle(event, "My season tickets");
        LoadFXML.fromEvent(event, "layouts/user-tickets-screen.fxml", true, true);
    }
}
