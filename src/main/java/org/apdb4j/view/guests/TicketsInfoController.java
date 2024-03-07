package org.apdb4j.view.guests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apdb4j.controllers.SessionManager;
import org.apdb4j.controllers.guests.BoughtTicketsController;
import org.apdb4j.controllers.guests.BoughtTicketsControllerImpl;
import org.apdb4j.controllers.guests.TicketControllerImpl;
import org.apdb4j.controllers.guests.TicketType;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see more information on the tickets bought.
 */
public class TicketsInfoController extends BackableAbstractFXMLController {

    private static final int TITLE_FONT_SIZE = 24;
    private static final int FILTERS_MENU_WIDTH = 250;
    @FXML
    private BorderPane pane;
    @FXML
    private Label title;
    @FXML
    private Button showFiltersButton;
    @FXML
    private TableView<TicketTableItem> tableView;
    private final BoughtTicketsController controller;
    private final TicketType ticketType;
    private boolean areFiltersOpen;
    private final ScrollPane filterScrollableContainer = new ScrollPane();
    private final ToolBar filtersToolBar = new ToolBar();
    private final ToggleGroup filters = new ToggleGroup();

    /**
     * Default constructor. Creates a new instance of this class that shows all the info about the tickets
     * of the provided type bought by the currently logged user so far.
     * @param ticketType the ticket type.
     */
    public TicketsInfoController(final @NonNull TicketType ticketType) {
        this.ticketType = ticketType;
        controller = new BoughtTicketsControllerImpl(ticketType);
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
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        title.setText(SessionManager.getSessionManager().getSession().username() + "'s " + ticketType.getName() + "s");
        title.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, TITLE_FONT_SIZE));
        final TableColumn<TicketTableItem, String> ticketIDColumn = new TableColumn<>("Ticket ID");
        final TableColumn<TicketTableItem, LocalDate> purchaseDateColumn = new TableColumn<>("Purchase date");
        final TableColumn<TicketTableItem, LocalDate> validOnOrValidUntilColumn = ticketType.equals(TicketType.SINGLE_DAY_TICKET)
                ? new TableColumn<>("Valid on") : new TableColumn<>("Valid until");
        final TableColumn<TicketTableItem, Integer> remainingEntrancesColumn = new TableColumn<>("Remaining entrances");
        final TableColumn<TicketTableItem, Year> yearColumn = new TableColumn<>("Price list");
        final TableColumn<TicketTableItem, String> categoryColumn = new TableColumn<>("Category");

        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        validOnOrValidUntilColumn.setCellValueFactory(ticketType.equals(TicketType.SINGLE_DAY_TICKET)
                ? new PropertyValueFactory<>("validOn") : new PropertyValueFactory<>("validUntil"));
        remainingEntrancesColumn.setCellValueFactory(new PropertyValueFactory<>("remainingEntrances"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        tableView.getColumns().addAll(List.of(ticketIDColumn,
                purchaseDateColumn,
                validOnOrValidUntilColumn,
                remainingEntrancesColumn,
                yearColumn,
                categoryColumn));
        tableView.getItems().addAll(controller.getAllData());

        initializeFiltersMenu();
    }

    private void initializeFiltersMenu() {
        filterScrollableContainer.setPrefWidth(FILTERS_MENU_WIDTH);
        filterScrollableContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        filtersToolBar.setPrefWidth(FILTERS_MENU_WIDTH);
        filtersToolBar.setOrientation(Orientation.VERTICAL);
        filterScrollableContainer.setContent(filtersToolBar);

        final var filtersTitle = new Label("Filters");
        filtersTitle.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 14));
        filtersToolBar.getItems().add(filtersTitle);

        final var categoryFilterTitle = new Label("Category");
        categoryFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, 12));
        filtersToolBar.getItems().add(categoryFilterTitle);

        new TicketControllerImpl().getCustomerCategories().forEach(category -> {
            final RadioButton categoryFilterButton = new RadioButton(StringUtils.capitalize(category));
            categoryFilterButton.setToggleGroup(filters);
            filtersToolBar.getItems().add(categoryFilterButton);
            categoryFilterButton.setOnAction(e -> {
                tableView.getItems().clear();
                tableView.getItems().addAll(controller.filterByCategory(category));
            });
        });

        final var validityFilterTitle = new Label("Validity");
        validityFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, 12));
        filtersToolBar.getItems().add(validityFilterTitle);

        final RadioButton validTicketsButton = new RadioButton("Valid");
        validTicketsButton.setToggleGroup(filters);
        filtersToolBar.getItems().add(validTicketsButton);
        validTicketsButton.setOnAction(e -> {
            tableView.getItems().clear();
            tableView.getItems().addAll(controller.getValidTickets());
        });

        final RadioButton expiredTicketsButton = new RadioButton("Expired");
        expiredTicketsButton.setToggleGroup(filters);
        filtersToolBar.getItems().add(expiredTicketsButton);
        expiredTicketsButton.setOnAction(e -> {
            tableView.getItems().clear();
            tableView.getItems().addAll(controller.getExpiredTickets());
        });

        final var yearFilterTitle = new Label("Year");
        yearFilterTitle.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, 12));
        filtersToolBar.getItems().add(yearFilterTitle);

        controller.getPriceListYears().forEach(year -> {
            final RadioButton yearFilterButton = new RadioButton(year.toString());
            yearFilterButton.setToggleGroup(filters);
            filtersToolBar.getItems().add(yearFilterButton);
            yearFilterButton.setOnAction(e -> {
                tableView.getItems().clear();
                tableView.getItems().addAll(controller.filterByPriceListYear(year));
            });
        });

        final Button resetFiltersButton = new Button("Reset filters");
        resetFiltersButton.setOnAction(e -> {
            Optional.ofNullable(filters.getSelectedToggle()).ifPresent(filterButton -> filterButton.setSelected(false));
            tableView.getItems().clear();
            tableView.getItems().addAll(controller.getAllData());
        });
        resetFiltersButton.setCursor(Cursor.HAND);
        filtersToolBar.getItems().add(resetFiltersButton);
    }
}

