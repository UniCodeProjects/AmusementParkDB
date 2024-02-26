package org.apdb4j.view.guests;

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
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.controllers.guests.RidesEstimatedWaitTimesController;
import org.apdb4j.controllers.guests.RidesEstimatedWaitTimesControllerImpl;
import org.apdb4j.view.FXMLController;

import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

/**
 * FXML controller for the screen that allows the user to see the estimated wait times for all the rides.
 */
public class LiveEstimatedWaitTimesController implements FXMLController, Initializable {

    private static final double FILTERS_MENU_WIDTH = 250;
    private static final double RIDE_INFO_FONT_SIZE = 18;
    private static final double FILTERS_TITLE_FONT_SIZE = 14;
    private static final double ATTRIBUTE_NAME_FONT_SIZE = 12;
    @FXML
    private BorderPane pane;
    @FXML
    private ListView<Label> ridesListView;
    @FXML
    private Button showFiltersButton;
    private final ScrollPane filtersScrollableContainer = new ScrollPane();
    private final ToolBar filtersToolBar = new ToolBar();
//    private final ToggleGroup filters = new ToggleGroup();
//    private final ToggleGroup sortButtons = new ToggleGroup();
    private final ToggleGroup sortAndFilterButtons = new ToggleGroup();
    private boolean areFiltersOpen;
    private final RidesEstimatedWaitTimesController controller = new RidesEstimatedWaitTimesControllerImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeRidesListView(controller.getRidesWithWaitTimes());
        initializeFiltersMenu();
    }

    private void addAttributeToFilters(final @NonNull String name,
                                       final @NonNull Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>
                                               valuesWithAction) {
        final var attributeFilterTitle = new Label(name);
        attributeFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        filtersToolBar.getItems().add(attributeFilterTitle);
        valuesWithAction.forEach(valueWithAction -> {
            final RadioButton filterOptionButton = new RadioButton(valueWithAction.getKey());
            filterOptionButton.setToggleGroup(sortAndFilterButtons);
            filtersToolBar.getItems().add(filterOptionButton);
            filterOptionButton.setOnAction(e -> initializeRidesListView(valueWithAction.getValue().get()));
        });
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

        final var sortByTitle = new Label("Sort by");
        sortByTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, ATTRIBUTE_NAME_FONT_SIZE));
        filtersToolBar.getItems().add(sortByTitle);
        initializeSortFields();

        controller.getFiltersWithValuesAndAction().forEach(this::addAttributeToFilters);

        final var resetFiltersButton = new Button("Reset filters");
        resetFiltersButton.setCursor(Cursor.HAND);
        resetFiltersButton.setOnAction(event -> {
            if (sortAndFilterButtons.getSelectedToggle() != null) {
                sortAndFilterButtons.getSelectedToggle().setSelected(false);
            }
            initializeRidesListView(controller.getRidesWithWaitTimes());
        });
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

    private void initializeRidesListView(final @NonNull Collection<Map<String, String>> rides) {
        ridesListView.getItems().clear();
        rides.forEach(ride -> {
            final Map<String, String> rideEstimatedWaitTimesInfo = new HashMap<>(ride);
            final var label = new Label();
            label.setFont(new Font(RIDE_INFO_FONT_SIZE));
            label.setText("Name: " + ride.get("Name") + " - ");
            rideEstimatedWaitTimesInfo.remove("Name");
            rideEstimatedWaitTimesInfo.forEach((attribute, value) ->
                    label.setText(label.getText() + attribute + ": " + value + " - "));
            ridesListView.getItems().add(label);
        });
        ridesListView.getItems().forEach(label -> {
            final var labelText = label.getText();
            label.setText(new StringBuilder(labelText).replace(labelText.length() - 3, labelText.length(), "").toString());
        });
    }

    private void initializeSortFields() {
        controller.getSortOptionsWithActions().forEach((sortOption, action) -> {
            final RadioButton sortOptionButton = new RadioButton(sortOption);
            sortOptionButton.setOnAction(e -> {
                if (((RadioButton) e.getSource()).isSelected()) {
                    initializeRidesListView(action.get());
                }
            });
            filtersToolBar.getItems().add(sortOptionButton);
            sortOptionButton.setToggleGroup(sortAndFilterButtons);
        });
    }
}
