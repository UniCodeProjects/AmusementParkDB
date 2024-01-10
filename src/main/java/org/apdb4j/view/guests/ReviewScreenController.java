package org.apdb4j.view.guests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.BackableAbstractFXMLController;
import org.jooq.Record;
import org.jooq.Result;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.apdb4j.db.Tables.*;

/**
 * FXML controller for the screen that displays all the reviews for a ride.
 */
public class ReviewScreenController extends BackableAbstractFXMLController {

    private static final String TITLE_BASE_TEXT = "Reviews";

    @FXML
    private Button addReviewButton;
    @FXML
    private Label averageRating;
    @FXML
    private Label numReviews;
    @FXML
    private BorderPane pane;
    @FXML
    private ListView<VBox> reviews;
    @FXML
    private Label title;
    private final String parkServiceName;

    /**
     * Creates a new instance of this class which refers to the park service {@code parkServiceName}.
     * @param parkServiceName the name of the park service referred by the scene.
     */
    public ReviewScreenController(final String parkServiceName) {
        this.parkServiceName = parkServiceName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        title.setText(parkServiceName + " " + TITLE_BASE_TEXT);
        for (final Record review : getReviews()) {
            addReviewInListView(review);
        }
        final var numReviewsAndAvgRating = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NUMREVIEWS, PARK_SERVICES.AVGRATING)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0);
        numReviews.setText(numReviewsAndAvgRating.get(PARK_SERVICES.NUMREVIEWS).toString());
        averageRating.setText(numReviewsAndAvgRating.get(PARK_SERVICES.AVGRATING).toString());
    }

    /**
     * Opens the screen that allows the user to add a new review.
     * @param event the click on the "add review" button.
     */
    @FXML
    void onAddReviewButtonPressed(final ActionEvent event) {
    }

    private Result<Record> getReviews() {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(REVIEWS.ACCOUNT,
                        REVIEWS.DESCRIPTION,
                        REVIEWS.DATE,
                        REVIEWS.RATING)
                        .from(REVIEWS)
                        .where(REVIEWS.PARKSERVICEID.eq(getParkServiceID()))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    private String getParkServiceID() {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.PARKSERVICEID)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0)
                .get(PARK_SERVICES.PARKSERVICEID);
    }

    private void addReviewInListView(final Record review) {
        final VBox reviewContainer = new VBox();
        reviews.getItems().add(reviewContainer);
        reviewContainer.getChildren().add(new Label("Author: " + review.get(REVIEWS.ACCOUNT)));
        final HBox ratingAndDate = new HBox();
        ratingAndDate.setPadding(new Insets(0, 0, 5, 0));
        final Label ratingLabel = new Label("Rating: " + review.get(REVIEWS.RATING).toString() + "/5");
        ratingLabel.setPadding(new Insets(0, 10, 0, 0));
        ratingAndDate.getChildren().add(ratingLabel);
        ratingAndDate.getChildren().add(new Label("Date: " + review.get(REVIEWS.DATE).toString()));
        reviewContainer.getChildren().add(ratingAndDate);
        if (!Objects.isNull(review.get(REVIEWS.DESCRIPTION))) {
            final TextArea reviewDescription = new TextArea();
            reviewDescription.setText(review.get(REVIEWS.DESCRIPTION));
            reviewDescription.setEditable(false);
            reviewContainer.getChildren().add(reviewDescription);
        }
    }
}
