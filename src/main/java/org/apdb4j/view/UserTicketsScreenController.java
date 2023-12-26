package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see both tickets and season tickets bought.
 */
public class UserTicketsScreenController extends AbstractFXMLController implements Initializable {

    private static final double LEFT_ARROW_IMAGE_HEIGHT = 25;
    private static final double LEFT_ARROW_IMAGE_WIDTH = 25;

    @FXML
    private Button backButton;

    @FXML
    private ListView<Hyperlink> listView;

    @FXML
    private BorderPane pane;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final var leftArrowImageView = new ImageView(new Image("img/left_arrow.png"));
        leftArrowImageView.setFitHeight(LEFT_ARROW_IMAGE_HEIGHT);
        leftArrowImageView.setFitWidth(LEFT_ARROW_IMAGE_WIDTH);
        backButton.setGraphic(leftArrowImageView);
        addTicket();
    }

    /**
     * Returns to the previous scene.
     * @param event the click on the "back" button.
     */
    @FXML
    void onBackButtonPressed(final ActionEvent event) {
        JavaFXUtils.setSceneFromEvent(event, getPreviousScene(), getPreviousSceneTitle());
    }

    private void addTicket() {
        final var ticket = new Hyperlink("3 tickets, valid on 25/04");
        ticket.setFocusTraversable(false);
        ticket.setOnAction(event ->
                LoadFXML.fromEventAsPopup(event, "layouts/tickets-info.fxml", "tickets valid on 25/04", 0.5, 0.5));
        listView.getItems().add(ticket);
    }
}
