package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.apdb4j.util.view.JavaFXUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see the price list of the current year.
 */
public class UserPriceListController extends AbstractFXMLController implements Initializable {

    private static final double LEFT_ARROW_IMAGE_HEIGHT = 25;
    private static final double LEFT_ARROW_IMAGE_WIDTH = 25;
    private static final String PRICE_LIST_PERIOD_BASE_TEXT = " tickets price list";

    @FXML
    private Button backButton;

    @FXML
    private BorderPane backButtonContainer;

    @FXML
    private Button buyTicketsButton;

    @FXML
    private BorderPane pane;

    @FXML
    private Label priceListPeriod;

    @FXML
    private ListView<Label> ticketsAndSeasonTickets;

    @FXML
    private VBox topContainer;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        priceListPeriod.setText(LocalDate.now().getYear() + PRICE_LIST_PERIOD_BASE_TEXT);
        final var leftArrowImageView = new ImageView(new Image("img/left_arrow.png"));
        leftArrowImageView.setFitHeight(LEFT_ARROW_IMAGE_HEIGHT);
        leftArrowImageView.setFitWidth(LEFT_ARROW_IMAGE_WIDTH);
        backButton.setGraphic(leftArrowImageView);
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
