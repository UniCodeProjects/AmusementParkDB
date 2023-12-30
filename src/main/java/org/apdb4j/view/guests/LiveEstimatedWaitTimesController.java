package org.apdb4j.view.guests;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.apdb4j.db.tables.ParkServices;
import org.apdb4j.db.tables.RideDetails;
import org.apdb4j.db.tables.Rides;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.FXMLController;

import java.net.URL;
import java.util.*;

/**
 * FXML controller for the screen that allows the user to see the estimated wait times for all the rides.
 */
// TODO: remove model references. Only for testing purposes.
@SuppressFBWarnings("NP_NULL_ON_SOME_PATH") // TODO: remove. The code that causes the false positive should be in the controller.
public class LiveEstimatedWaitTimesController implements FXMLController, Initializable {

    private static final double FILTERS_MENU_WIDTH = 250;
    private static final double RIDE_INFO_FONT_SIZE = 18;
    private static final double FILTERS_TITLE_FONT_SIZE = 14;
    private static final double ATTRIBUTE_NAME_FONT_SIZE = 12;
    private static final double CHECKBOXES_PREF_WIDTH = 100;


    @FXML
    private BorderPane pane;

    @FXML
    private ListView<Label> ridesListView;

    @FXML
    private Button showFiltersButton;

    @FXML
    private ComboBox<String> sortMenu;
    private final Set<String> sortMenuFields = Set.of("Name", "Wait time");

    private final ScrollPane filtersScrollableContainer = new ScrollPane();

    private final ToolBar filtersToolBar = new ToolBar();

    private final List<CheckBox> filters = new ArrayList<>();

    private boolean areFiltersOpen;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeRidesListView();
        initializeSortMenu();
        initializeFiltersMenu();
    }

    private void initializeFiltersMenu() {
        filtersScrollableContainer.setContent(filtersToolBar);
        filtersScrollableContainer.setPrefWidth(FILTERS_MENU_WIDTH);
        filtersScrollableContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        filtersToolBar.setOrientation(Orientation.VERTICAL);
        filtersToolBar.setPrefWidth(FILTERS_MENU_WIDTH);

        final var filtersTitle = new Label("Filters");
        filtersTitle.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, FILTERS_TITLE_FONT_SIZE));
        filtersTitle.setPadding(new Insets(0, 0, 5, 0));
        filtersToolBar.getItems().add(filtersTitle);

        final var intensityFilterTitle = new Label("Intensity");
        intensityFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        filtersToolBar.getItems().add(intensityFilterTitle);

        final var intensities = new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(Rides.RIDES.INTENSITY)
                        .from(Rides.RIDES)
                        .fetch())
                .closeConnection().getResultAsRecords();
        intensities.forEach(intensity -> {
            final var intensityCheckbox = new CheckBox(Objects.requireNonNull(intensity.get(0)).toString());
            filters.add(intensityCheckbox);
            filtersToolBar.getItems().add(intensityCheckbox);
        });

        filters.forEach(checkBox -> checkBox.setPrefWidth(CHECKBOXES_PREF_WIDTH));

        final var applyFiltersButton = new Button("Apply filters");
        applyFiltersButton.setCursor(Cursor.HAND);
        filtersToolBar.getItems().add(applyFiltersButton);
        final var resetFiltersButton = new Button("Reset filters");
        resetFiltersButton.setCursor(Cursor.HAND);
        resetFiltersButton.setOnAction(event -> filters.forEach(filter -> filter.setSelected(false)));
        filtersToolBar.getItems().add(resetFiltersButton);
    }

    /**
     * Opens the filter toolbar.
     * @param event the click on the "show filters" button.
     */
    @FXML
    void onShowFiltersButtonPressed(final ActionEvent event) {
        if (!areFiltersOpen) {
            showFiltersButton.setText("Hide filters");
            pane.setRight(filtersScrollableContainer);
            areFiltersOpen = true;
        } else {
            showFiltersButton.setText("Show filters");
            pane.getChildren().remove(filtersScrollableContainer);
            areFiltersOpen = false;
        }
    }

    private void initializeRidesListView() {
        final var rideDetails = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(ParkServices.PARK_SERVICES.NAME,
                                Rides.RIDES.INTENSITY,
                                RideDetails.RIDE_DETAILS.ESTIMATEDWAITTIME, RideDetails.RIDE_DETAILS.STATUS)
                        .from(ParkServices.PARK_SERVICES, Rides.RIDES, RideDetails.RIDE_DETAILS)
                        .where(ParkServices.PARK_SERVICES.PARKSERVICEID.eq(Rides.RIDES.RIDEID)
                                .and(Rides.RIDES.RIDEID.eq(RideDetails.RIDE_DETAILS.RIDEID)))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        for (final var rideDetail : rideDetails) {
            final var rideDetailLabel = new Label();
            rideDetailLabel.setFont(new Font(RIDE_INFO_FONT_SIZE));
            if (Objects.equals(RideDetails.RIDE_DETAILS.STATUS.getValue(rideDetail), "O")) {
                rideDetailLabel.setText("Ride: " + ParkServices.PARK_SERVICES.NAME.getValue(rideDetail)
                        + " Intensity: " + Rides.RIDES.INTENSITY.getValue(rideDetail) + " Estimated wait time: "
                        + RideDetails.RIDE_DETAILS.ESTIMATEDWAITTIME.getValue(rideDetail));
            } else {
                rideDetailLabel.setText("Ride: " + ParkServices.PARK_SERVICES.NAME.getValue(rideDetail)
                        + " Intensity: " + Rides.RIDES.INTENSITY.getValue(rideDetail) + " Estimated wait time: ride closed");
            }
            ridesListView.getItems().add(rideDetailLabel);
        }
    }

    private void initializeSortMenu() {
        sortMenuFields.forEach(field -> {
            sortMenu.getItems().add(field + " (ascending)");
            sortMenu.getItems().add(field + " (descending)");
        });
        sortMenu.getItems().add("No sorting");
    }
}
