package org.apdb4j.view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.apdb4j.core.managers.Manager;
import org.apdb4j.db.tables.ParkServices;
import org.apdb4j.db.tables.Rides;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see all the rides' info.
 */
@SuppressFBWarnings("NP_NULL_ON_SOME_PATH") // TODO: remove. The code that causes the false positive should be in the controller.
// TODO: remove all the direct usages of the model. Only for testing GUI.
public class UserRidesScreenController extends AbstractFXMLController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button estimatedWaitTimesButton;

    @FXML
    private BorderPane pane;

    @FXML
    private ListView<Hyperlink> ridesListView;

    @FXML
    private Button showFiltersButton;

    @FXML
    private ComboBox<String> sortMenu;

    private boolean areFiltersOpen;
    private final ScrollPane filterScrollableContainer = new ScrollPane();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.setBackButtonImage(backButton);
        sortMenu.getItems().add("Name (ascending)");
        sortMenu.getItems().add("Name (descending)");
        sortMenu.getItems().add("Average rating (ascending)");
        sortMenu.getItems().add("Average rating (descending)");
        sortMenu.getItems().add("No sorting");
        final var rides = Manager.viewAllInfoFromTable("rides", "");
        for (final var ride : rides) {
            final StringBuilder rideInfo = new StringBuilder();
            for (int i = 0; i < ride.size(); i++) {
                final String iteratingFieldName = Objects.requireNonNull(ride.field(i)).getName();
                if ("intensity".equalsIgnoreCase(iteratingFieldName) || "rideid".equalsIgnoreCase(iteratingFieldName)) {
                    rideInfo.append(iteratingFieldName).append(": ").append(ride.get(i)).append(' ');
                }
            }
            final var rideInfoHyperlink = new Hyperlink(rideInfo.toString());
            rideInfoHyperlink.setFont(Font.font(18));
            rideInfoHyperlink.setFocusTraversable(false);
            ridesListView.getItems().add(rideInfoHyperlink);
            rideInfo.delete(0, rideInfo.length());
        }
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
     * Opens the filter toolbar.
     * @param event the click on the "show filters" button.
     */
    @FXML
    void onFilterButtonPressed(final ActionEvent event) {
        if (!areFiltersOpen) {
            showFiltersButton.setText("Hide filters");
            filterScrollableContainer.setPrefWidth(250);
            filterScrollableContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            final var filterToolBar = new ToolBar();
            filterToolBar.setPrefWidth(250);
            filterToolBar.setOrientation(Orientation.VERTICAL);
            filterScrollableContainer.setContent(filterToolBar);

            final var filtersTitle = new Label("Filters");
            filtersTitle.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 14));
            filtersTitle.setPadding(new Insets(0, 0, 5, 0));

            final var intensityFilterTitle = new Label("Intensity");
            intensityFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, 12));

            filterToolBar.getItems().add(filtersTitle);
            filterToolBar.getItems().add(intensityFilterTitle);

            final var intensities = new QueryBuilder().createConnection()
                    .queryAction(db -> db.selectDistinct(Rides.RIDES.INTENSITY)
                            .from(Rides.RIDES)
                            .fetch())
                    .closeConnection().getResultAsRecords();
            intensities.forEach(intensity -> {
                final var intensityCheckbox = new CheckBox(Objects.requireNonNull(intensity.get(0)).toString());
                intensityCheckbox.setPrefWidth(100);
                filterToolBar.getItems().add(intensityCheckbox);
            });

            final var averageRatingTitle = new Label("Average rating");
            averageRatingTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, 12));
            averageRatingTitle.setPadding(new Insets(5, 0, 0, 0));

            filterToolBar.getItems().add(averageRatingTitle);

            final var hBox1 = new HBox();
            final var hBox2 = new HBox();

            final var firstRatingInterval = new CheckBox("0-1.9 stars");
            final var secondRatingInterval = new CheckBox("2-2.9 stars");
            secondRatingInterval.setPadding(new Insets(0, 0, 0, 5));

            final var thirdRatingInterval = new CheckBox("3-3.9 stars");
            final var lastRatingInterval = new CheckBox("4-5 stars");
            lastRatingInterval.setPadding(new Insets(0, 0, 0, 5));

            hBox1.getChildren().add(firstRatingInterval);
            hBox1.getChildren().add(secondRatingInterval);
            hBox2.getChildren().add(thirdRatingInterval);
            hBox2.getChildren().add(lastRatingInterval);

            hBox1.getChildren().forEach(node -> {
                if (node instanceof Region) {
                    ((Region) node).setPrefWidth(100);
                }
            });
            hBox2.getChildren().forEach(node -> {
                if (node instanceof Region) {
                    ((Region) node).setPrefWidth(100);
                }
            });
            hBox2.setPadding(new Insets(0, 0, 5, 0));

            filterToolBar.getItems().add(hBox1);
            filterToolBar.getItems().add(hBox2);

            final var typeFilterTitle = new Label("Type");
            typeFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, 12));
            filterToolBar.getItems().add(typeFilterTitle);

            final var rideTypes = new QueryBuilder().createConnection()
                    .queryAction(db -> db.selectDistinct(ParkServices.PARK_SERVICES.TYPE)
                            .from(ParkServices.PARK_SERVICES)
                            .where(ParkServices.PARK_SERVICES.PARKSERVICEID.like("RI%"))
                            .fetch())
                    .closeConnection().getResultAsRecords();
            rideTypes.forEach(type -> {
                final var typeCheckbox = new CheckBox(Objects.requireNonNull(type.get(0)).toString());
                typeCheckbox.setPrefWidth(100);
                filterToolBar.getItems().add(typeCheckbox);
            });

            final var applyFiltersButton = new Button("Apply filters");
            applyFiltersButton.setCursor(Cursor.HAND);
            filterToolBar.getItems().add(applyFiltersButton);
            final var resetFiltersButton = new Button("Reset filters");
            resetFiltersButton.setCursor(Cursor.HAND);
            filterToolBar.getItems().add(resetFiltersButton);

            pane.setRight(filterScrollableContainer);
            areFiltersOpen = true;
        } else {
            showFiltersButton.setText("Show filters");
            pane.getChildren().remove(filterScrollableContainer);
            areFiltersOpen = false;
        }
    }

    /**
     * Opens the popup screen that shows all the rides with their estimated wait times.
     * @param event the click on the "estimated wait times" button.
     */
    @FXML
    void onEstimatedWaitTimesButtonPressed(final ActionEvent event) {
        LoadFXML.fromEventAsPopup(event, "layouts/live-estimated-wait-times.fxml", "Live rides estimated wait times!", 0.5, 0.5);
    }
}
