package org.apdb4j.view.guests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.NonNull;
import org.apdb4j.controllers.guests.ParkServiceController;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see all the information about a single park service.
 */
public class ParkServicesInfoScreenController implements Initializable {

    @FXML
    private TextArea description;
    @FXML
    private VBox descriptionAndInfoContainer;
    @FXML
    private Label parkServiceDescriptionLabel;
    @FXML
    private Label parkServiceInfoLabel;
    @FXML
    private Label parkServiceNameLabel;
    @FXML
    private Button photosButton; // TODO: remove, also from fxml
    @FXML
    private Button reviewsButton; // TODO: remove, also from fxml
    private final String parkServiceName;
    private final ParkServiceController controller;
    private final ParkServiceType parkServiceType;

    /**
     * Creates a new instance of this class which refers to the park service {@code parkServiceName}.
     * @param parkServiceName the name of the park service referred by the scene.
     * @param controller the MVC controller.
     * @param parkServiceType the type of the park service with name {@code parkServiceName}.
     */
    public ParkServicesInfoScreenController(final @NonNull String parkServiceName,
                                            final @NonNull ParkServiceController controller,
                                            final @NonNull ParkServiceType parkServiceType) {
        this.parkServiceName = parkServiceName;
        this.controller = controller;
        this.parkServiceType = parkServiceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        parkServiceNameLabel.setText(parkServiceName);
        parkServiceDescriptionLabel.setText(parkServiceType.getName() + parkServiceDescriptionLabel.getText());
        description.setText(controller.getParkServiceDescription(parkServiceName));
        description.setEditable(false);
        parkServiceInfoLabel.setText(parkServiceType.getName() + parkServiceInfoLabel.getText());
        final var parkServiceInfo = controller.getAllInfo(parkServiceName);
        parkServiceInfo.forEach((attribute, value) -> {
            final var hBox = new HBox();
            descriptionAndInfoContainer.getChildren().add(hBox);
            VBox.setMargin(hBox, new Insets(0, 0, 5, 0));
            final var attributeNameLabel = new Label(attribute + ":");
            attributeNameLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 12));
            final var attributeValueLabel = new Label(value);
            hBox.getChildren().addAll(attributeNameLabel, attributeValueLabel);
            HBox.setMargin(attributeNameLabel, new Insets(0, 0, 0, 5));
            HBox.setMargin(attributeValueLabel, new Insets(0, 0, 0, 3));
        });
    }

    /**
     * Opens the popup screen that shows all the photo of the ride.
     * @param event the click on the "show photos" button.
     */
    @FXML
    void onPhotosButtonPressed(final ActionEvent event) {
        LoadFXML.fromEventAsPopup(event, ParkServicesPhotosScreenController.class,
                parkServiceName + " photos",
                1,
                1,
                controller.getParkServiceID(parkServiceName));
    }

    /**
     * Opens the popup screen that allows the user to see all the reviews for the ride
     * and also to add a new review on the ride.
     * @param event the click on the "reviews" button.
     */
    @FXML
    void onReviewsButtonPressed(final ActionEvent event) {
        LoadFXML.fromEvent(event,
                ReviewScreenController.class,
                true,
                true,
                true,
                parkServiceName);
        JavaFXUtils.setStageTitle(event, "reviews", true);
    }
}
