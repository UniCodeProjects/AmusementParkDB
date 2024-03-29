package org.apdb4j.view.guests;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apdb4j.controllers.guests.BestParkServicesControllerImpl;
import org.apdb4j.controllers.guests.ParkServiceType;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.FXMLController;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML controller for the user UI.
 */
public class UserScreenController implements FXMLController, Initializable {

    private static final double BACKGROUND_IMAGE_OPACITY = 0.4;
    private static final double ACCOUNT_IMAGE_HEIGHT = 50;
    private static final double ACCOUNT_IMAGE_WIDTH = 50;
    private static final String ACCOUNT_IMAGE_PATH = "img/account.png";
    private static final double ACCOUNT_SCROLLABLE_MENU_MAX_HEIGHT = 100;

    @FXML
    private Label accountLabel;

    @FXML
    private BorderPane pane;

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
        accountInfo.getItems().addAll(List.of(accountPurchases, accountPersonalData, new Separator(), logOutButton));
        accountPurchases.setOnAction(event -> {
            LoadFXML.fromEvent(event, "layouts/user-purchases.fxml", true, true, true);
            JavaFXUtils.setStageTitle(event, "My purchases", true);
        });
        accountPersonalData.setOnAction(event -> {
            LoadFXML.fromEvent(event, "layouts/user-personal-data.fxml", true, true, true);
            JavaFXUtils.setStageTitle(event, "My personal data", true);
        });
        logOutButton.setOnAction(event -> Platform.exit());
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
        JavaFXUtils.setStageTitle(event, "Tickets price list", true);
    }

    /**
     * Opens the screen that displays all the rides' info.
     * @param event the click on the "rides" hyperlink.
     */
    @FXML
    void onRidesClick(final ActionEvent event) {
        LoadFXML.fromEvent(event, UserParkServicesScreenController.class, true, true, true, ParkServiceType.RIDE);
        JavaFXUtils.setStageTitle(event, "Our rides", true);
    }

    /**
     * Opens the screen that displays all the exhibitions' info.
     * @param event the click on related hyperlink
     */
    @FXML
    void onExhibitionsClick(final ActionEvent event) {
        LoadFXML.fromEvent(event, UserParkServicesScreenController.class, true, true, true, ParkServiceType.EXHIBITION);
        JavaFXUtils.setStageTitle(event, "Our exhibitions", true);
    }

    /**
     * Opens the screen that displays all the restaurants' info.
     * @param event the click on the related hyperlink
     */
    @FXML
    void onRestaurantsClick(final ActionEvent event) {
        LoadFXML.fromEvent(event, UserParkServicesScreenController.class, true, true, true, ParkServiceType.RESTAURANT);
        JavaFXUtils.setStageTitle(event, "Our restaurants", true);
    }

    /**
     * Opens the screen that displays all the shops' info.
     * @param event the click on the related hyperlink
     */
    @FXML
    void onShopsClick(final ActionEvent event) {
        LoadFXML.fromEvent(event, UserParkServicesScreenController.class, true, true, true, ParkServiceType.SHOP);
        JavaFXUtils.setStageTitle(event, "Our shops", true);
    }

    /**
     * Opens the screen that displays the best park services according to their average rating.
     * @param event the click on the related hyperlink
     */
    @FXML
    void onBestParkServicesClick(final ActionEvent event) {
        final VBox bestParkServicesContainer = new VBox();
        final List<Map<String, String>> bestParkServices = new BestParkServicesControllerImpl().getBestParkServices();
        bestParkServices.forEach(parkService -> {
            final var parkServiceInfo = new HashMap<>(parkService);
            final Label parkServiceLabel = new Label();
            parkServiceLabel.setText((bestParkServices.indexOf(parkService) + 1) + ". ");
            parkServiceLabel.setText(parkServiceLabel.getText() + "Name: " + parkServiceInfo.get("Name") + " - ");
            parkServiceInfo.remove("Name");
            parkServiceInfo.forEach((attribute, value) ->
                    parkServiceLabel.setText(parkServiceLabel.getText() + attribute + ": " + value + " - "));
            bestParkServicesContainer.getChildren().add(parkServiceLabel);
            VBox.setMargin(parkServiceLabel, new Insets(0, 0, 0, 5));
        });
        bestParkServicesContainer.getChildren().forEach(label -> {
            final String labelText = ((Label) label).getText();
            ((Label) label).setText(new StringBuilder(labelText)
                    .replace(labelText.length() - 3, labelText.length(), "").toString());
        });
        new AlertBuilder(Alert.AlertType.INFORMATION).setHeaderText("Our best park services")
                .setContent(bestParkServicesContainer).show();
    }
}
