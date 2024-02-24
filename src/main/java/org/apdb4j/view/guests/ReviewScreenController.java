package org.apdb4j.view.guests;

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
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableAbstractFXMLController;

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

    /**
     * Creates a new instance of this class which refers to the park service {@code parkServiceName}.
     * @param parkServiceName the name of the park service referred by the scene.
     */
    public ReviewScreenController(final @NonNull String parkServiceName) {
        this.parkServiceName = parkServiceName;
        controller = new ReviewControllerImpl(parkServiceName);
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
                parkServiceName,
                controller);
    }

    private void addReviewInListView(final @NonNull Map<String, String> review) {
        final VBox reviewContainer = new VBox();
        reviews.getItems().add(reviewContainer);
        reviewContainer.getChildren().add(new Label("Author: " + review.get("Username")));
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
