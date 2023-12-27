package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apdb4j.util.view.JavaFXUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to buy either tickets or season tickets, or both.
 */
public class UserTicketsChooserController extends AbstractFXMLController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button buyButton;

    @FXML
    private ListView<Label> cart;

    @FXML
    private BorderPane pane;

    @FXML
    private VBox ticketsAndSeasonTicketsContainer;

    @FXML
    private ScrollPane ticketsAndSeasonTicketsScrollableContainer;

    @FXML
    private Label totalPrice;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.setBackButtonImage(backButton);
    }

    /**
     * Returns to the previous scene.
     * @param event the click on the "back" button.
     */
    @FXML
    void onBackButtonPressed(final ActionEvent event) {
        JavaFXUtils.setSceneFromEvent(event, getPreviousScene(), getPreviousSceneTitle());
    }
}
