package org.apdb4j.view.guests;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.control.Alert;
import lombok.NonNull;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apdb4j.controllers.guests.ParkServiceType;
import org.apdb4j.controllers.guests.SingleExhibitionInfoController;
import org.apdb4j.controllers.guests.SingleParkServiceInfoController;
import org.apdb4j.controllers.guests.SingleRideInfoController;
import org.apdb4j.controllers.guests.SingleShopInfoController;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableFXMLController;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
    private final String parkServiceName;
    private final ParkServiceType parkServiceType;
    private final SingleParkServiceInfoController controller;
    private final BackableFXMLController userParkServicesScreenController;

    /**
     * Creates a new instance of this class which refers to the park service {@code parkServiceName}, of type
     * {@code parkServiceType}.
     * @param parkServiceName the name of the park service referred by the scene.
     * @param parkServiceType the type of the park service referred by the scene.
     * @param userParkServicesScreenController the controller of the screen that shows all the park services.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public ParkServicesInfoScreenController(final @NonNull String parkServiceName,
                                            final @NonNull ParkServiceType parkServiceType,
                                            final @NonNull BackableFXMLController userParkServicesScreenController) {
        this.parkServiceName = parkServiceName;
        this.parkServiceType = parkServiceType;
        this.userParkServicesScreenController = userParkServicesScreenController;
        controller = switch (parkServiceType) {
            case RIDE -> new SingleRideInfoController();
            case EXHIBITION -> new SingleExhibitionInfoController();
            case SHOP, RESTAURANT -> new SingleShopInfoController();
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        parkServiceNameLabel.setText(parkServiceName);
        description.setText(controller.getParkServiceDescription(parkServiceName));
        description.setEditable(false);
        parkServiceDescriptionLabel.setText(parkServiceType.getName() + parkServiceDescriptionLabel.getText());
        parkServiceInfoLabel.setText(parkServiceType.getName() + parkServiceInfoLabel.getText());
        if (parkServiceType.equals(ParkServiceType.EXHIBITION)) {
            final var exhibitionInfo = new HashMap<>(controller.getAllParkServiceInfo(parkServiceName));
            addNewAttributeWithValueInInfoView("Type", exhibitionInfo.get("Type"));
            exhibitionInfo.remove("Type");

            final var plannedExhibitionLabel = new Label("Next show's info:");
            plannedExhibitionLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 14));
            VBox.setMargin(plannedExhibitionLabel, new Insets(0, 0, 5, 5));
            descriptionAndInfoContainer.getChildren().add(plannedExhibitionLabel);
            if (exhibitionInfo.isEmpty()) {
                final var noPlannedExhibitionLabel = new Label("There are no shows planned for this exhibition");
                noPlannedExhibitionLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 12));
                VBox.setMargin(noPlannedExhibitionLabel, new Insets(0, 0, 0, 5));
                descriptionAndInfoContainer.getChildren().add(noPlannedExhibitionLabel);
            } else {
                exhibitionInfo.forEach(this::addNewAttributeWithValueInInfoView);
            }
        } else {
            controller.getAllParkServiceInfo(parkServiceName).forEach(this::addNewAttributeWithValueInInfoView);
        }
    }

    /**
     * Opens the popup screen that shows all the photo of the ride.
     * @param event the click on the "show photos" button.
     */
    @FXML
    void onPhotosButtonPressed(final ActionEvent event) {
        final List<String> photosPath = controller.getPhotosPath(parkServiceName);
        if (photosPath.isEmpty()) {
            new AlertBuilder(Alert.AlertType.INFORMATION).setHeaderText("There are no photos for " + parkServiceName).show();
        } else {
            LoadFXML.fromEventAsPopup(event, ParkServicesPhotosScreenController.class,
                    parkServiceName + " photos",
                    1,
                    1,
                    null,
                    List.copyOf(photosPath));
        }
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
                parkServiceName, userParkServicesScreenController);
        JavaFXUtils.setStageTitle(event, "reviews", true);
    }

    private void addNewAttributeWithValueInInfoView(final @NonNull String attribute, final @NonNull String value) {
        final var hBox = new HBox();
        descriptionAndInfoContainer.getChildren().add(hBox);
        VBox.setMargin(hBox, new Insets(0, 0, 5, 0));
        final var attributeNameLabel = new Label(attribute + ":");
        attributeNameLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 12));
        final var attributeValueLabel = new Label(value);
        hBox.getChildren().addAll(attributeNameLabel, attributeValueLabel);
        HBox.setMargin(attributeNameLabel, new Insets(0, 0, 0, 5));
        HBox.setMargin(attributeValueLabel, new Insets(0, 0, 0, 3));
    }
}
