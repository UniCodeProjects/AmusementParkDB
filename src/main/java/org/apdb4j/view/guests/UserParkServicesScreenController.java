package org.apdb4j.view.guests;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.apdb4j.db.tables.ParkServices;
import org.apdb4j.db.tables.Rides;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.util.*;

import static org.apdb4j.db.Tables.*;

/**
 * FXML controller for the screen that allows the user to see all the rides' info.
 */
@SuppressFBWarnings("NP_NULL_ON_SOME_PATH") // TODO: remove. The code that causes the false positive should be in the controller.
// TODO: remove all the direct usages of the model. Only for testing GUI.
public class UserParkServicesScreenController extends BackableAbstractFXMLController {

    private static final double PARK_SERVICES_INFO_FONT_SIZE = 18;
    private static final double FILTERS_TITLE_FONT_SIZE = 14;
    private static final double ATTRIBUTE_NAME_FONT_SIZE = 12;
    private static final double FILTERS_MENU_WIDTH = 250;
    private static final double CHECKBOXES_PREF_WIDTH = 150;
    private static final Insets ATTRIBUTES_TITLE_PADDING = new Insets(5, 0, 0, 0);
    private static final int MAX_RATING = 5;

    @FXML
    private BorderPane pane;
    @FXML
    private ListView<Hyperlink> parkServicesListView;
    @FXML
    private Button showFiltersButton;
    @FXML
    private ComboBox<String> sortMenu;
    private boolean areFiltersOpen;
    private final ScrollPane filterScrollableContainer = new ScrollPane();
    private final ToolBar filtersToolBar = new ToolBar();
    private final List<CheckBox> filters = new ArrayList<>();
    private final Set<String> sortMenuFields = Set.of("Name", "Average rating");

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        initializeSortMenu();
        initializeRidesListView();
        initializeFiltersMenu();
    }

    /**
     * Opens the filter toolbar.
     * @param event the click on the "show filters" button.
     */
    @FXML
    void onFilterButtonPressed(final ActionEvent event) {
        if (!areFiltersOpen) {
            showFiltersButton.setText("Hide filters");
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

    private void initializeSortMenu() {
        sortMenuFields.forEach(field -> sortMenu.getItems().addAll(field + " (ascending)", field + " (descending)"));
        sortMenu.getItems().add("No sorting");
    }

    private void initializeRidesListView() {
        final var queryBuilder = new QueryBuilder();
        final var rides = queryBuilder.createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NAME, RIDES.INTENSITY)
                        .from(PARK_SERVICES, RIDES)
                        .where(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        for (final var ride : rides) {
            final StringBuilder rideInfo = new StringBuilder();
            for (int i = 0; i < ride.size(); i++) {
                final String iteratingFieldName = Objects.requireNonNull(ride.field(i)).getName();
                rideInfo.append(iteratingFieldName).append(": ").append(ride.get(i)).append(' ');
            }
            final var rideInfoHyperlink = new Hyperlink(rideInfo.toString());
            final var rideName = ride.get(PARK_SERVICES.NAME);
            rideInfoHyperlink.setOnAction(event ->
                    LoadFXML.fromEventAsPopup(event,
                            ParkServicesInfoScreenController.class,
                            rideName + " info",
                            0.5,
                            0.5,
                            rideName));
            rideInfoHyperlink.setFont(Font.font(PARK_SERVICES_INFO_FONT_SIZE));
            rideInfoHyperlink.setFocusTraversable(false);
            parkServicesListView.getItems().add(rideInfoHyperlink);
            rideInfo.delete(0, rideInfo.length());
        }
    }

    private void addNewAttributeToFilters(final String attributeName) {
        final var attributeFilterTitle = new Label(attributeName);
        attributeFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        attributeFilterTitle.setPadding(ATTRIBUTES_TITLE_PADDING);
        filtersToolBar.getItems().add(attributeFilterTitle);
    }

    private void initializeFiltersMenu() {
        filterScrollableContainer.setPrefWidth(FILTERS_MENU_WIDTH);
        filterScrollableContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        filtersToolBar.setPrefWidth(FILTERS_MENU_WIDTH);
        filtersToolBar.setOrientation(Orientation.VERTICAL);
        filterScrollableContainer.setContent(filtersToolBar);

        final var filtersTitle = new Label("Filters");
        filtersTitle.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, FILTERS_TITLE_FONT_SIZE));
        filtersToolBar.getItems().add(filtersTitle);

        addNewAttributeToFilters("Intensity");
        final var intensities = new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(Rides.RIDES.INTENSITY)
                        .from(Rides.RIDES)
                        .fetch())
                .closeConnection().getResultAsRecords();
        intensities.forEach(intensity -> {
            final var intensityCheckbox = new CheckBox(Objects.requireNonNull(intensity.get(0)).toString());
            intensityCheckbox.setPrefWidth(CHECKBOXES_PREF_WIDTH);
            filters.add(intensityCheckbox);
            filtersToolBar.getItems().add(intensityCheckbox);
        });

        addNewAttributeToFilters("Average rating");
        final Slider averageRatingSlider = new Slider(1, MAX_RATING, MAX_RATING);
        averageRatingSlider.setBlockIncrement(1);
        averageRatingSlider.setShowTickMarks(true);
        averageRatingSlider.setShowTickLabels(true);
        averageRatingSlider.setMajorTickUnit(1);
        averageRatingSlider.setMinorTickCount(0);
        averageRatingSlider.setSnapToTicks(true);
        filtersToolBar.getItems().add(averageRatingSlider);
        final CheckBox rangedCheckBox = new CheckBox("Ranged");
        rangedCheckBox.setPrefWidth(CHECKBOXES_PREF_WIDTH);
        filtersToolBar.getItems().add(rangedCheckBox);

        addNewAttributeToFilters("Type");
        final var rideTypes = new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(ParkServices.PARK_SERVICES.TYPE)
                        .from(ParkServices.PARK_SERVICES)
                        .where(ParkServices.PARK_SERVICES.PARKSERVICEID.like("RI%"))
                        .fetch())
                .closeConnection().getResultAsRecords();
        rideTypes.forEach(type -> {
            final var typeCheckbox = new CheckBox(Objects.requireNonNull(type.get(0)).toString());
            typeCheckbox.setPrefWidth(CHECKBOXES_PREF_WIDTH);
            filters.add(typeCheckbox);
            filtersToolBar.getItems().add(typeCheckbox);
        });

        filters.forEach(checkBox -> checkBox.setPrefWidth(CHECKBOXES_PREF_WIDTH));

        final var applyFiltersButton = new Button("Apply filters");
        final var resetFiltersButton = new Button("Reset filters");
        applyFiltersButton.setCursor(Cursor.HAND);
        resetFiltersButton.setOnAction(e -> filters.forEach(checkBox -> checkBox.setSelected(false)));
        resetFiltersButton.setCursor(Cursor.HAND);
        filtersToolBar.getItems().addAll(applyFiltersButton, resetFiltersButton);
    }
}