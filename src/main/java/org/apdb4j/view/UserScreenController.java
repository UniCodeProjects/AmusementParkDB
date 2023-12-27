package org.apdb4j.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the user UI.
 */
public class UserScreenController extends AbstractFXMLController implements Initializable {

    private static final double BACKGROUND_IMAGE_OPACITY = 0.4;
    private static final double ACCOUNT_IMAGE_HEIGHT = 50;
    private static final double ACCOUNT_IMAGE_WIDTH = 50;
    private static final String ACCOUNT_IMAGE_PATH = "img/account.png";
    private static final double ACCOUNT_SCROLLABLE_MENU_MAX_HEIGHT = 100;

    @FXML
    private Label accountLabel;

    @FXML
    private Hyperlink exhibitions;

    @FXML
    private BorderPane pane;

    @FXML
    private Hyperlink restaurants;

    @FXML
    private Hyperlink rides;

    @FXML
    private Hyperlink souvenirShops;

    @FXML
    private Hyperlink tickets;

    @FXML
    private Label welcomeLabel;

    private boolean isPersonalAreaOpen;
    private final ToolBar accountInfo = new ToolBar();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final Background background = new Background(new BackgroundImage(makeImageTransparent(new Image("img/park.jpg"),
                BACKGROUND_IMAGE_OPACITY),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false,
                        false,
                        true,
                        true)));
        JavaFXUtils.setLabeledImage(accountLabel, ACCOUNT_IMAGE_PATH, ACCOUNT_IMAGE_WIDTH, ACCOUNT_IMAGE_HEIGHT);
        pane.setBackground(background);
        // ToolBar initialization
        final Hyperlink accountPurchases = new Hyperlink("My purchases");
        final Hyperlink accountPersonalData = new Hyperlink("My personal data");
        final Button logOutButton = new Button("Log out");
        logOutButton.setCursor(Cursor.HAND);
        accountInfo.getItems().add(accountPurchases);
        accountInfo.getItems().add(accountPersonalData);
        accountInfo.getItems().add(new Separator());
        accountInfo.getItems().add(logOutButton);
        accountPurchases.setOnAction(event ->
                LoadFXML.fromEvent(event, "layouts/user-purchases.fxml", true, true, true));
        accountPersonalData.setOnAction(event ->
                LoadFXML.fromEvent(event, "layouts/user-personal-data.fxml", true, true, true));
        for (final var accountInfoItem : accountInfo.getItems()) {
            accountInfoItem.setFocusTraversable(false);
        }
        accountInfo.setOrientation(Orientation.VERTICAL);
        accountInfo.setMaxHeight(ACCOUNT_SCROLLABLE_MENU_MAX_HEIGHT);
    }

    /**
     * Opens the user's personal area.
     * @param event the event.
     */
    @FXML
    void onAccountLabelClick(final MouseEvent event) {
        if (!isPersonalAreaOpen) {
            pane.setRight(accountInfo);
            isPersonalAreaOpen = true;
        } else {
            pane.getChildren().remove(accountInfo);
            isPersonalAreaOpen = false;
        }
    }

    private Image makeImageTransparent(final Image originalImage, final double opacity) {
        final int width = (int) originalImage.getWidth();
        final int height = (int) originalImage.getHeight();

        final WritableImage result = new WritableImage(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final Color color = originalImage.getPixelReader().getColor(x, y);
                result.getPixelWriter().setColor(x, y,
                        new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity));
            }
        }

        return result;
    }

    /**
     * Opens the tickets price list for the current year.
     * @param event the click on the "tickets" hyperlink.
     */
    @FXML
    void onTicketsClick(final ActionEvent event) {
        LoadFXML.fromEvent(event, "layouts/user-price-list.fxml", true, true, true);
        JavaFXUtils.setStageTitle(event, "Tickets price list");
    }

    /**
     * Opens the screen that displays all the rides' info.
     * @param event the click on the "rides" hyperlink.
     */
    @FXML
    void onRidesClick(final ActionEvent event) {
        LoadFXML.fromEvent(event, "layouts/user-rides-screen.fxml", true, true, true);
        JavaFXUtils.setStageTitle(event, "Our rides");
    }
}