package org.apdb4j.view.guests;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apdb4j.controllers.guests.ReviewController;
import org.apdb4j.controllers.guests.ReviewControllerImpl;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableAbstractFXMLController;
import org.apdb4j.view.BackableFXMLController;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that displays all the reviews for a park service.
 */
public class ReviewScreenController extends BackableAbstractFXMLController {

    private static final String TITLE_BASE_TEXT = "reviews";
    @FXML
    private Label averageRating;
    @FXML
    private Label numReviews;
    @FXML
    private ListView<VBox> reviews;
    @FXML
    private Label title;
    private final String parkServiceName;
    private final ReviewController controller;
    private final BackableFXMLController userParkServicesScreenController;

    /**
     * Creates a new instance of this class which refers to the park service {@code parkServiceName}.
     * @param parkServiceName the name of the park service referred by the scene.
     * @param userParkServicesScreenController the controller of the screen that shows all the park services.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public ReviewScreenController(final @NonNull String parkServiceName,
                                  final @NonNull BackableFXMLController userParkServicesScreenController) {
        this.parkServiceName = parkServiceName;
        controller = new ReviewControllerImpl(parkServiceName);
        this.userParkServicesScreenController = userParkServicesScreenController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        title.setText(parkServiceName + " " + TITLE_BASE_TEXT);
        for (final var review : controller.getReviews()) {
            addReviewInListView(review);
        }
        numReviews.setText(String.valueOf(controller.getNumberOfReviews()));
        averageRating.setText(String.valueOf(controller.getAverageRating()));
    }

    /**
     * Opens the screen that allows the user to add a new review.
     * @param event the click on the "add review" button.
     */
    @FXML
    void onAddReviewButtonPressed(final ActionEvent event) {
        LoadFXML.fromEventAsPopup(event,
                AddReviewScreenController.class,
                "Share your opinion on " + parkServiceName,
                1,
                1,
                JavaFXUtils.getStage(event),
                parkServiceName, controller, userParkServicesScreenController);
    }

    private void addReviewInListView(final @NonNull Map<String, String> review) {
        final VBox reviewContainer = new VBox();
        reviews.getItems().add(reviewContainer);
        reviewContainer.getChildren().add(new Label("Author: " + review.get("Name") + " " + review.get("Surname")));
        final HBox ratingAndDateTime = new HBox();
        reviewContainer.getChildren().add(ratingAndDateTime);
        VBox.setMargin(ratingAndDateTime, new Insets(0, 0, 5, 0));
        final Label ratingLabel = new Label("Rating: " + review.get("Rating") + "/" + ReviewController.getMaxRating());
        ratingAndDateTime.getChildren().add(ratingLabel);
        HBox.setMargin(ratingLabel, new Insets(0, 5, 0, 0));
        final Label dateLabel = new Label("Date: " + review.get("Date"));
        ratingAndDateTime.getChildren().add(dateLabel);
        HBox.setMargin(dateLabel, new Insets(0, 5, 0, 0));
        ratingAndDateTime.getChildren().add(new Label("Time: " + review.get("Time")));
        if (!review.get("Description").isEmpty()) {
            final TextArea reviewDescription = new TextArea();
            reviewDescription.setText(review.get("Description"));
            reviewDescription.setEditable(false);
            reviewContainer.getChildren().add(reviewDescription);
        }
    }
}
