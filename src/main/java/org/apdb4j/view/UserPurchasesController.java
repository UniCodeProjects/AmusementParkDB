package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the scene that allows the user to see their purchases in the park.
 */
public class UserPurchasesController extends AbstractFXMLController implements Initializable {

    private static final double LEFT_ARROW_IMAGE_HEIGHT = 25;
    private static final double LEFT_ARROW_IMAGE_WIDTH = 25;

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
        LoadFXML.fromEvent(event, "layouts/user-tickets-screen.fxml", true, true, true);
        JavaFXUtils.setStageTitle(event, "My tickets");
    }

    /**
     * Opens the screen containing all the season tickets bought by the user so far.
     * @param event the event.
     */
    @FXML
    void onSeasonTicketsButtonPressed(final MouseEvent event) {
        LoadFXML.fromEvent(event, "layouts/user-tickets-screen.fxml", true, true, true);
        JavaFXUtils.setStageTitle(event, "My season tickets");
    }

    /**
     * Returns to the previous scene.
     * @param event the click on the "back" button.
     */
    @FXML
    void onBackButtonPressed(final ActionEvent event) {
        JavaFXUtils.setSceneFromEvent(event, getPreviousScene(), getPreviousSceneTitle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final var leftArrowImageView = new ImageView(new Image("img/left_arrow.png"));
        leftArrowImageView.setFitHeight(LEFT_ARROW_IMAGE_HEIGHT);
        leftArrowImageView.setFitWidth(LEFT_ARROW_IMAGE_WIDTH);
        backButton.setGraphic(leftArrowImageView);
    }
}
