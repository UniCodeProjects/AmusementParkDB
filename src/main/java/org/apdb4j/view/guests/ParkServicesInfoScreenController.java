package org.apdb4j.view.guests;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.apdb4j.db.Tables.*;

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
    private Button photosButton;
    @FXML
    private Button reviewsButton;
    private final String parkServiceName;

    /**
     * Creates a new instance of this class which refers to the park service {@code parkServiceName}.
     * @param parkServiceName the name of the park service referred by the scene.
     */
    public ParkServicesInfoScreenController(final @NonNull String parkServiceName) {
        this.parkServiceName = parkServiceName;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH")
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        parkServiceNameLabel.setText(parkServiceName);
        description.setText(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.DESCRIPTION)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0)
                .get(PARK_SERVICES.DESCRIPTION));
        description.setEditable(false);
        final var parkService = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(RIDES.INTENSITY,
                        RIDES.DURATION,
                        RIDES.MAXSEATS,
                        RIDES.MINHEIGHT,
                        RIDES.MAXHEIGHT,
                        RIDES.MINWEIGHT,
                        RIDES.MAXWEIGHT,
                        FACILITIES.OPENINGTIME,
                        FACILITIES.CLOSINGTIME,
                        PARK_SERVICES.TYPE,
                        RIDE_DETAILS.STATUS)
                        .from(PARK_SERVICES, FACILITIES, RIDES, RIDE_DETAILS)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName)
                                .and(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID)
                                .and(FACILITIES.FACILITYID.eq(RIDES.RIDEID))
                                .and(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0);
        for (final var field : parkService.fields()) {
            final var hBox = new HBox();
            hBox.setPadding(new Insets(0, 0, 5, 0));
            final var fieldNameLabel = new Label(field.getName() + ":");
            fieldNameLabel.setPadding(new Insets(0, 0, 0, 5));
            fieldNameLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 12));
            final var fieldValueLabel = new Label(Objects.requireNonNull(field.getValue(parkService)).toString());
            fieldValueLabel.setPadding(new Insets(0, 0, 0, 3));
            hBox.getChildren().addAll(fieldNameLabel, fieldValueLabel);
            descriptionAndInfoContainer.getChildren().add(hBox);
        }
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
                getParkServiceID());
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
        JavaFXUtils.setStageTitle(event, "reviews");
    }

    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH")
    private String getParkServiceID() {
        return (String) Objects.requireNonNull(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.PARKSERVICEID)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0)
                .get(0));
    }
}
