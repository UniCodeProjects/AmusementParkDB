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
import org.apdb4j.controllers.guests.*;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.util.*;

/**
 * FXML controller for the screen that allows the user to see all the rides' info.
 */
public class UserParkServicesScreenController extends BackableAbstractFXMLController {

    private static final double PARK_SERVICE_INFO_FONT_SIZE = 18;
    private static final double FILTERS_TITLE_FONT_SIZE = 14;
    private static final double ATTRIBUTE_NAME_FONT_SIZE = 12;
    private static final double FILTERS_MENU_WIDTH = 250;
    private static final double CHECKBOXES_PREF_WIDTH = 150;
    private static final Insets ATTRIBUTES_TITLE_PADDING = new Insets(5, 0, 0, 0);

    @FXML
    private BorderPane pane;
    @FXML
    private ListView<Hyperlink> parkServicesListView;
    @FXML
    private Button estimatedWaitTimesButton;
    @FXML
    private Button showFiltersButton;
    private boolean areFiltersOpen;
    private final ScrollPane filterScrollableContainer = new ScrollPane();
    private final ToolBar filtersToolBar = new ToolBar();
    private final ParkServiceType parkServiceType;
    private final ParkServiceController controller;
    private final ToggleGroup sortByButtons = new ToggleGroup();
    private final ToggleGroup filters = new ToggleGroup();

    /**
     * Creates a new instance of this class that refers to the provided {@code parkServiceType}.
     * For instance, if the provided {@code parkServiceType} is {@link ParkServiceType#RIDE}, the
     * screen will be prepared with all the information about all the rides, and so on.
     * @param parkServiceType the park service type to which the screen controlled by this class refers to.
     */
    public UserParkServicesScreenController(final ParkServiceType parkServiceType) {
        this.parkServiceType = parkServiceType;
        controller = switch (parkServiceType) {
            case RIDE -> new RideController();
            case SHOP -> new ShopController();
            case RESTAURANT -> new RestaurantController();
            case EXHIBITION -> new ExhibitionController();
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
        initializeParkServicesListView();
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
        LoadFXML.fromEventAsPopup(event,
                "layouts/live-estimated-wait-times.fxml",
                "Live rides estimated wait times!",
                0.5,
                0.5);
    }

    private void initializeSortFields() {
        controller.getSortFields().forEach(sortField -> {
            final RadioButton sortFieldButtonAscending = new RadioButton(sortField + " (ascending)");
            filtersToolBar.getItems().add(sortFieldButtonAscending);
            final RadioButton sortFieldButtonDescending = new RadioButton(sortField + " (descending)");
            filtersToolBar.getItems().add(sortFieldButtonDescending);
            sortFieldButtonAscending.setToggleGroup(sortByButtons);
            sortFieldButtonDescending.setToggleGroup(sortByButtons);
        });
    }

    private void initializeParkServicesListView() {
        controller.getMainInfo().forEach(parkService -> {
            final Hyperlink rideHyperlink = new Hyperlink();
            parkService.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()))
                    .forEach(entry -> rideHyperlink.setText(rideHyperlink.getText() + entry.getKey() + ": " + entry.getValue() + " - "));
            rideHyperlink.setOnAction(e -> LoadFXML.fromEventAsPopup(e,
                    ParkServicesInfoScreenController.class,
                    parkService.get("Name") + " info",
                    0.5,
                    0.5,
                    parkService.get("Name"),
                    controller,
                    parkServiceType));
            rideHyperlink.setFont(new Font(PARK_SERVICE_INFO_FONT_SIZE));
            rideHyperlink.setFocusTraversable(false);
            parkServicesListView.getItems().add(rideHyperlink);
        });
    }

    private void addNewAttributeToFilters(final String attributeName, final Collection<String> values) {
        final var attributeFilterTitle = new Label(attributeName);
        attributeFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        attributeFilterTitle.setPadding(ATTRIBUTES_TITLE_PADDING);
        filtersToolBar.getItems().add(attributeFilterTitle);
        values.forEach(value -> {
            final RadioButton valueRadioButton = new RadioButton(value);
            valueRadioButton.setPrefWidth(CHECKBOXES_PREF_WIDTH);
            valueRadioButton.setToggleGroup(filters);
            valueRadioButton.setOnAction(e -> {
                if (((RadioButton) e.getSource()).isSelected()) {
                    parkServicesListView.getItems().clear();
                    controller.filterBy(attributeName, value).forEach(parkService -> {
                        final Hyperlink rideHyperlink = new Hyperlink();
                        parkService.entrySet().stream()
                                .sorted((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()))
                                .forEach(entry -> rideHyperlink.setText(rideHyperlink.getText() + entry.getKey() + ": " + entry.getValue() + " - "));
                        rideHyperlink.setOnAction(e1 -> LoadFXML.fromEventAsPopup(e1,
                                ParkServicesInfoScreenController.class,
                                parkService.get("Name") + " info",
                                0.5,
                                0.5,
                                parkService.get("Name"),
                                controller,
                                parkServiceType));
                        rideHyperlink.setFont(new Font(PARK_SERVICE_INFO_FONT_SIZE));
                        rideHyperlink.setFocusTraversable(false);
                        parkServicesListView.getItems().add(rideHyperlink);
                    });
                }
            });
            filtersToolBar.getItems().add(valueRadioButton);
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
        controller.getAllFiltersWithValues().forEach(this::addNewAttributeToFilters);
        final Label averageRatingFilterTitle = new Label("Average rating");
        averageRatingFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        averageRatingFilterTitle.setPadding(ATTRIBUTES_TITLE_PADDING);
        filtersToolBar.getItems().add(averageRatingFilterTitle);
        final var averageRatingSlider = new Slider(1, 5, 5);
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
        rangedCheckBox.setOnAction(e -> {
//            if (filters.getSelectedToggle() != null) {
//                filters.getSelectedToggle().setSelected(false);
//            }
//            if (sortByButtons.getSelectedToggle() != null) {
//                sortByButtons.getSelectedToggle().setSelected(false);
//            }
            parkServicesListView.getItems().clear();
            controller.filterByAverageRating((int) averageRatingSlider.getValue(), rangedCheckBox.isSelected()).forEach(parkService -> {
                final Hyperlink rideHyperlink = new Hyperlink();
                parkService.entrySet().stream()
                        .sorted((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()))
                        .forEach(entry -> rideHyperlink.setText(rideHyperlink.getText() + entry.getKey() + ": " + entry.getValue() + " - "));
                rideHyperlink.setOnAction(e1 -> LoadFXML.fromEventAsPopup(e1,
                        ParkServicesInfoScreenController.class,
                        parkService.get("Name") + " info",
                        0.5,
                        0.5,
                        parkService.get("Name"),
                        controller,
                        parkServiceType));
                rideHyperlink.setFont(new Font(PARK_SERVICE_INFO_FONT_SIZE));
                rideHyperlink.setFocusTraversable(false);
                parkServicesListView.getItems().add(rideHyperlink);
            });
        });
        averageRatingSlider.valueProperty().addListener(((observableValue, previousValue, newValue) -> {
//            if (filters.getSelectedToggle() != null) {
//                filters.getSelectedToggle().setSelected(false);
//            }
//            if (sortByButtons.getSelectedToggle() != null) {
//                sortByButtons.getSelectedToggle().setSelected(false);
//            }
            final var previousListViewItems = parkServicesListView.getItems();
            parkServicesListView.getItems().clear();
            controller.filterByAverageRating(newValue.intValue(), rangedCheckBox.isSelected()).forEach(parkService -> {
                final Hyperlink rideHyperlink = new Hyperlink();
                parkService.entrySet().stream()
                        .sorted((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()))
                        .forEach(entry -> rideHyperlink.setText(rideHyperlink.getText() + entry.getKey() + ": " + entry.getValue() + " - "));
                rideHyperlink.setOnAction(e1 -> LoadFXML.fromEventAsPopup(e1,
                        ParkServicesInfoScreenController.class,
                        parkService.get("Name") + " info",
                        0.5,
                        0.5,
                        parkService.get("Name"),
                        controller,
                        parkServiceType));
                rideHyperlink.setFont(new Font(PARK_SERVICE_INFO_FONT_SIZE));
                rideHyperlink.setFocusTraversable(false);
                parkServicesListView.getItems().add(rideHyperlink);
            });
        }));

        final var resetFiltersButton = new Button("Reset filters");
        resetFiltersButton.setOnAction(e -> {
            if (filters.getSelectedToggle() != null) {
                filters.getSelectedToggle().setSelected(false);
            }
            if (sortByButtons.getSelectedToggle() != null) {
                sortByButtons.getSelectedToggle().setSelected(false);
            }
            averageRatingSlider.setValue(averageRatingSlider.getMax());
            rangedCheckBox.setSelected(false);
            parkServicesListView.getItems().clear();
            initializeParkServicesListView();
        });
        resetFiltersButton.setCursor(Cursor.HAND);
        filtersToolBar.getItems().addAll(resetFiltersButton);
    }
}
