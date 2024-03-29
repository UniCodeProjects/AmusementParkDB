package org.apdb4j.view.guests;

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
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.controllers.guests.ExhibitionOverviewController;
import org.apdb4j.controllers.guests.ParkServiceOverviewController;
import org.apdb4j.controllers.guests.ParkServiceType;
import org.apdb4j.controllers.guests.RestaurantOverviewController;
import org.apdb4j.controllers.guests.RideOverviewController;
import org.apdb4j.controllers.guests.ShopOverviewController;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

/**
 * FXML controller for the screen that allows the user to see all the rides' info.
 */
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
    private Label title;
    @FXML
    private ListView<Hyperlink> parkServicesListView;
    @FXML
    private Button showFiltersButton;
    @FXML
    private Button estimatedWaitTimesButton;
    private boolean areFiltersOpen;
    private final ScrollPane filterScrollableContainer = new ScrollPane();
    private final ToolBar filtersToolBar = new ToolBar();
    private final ToggleGroup sortAndFilterButtons = new ToggleGroup();
    private final ParkServiceType parkServiceType;
    private final ParkServiceOverviewController controller;
    private final Slider averageRatingSlider = new Slider(1, MAX_RATING, MAX_RATING);
    private final CheckBox rangedCheckBox = new CheckBox("Ranged");

    /**
     * Default constructor.
     * @param type the type of the park service handled by this screen.
     */
    public UserParkServicesScreenController(final @NonNull ParkServiceType type) {
        parkServiceType = type;
        controller = switch (type) {
            case RIDE -> new RideOverviewController();
            case EXHIBITION -> new ExhibitionOverviewController();
            case SHOP -> new ShopOverviewController();
            case RESTAURANT -> new RestaurantOverviewController();
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        if (!parkServiceType.equals(ParkServiceType.RIDE)) {
            estimatedWaitTimesButton.setVisible(false);
        }

        title.setText(title.getText() + (parkServiceType.getName() + "s").toLowerCase(Locale.getDefault()));

        averageRatingSlider.setBlockIncrement(1);
        averageRatingSlider.setShowTickMarks(true);
        averageRatingSlider.setShowTickLabels(true);
        averageRatingSlider.setMajorTickUnit(1);
        averageRatingSlider.setMinorTickCount(0);
        averageRatingSlider.setSnapToTicks(true);
        averageRatingSlider.valueProperty().addListener((observableValue, previousValue, newValue) -> {
            Optional.ofNullable(sortAndFilterButtons.getSelectedToggle()).ifPresent(button -> button.setSelected(false));
            initializeListView(controller.filterByAverageRating(newValue.intValue(), rangedCheckBox.isSelected()));
        });

        rangedCheckBox.setSelected(true);
        rangedCheckBox.setPrefWidth(CHECKBOXES_PREF_WIDTH);
        rangedCheckBox.setOnAction(e -> {
            initializeListView(controller
                    .filterByAverageRating((int) averageRatingSlider.getValue(), rangedCheckBox.isSelected()));
            Optional.ofNullable(sortAndFilterButtons.getSelectedToggle()).ifPresent(button -> button.setSelected(false));
        });

        initializeListView(controller.getOverview());
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
        LoadFXML.fromEventAsPopup(event, "layouts/live-estimated-wait-times.fxml", "Live rides estimated wait times!", 0.9, 0.7);
    }

    private void initializeSortFields() {
        controller.getSortOptionsWithActions().forEach((sortOption, action) -> {
            final RadioButton sortOptionButton = new RadioButton(sortOption);
            sortOptionButton.setOnAction(e -> {
                if (((RadioButton) e.getSource()).isSelected()) {
                    resetAverageRatingFilter((RadioButton) e.getSource());
                    initializeListView(action.get());
                }
            });
            filtersToolBar.getItems().add(sortOptionButton);
            sortOptionButton.setToggleGroup(sortAndFilterButtons);
        });
    }

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    private void initializeListView(final Collection<Map<String, String>> parkServices) {
        parkServicesListView.getItems().clear();
        parkServices.forEach(parkService -> {
            final var parkServiceInfo = new HashMap<>(parkService);
            final Hyperlink parkServiceHyperlink = new Hyperlink();
            parkServiceHyperlink.setText("Name: " + parkServiceInfo.get("Name") + " - ");
            parkServiceInfo.remove("Name");
            parkServiceInfo.forEach((attribute, value) ->
                    parkServiceHyperlink.setText(parkServiceHyperlink.getText() + attribute + ": " + value + " - "));
            parkServiceHyperlink.setOnAction(e -> LoadFXML.fromEventAsPopup(e,
                    ParkServicesInfoScreenController.class,
                    parkService.get("Name") + " info",
                    0.5,
                    0.5,
                    JavaFXUtils.getStage(e),
                    parkService.get("Name"), parkServiceType, this));
            parkServiceHyperlink.setFont(new Font(PARK_SERVICES_INFO_FONT_SIZE));
            parkServiceHyperlink.setFocusTraversable(false);
            parkServicesListView.getItems().add(parkServiceHyperlink);
        });
        parkServicesListView.getItems().forEach(parkServiceHyperlink -> {
            final var hyperlinkText = parkServiceHyperlink.getText();
            parkServiceHyperlink.setText(new StringBuilder(hyperlinkText)
                    .replace(hyperlinkText.length() - 3, hyperlinkText.length(), "").toString());
        });
    }

    private void addNewAttributeToFilters(final String attributeName,
                                          final @NonNull Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>
                                                  valuesWithAction) {
        final var attributeFilterTitle = new Label(attributeName);
        attributeFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        attributeFilterTitle.setPadding(ATTRIBUTES_TITLE_PADDING);
        filtersToolBar.getItems().add(attributeFilterTitle);
        valuesWithAction.forEach(valueWithAction -> {
            final RadioButton filterOptionButton = new RadioButton(valueWithAction.getKey());
            filterOptionButton.setToggleGroup(sortAndFilterButtons);
            filtersToolBar.getItems().add(filterOptionButton);
            filterOptionButton.setOnAction(e -> {
                resetAverageRatingFilter((RadioButton) e.getSource());
                initializeListView(valueWithAction.getValue().get());
            });
        });
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

        final var sortByTitle = new Label("Sort by");
        sortByTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        filtersToolBar.getItems().add(sortByTitle);
        initializeSortFields();

        controller.getFiltersWithValuesAndAction().forEach(this::addNewAttributeToFilters);

        final var averageRatingFilterTitle = new Label("Average rating");
        averageRatingFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        averageRatingFilterTitle.setPadding(ATTRIBUTES_TITLE_PADDING);
        filtersToolBar.getItems().add(averageRatingFilterTitle);

        filtersToolBar.getItems().add(averageRatingSlider);
        filtersToolBar.getItems().add(rangedCheckBox);

        final var resetFiltersButton = new Button("Reset filters");
        resetFiltersButton.setOnAction(e -> {
            if (sortAndFilterButtons.getSelectedToggle() != null) {
                sortAndFilterButtons.getSelectedToggle().setSelected(false);
            }
            averageRatingSlider.setValue(MAX_RATING);
            rangedCheckBox.setSelected(true);
            initializeListView(controller.getOverview());
        });
        resetFiltersButton.setCursor(Cursor.HAND);
        filtersToolBar.getItems().add(resetFiltersButton);
    }

    private void resetAverageRatingFilter(final RadioButton button) {
        if (!rangedCheckBox.isSelected()) {
            rangedCheckBox.setSelected(true);
        }
        if (((int) averageRatingSlider.getValue()) != MAX_RATING) {
            averageRatingSlider.setValue(MAX_RATING);
        }
        // Selecting the button because the slider's event handler deselects all the buttons when its value changes.
        button.setSelected(true);
    }
}
