package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.controllers.staff.ContractControllerImpl;
import org.apdb4j.controllers.staff.EmployeeControllerImpl;
import org.apdb4j.controllers.staff.ExhibitionController;
import org.apdb4j.controllers.staff.ExhibitionControllerImpl;
import org.apdb4j.controllers.staff.ExpensesControllerImpl;
import org.apdb4j.controllers.staff.MaintenanceController;
import org.apdb4j.controllers.staff.MaintenanceControllerImpl;
import org.apdb4j.controllers.staff.OverviewController;
import org.apdb4j.controllers.staff.OverviewControllerImpl;
import org.apdb4j.controllers.staff.PictureControllerImpl;
import org.apdb4j.controllers.staff.ReviewController;
import org.apdb4j.controllers.staff.ReviewControllerImpl;
import org.apdb4j.controllers.staff.RideControllerImpl;
import org.apdb4j.controllers.staff.ShopControllerImpl;
import org.apdb4j.controllers.staff.TicketControllerImpl;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.util.view.JavaFXUtils;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.staff.tableview.AttractionTableItem;
import org.apdb4j.view.staff.tableview.ContractTableItem;
import org.apdb4j.view.staff.tableview.EmployeeTableItem;
import org.apdb4j.view.staff.tableview.ExhibitionTableItem;
import org.apdb4j.view.staff.tableview.MaintenanceTableItem;
import org.apdb4j.view.staff.tableview.PictureTableItem;
import org.apdb4j.view.staff.tableview.ReviewTableItem;
import org.apdb4j.view.staff.tableview.RideTableItem;
import org.apdb4j.view.staff.tableview.ShopTableItem;
import org.apdb4j.view.staff.tableview.TicketTableItem;
import org.jooq.exception.DataAccessException;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * The FXML controller for the staff UI.
 */
public class StaffScreenController implements Initializable {

    private static final MaintenanceController MAINTENANCE_CONTROLLER = new MaintenanceControllerImpl();
    private static final String TOOLTIP_STRING = "Press BACKSPACE or DELETE to clear date";
    @FXML
    private TextField parkNameField;
    @FXML
    private TextField adminField;
    @FXML
    private TextField attractionsNumField;
    @FXML
    private TextField shopsNumField;
    @FXML
    private TextField employeesNumField;
    @FXML
    private TextField openingTimeField;
    @FXML
    private TextField closingTimeField;
    @FXML
    private DatePicker expensesDatePicker1;
    @FXML
    private DatePicker expensesDatePicker2;
    @FXML
    private Button addRowBtn;
    @FXML
    private Button deleteAllRowsBtn;
    @FXML
    private LineChart<String, Number> chart;
    @FXML
    private Button clearBtn;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ToggleGroup radioBtnToggle;
    @FXML
    private VBox expensesVBox;
    @FXML
    private TextField employeeSearchField;
    @FXML
    private TableView<EmployeeTableItem> employeeTableView;
    @FXML
    private TextField contractSearchField;
    @FXML
    private TableView<ContractTableItem> contractsTableView;
    @FXML
    private TextField ticketSearchField;
    @FXML
    private DatePicker ticketPurchaseDateFilter;
    @FXML
    private DatePicker ticketPunchDateFilter;
    @FXML
    private TableView<TicketTableItem> ticketTableView;
    @FXML
    private TableView<AttractionTableItem> attractionsTableView;
    @FXML
    private TextField attractionNameSearchField;
    @FXML
    private MenuButton advancedAttractionBtn;
    @FXML
    private TextField shopSearchField;
    @FXML
    private TableView<ShopTableItem> shopsTableView;
    @FXML
    private TableView<MaintenanceTableItem> maintenanceTableView;
    @FXML
    private TextField maintenanceSearchField;
    @FXML
    private DatePicker maintenanceSearchDatePicker;
    @FXML
    private CheckBox maintenanceRideFilter;
    @FXML
    private CheckBox maintenanceShopFilter;
    @FXML
    private TextField picturesSearchField;
    @FXML
    private TableView<PictureTableItem> picturesTableView;
    @FXML
    private TableView<ReviewTableItem> reviewsTableView;
    @FXML
    private TextField reviewSearchField;
    @FXML
    private DatePicker reviewDateFilter;
    @FXML
    private Slider ratingFilterSlider;
    @FXML
    private CheckBox rangedCheckBox;
    @FXML
    private ToggleGroup reviewFilterToggle;
    @FXML
    private RadioButton reviewRideFilter;
    @FXML
    private RadioButton reviewExhibitionFilter;
    @FXML
    private RadioButton reviewShopFilter;
    @FXML
    private ToggleGroup attractionsToggleGroup;
    @FXML
    private RadioButton ridesRadioBtn;
    @FXML
    private RadioButton exhibitionsRadioBtn;
    private int addRowCounter = 1;
    private static final int MAX_ROWS = 10;

    /**
     * Opens a popup window to change the user's password.
     * @param event the event
     */
    @FXML
    void changePasswordAction(final ActionEvent event) {
        LoadFXML.fromNodeAsPopup(menuBar, "layouts/change-password-popup.fxml", "Change password");
    }

    /**
     * Shows the park expenses on a line chart.
     * @param event the event
     */
    @FXML
    void showExpensesChart(final ActionEvent event) {
        chart.setAnimated(false);
        chart.getData().clear();
        chart.setAnimated(true);
        final List<DatePicker> datePickers = expensesVBox.getChildren().stream()
                .filter(node -> node instanceof HBox)
                .map(node -> ((HBox) node).getChildren())
                .flatMap(Collection::stream)
                .filter(node -> node instanceof DatePicker)
                .map(node -> (DatePicker) node)
                .toList();
        if (datePickers.stream().map(DatePicker::getValue).anyMatch(Objects::isNull)) {
            new AlertBuilder(Alert.AlertType.ERROR)
                    .setContentText("Missing data in date picker.")
                    .show();
            return;
        }
        final List<Pair<LocalDate, LocalDate>> datePairs = new ArrayList<>();
        for (int i = 0; i < datePickers.size(); i += 2) {
            datePairs.add(new ImmutablePair<>(datePickers.get(i).getValue(), datePickers.get(i + 1).getValue()));
        }
        datePairs.forEach(dates -> {
            final String seriesName = "Expenses " + (chart.getData().size() + 1);
            final XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(seriesName);
            final LocalDate fromDate = dates.getLeft();
            final LocalDate toDate = dates.getRight();
            Pair<Double, Double> income;
            try {
                income = new ExpensesControllerImpl().getIncome(
                        YearMonth.of(fromDate.getYear(), fromDate.getMonth()),
                        YearMonth.of(toDate.getYear(), toDate.getMonth()));
            } catch (final IllegalArgumentException e) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText(e.getMessage())
                        .show();
                return;
            }
            series.getData().add(new XYChart.Data<>(fromDate.getMonth().getDisplayName(TextStyle.SHORT,
                    Locale.ROOT), income.getLeft()));
            series.getData().add(new XYChart.Data<>(toDate.getMonth().getDisplayName(TextStyle.SHORT,
                    Locale.ROOT), income.getRight()));
            chart.getData().add(series);
        });
    }

    /**
     * Adds a new row of date pickers and relative buttons.
     * @param event the event
     */
    @FXML
    void addRow(final ActionEvent event) {
        final var label = new Label("Net profit");
        final var datePicker1 = new DatePicker();
        final var datePicker2 = new DatePicker();
        datePicker1.setPromptText("Start date");
        datePicker1.setMaxWidth(Double.MAX_VALUE);
        datePicker1.setDayCellFactory(param -> new JavaFXUtils.FirstDayDateCell());
        datePicker2.setPromptText("End date");
        datePicker2.setMaxWidth(Double.MAX_VALUE);
        datePicker2.setDayCellFactory(param -> new JavaFXUtils.FirstDayDateCell());

        final var clearBtn = new Button("Clear");
        clearBtn.setMaxWidth(Double.MAX_VALUE);
        clearBtn.setDisable(true);
        final var deleteBtn = new Button("Delete");
        deleteBtn.setMaxWidth(Double.MAX_VALUE);

        final var spacing = 20;
        final var container = new HBox(spacing, label, datePicker1, datePicker2, clearBtn, deleteBtn);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(5, 5, 5, 5));

        addListenersToDatePicker(datePicker1, datePicker2, clearBtn);

        // Adds the clear feature for the button.
        clearBtn.setOnAction(e -> clearDatePickers(datePicker1, datePicker2, clearBtn));

        // Adds the delete feature for the button.
        deleteBtn.setOnAction(e -> {
            expensesVBox.getChildren().remove(container);
            addRowCounter--;
            if (addRowCounter < MAX_ROWS) {
                addRowBtn.setDisable(false);
            }
        });

        expensesVBox.getChildren().add(container);
        addRowCounter++;
        if (addRowCounter >= MAX_ROWS) {
            addRowBtn.setDisable(true);
        }
    }

    /**
     * Clears the date pickers' text input.
     * @param event the event
     */
    @FXML
    void clearRowBtn(final ActionEvent event) {
        final HBox firstRow = (HBox) expensesVBox.getChildren().get(0);
        final DatePicker datePicker1 = (DatePicker) firstRow.getChildren().get(1);
        final DatePicker datePicker2 = (DatePicker) firstRow.getChildren().get(2);
        final Button clearButton = (Button) firstRow.getChildren().get(3);
        clearDatePickers(datePicker1, datePicker2, clearButton);
    }

    /**
     * Deletes all the added rows.
     * @param event the event
     */
    @FXML
    void deleteAllRows(final ActionEvent event) {
        expensesVBox.getChildren().remove(1, expensesVBox.getChildrenUnmodifiable().size());
        addRowCounter = 1;
        addRowBtn.setDisable(false);
    }

    /**
     * Filters the employee table based on the content of the search field at each typed key.
     * @param keyEvent the event
     */
    @FXML
    void onEmployeeSearch(final KeyEvent keyEvent) {
        if (!keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            return;
        }
        if (employeeSearchField.getText().isBlank() || employeeSearchField.getText() == null) {
            // TODO: Put controller as field.
            final Collection<EmployeeTableItem> allItems = new EmployeeControllerImpl().getData();
            Platform.runLater(() -> {
                employeeTableView.getItems().clear();
                employeeTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<EmployeeTableItem> filtered = new EmployeeControllerImpl().filter(employeeSearchField.getText());
            Platform.runLater(() -> {
                employeeTableView.getItems().clear();
                employeeTableView.getItems().addAll(filtered);
            });
        }
    }

    /**
     * Opens the employee hiring popup screen.
     * @param event the event
     */
    @FXML
    void onEmployeeHire(final ActionEvent event) {
        EmployeeScreenController.setFromHire(true);
        EmployeeScreenController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/hire-employee-form.fxml", "Hire employee");
    }

    /**
     * Opens the edit employee popup screen.
     * @param event the event
     */
    @FXML
    void onEmployeeEdit(final ActionEvent event) {
        final EmployeeTableItem selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedEmployee)) {
            showAlertForUnselectedRowInTableView("employee");
            return;
        }
        EmployeeScreenController.setFromHire(false);
        EmployeeScreenController.setEditMode(true);
        EmployeeScreenController.setEmployee(selectedEmployee);
        LoadFXML.fromEventAsPopup(event, "layouts/hire-employee-form.fxml", "Edit employee");
    }

    /**
     * Removes from the employee table view the fired employee.
     * @param event the event
     */
    @FXML
    void onEmployeeFire(final ActionEvent event) {
        final EmployeeTableItem selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedEmployee)) {
            showAlertForUnselectedRowInTableView("employee");
            return;
        }
        Platform.runLater(() -> {
            employeeTableView.getItems().remove(new EmployeeControllerImpl().fire(selectedEmployee));
            // Refreshing contracts table view.
            contractsTableView.getItems().clear();
            contractsTableView.getItems().addAll(new ContractControllerImpl().getData());
        });
    }

    /**
     * Opens the fired employee history screen.
     * @param event the event
     */
    @FXML
    void onEmployeeHistory(final ActionEvent event) {
        LoadFXML.fromEventAsPopup(event, "layouts/employee-history.fxml", "Fired employees history");
    }

    /**
     * Filters the contracts table based on the content of the search field at each typed key.
     * @param keyEvent the event
     */
    @FXML
    void onContractSearch(final KeyEvent keyEvent) {
        if (!keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            return;
        }
        if (contractSearchField.getText().isBlank() || contractSearchField.getText() == null) {
            // TODO: Put controller as field.
            final Collection<ContractTableItem> allItems = new ContractControllerImpl().getData();
            Platform.runLater(() -> {
                contractsTableView.getItems().clear();
                contractsTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<ContractTableItem> filtered = new ContractControllerImpl().filter(contractSearchField.getText());
            Platform.runLater(() -> {
                contractsTableView.getItems().clear();
                contractsTableView.getItems().addAll(filtered);
            });
        }
    }

    /**
     * Opens the add contract popup screen.
     * @param event the event
     */
    @FXML
    void onContractAdd(final ActionEvent event) {
        ContractScreenController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/contract-form.fxml", "Add contract");
    }

    /**
     * Opens the edit contract popup screen.
     * @param event the event
     */
    @FXML
    void onContractEdit(final ActionEvent event) {
        final ContractTableItem selectedContract = contractsTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedContract)) {
            showAlertForUnselectedRowInTableView("contract");
            return;
        }
        ContractScreenController.setEditMode(true);
        ContractScreenController.setContract(selectedContract);
        LoadFXML.fromEventAsPopup(event, "layouts/contract-form.fxml", "Edit contract");
    }

    /**
     * Filters the table items by the search field content.
     * @param keyEvent the event
     */
    @FXML
    void onTicketSearch(final KeyEvent keyEvent) {
        if (!keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            return;
        }
        if (ticketSearchField.getText().isBlank() || ticketSearchField.getText() == null) {
            // TODO: Put controller as field.
            final Collection<TicketTableItem> allItems = new TicketControllerImpl().getData();
            Platform.runLater(() -> {
                ticketTableView.getItems().clear();
                ticketTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<TicketTableItem> filtered = new TicketControllerImpl().filter(ticketSearchField.getText());
            Platform.runLater(() -> {
                ticketTableView.getItems().clear();
                ticketTableView.getItems().addAll(filtered);
            });
        }
    }

    /**
     * Filters the tickets by their purchase date.
     * @param event the event
     */
    @FXML
    void onTicketPurchaseDateFilter(final ActionEvent event) {
        final String datePickerText = Objects.requireNonNull(ticketPurchaseDateFilter.getEditor().getText());
        if (datePickerText.isBlank()) {
            // TODO: Extract method.
            final Collection<TicketTableItem> allItems = new TicketControllerImpl().getData();
            Platform.runLater(() -> {
                ticketTableView.getItems().clear();
                ticketTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<TicketTableItem> filtered = new TicketControllerImpl()
                    .filterByPurchaseDate(ticketPurchaseDateFilter.getValue());
            Platform.runLater(() -> {
                ticketTableView.getItems().clear();
                ticketTableView.getItems().addAll(filtered);
                ticketPurchaseDateFilter.setTooltip(new Tooltip(TOOLTIP_STRING));
            });
        }
    }

    /**
     * Filters the tickets by their validation/punch date.
     * @param event the event
     */
    @FXML
    void onTicketPunchDateFilter(final ActionEvent event) {
        final String datePickerText = Objects.requireNonNull(ticketPunchDateFilter.getEditor().getText());
        if (datePickerText.isBlank()) {
            // TODO: Extract method.
            final Collection<TicketTableItem> allItems = new TicketControllerImpl().getData();
            Platform.runLater(() -> {
                ticketTableView.getItems().clear();
                ticketTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<TicketTableItem> filtered = new TicketControllerImpl()
                    .filterByPunchDate(ticketPunchDateFilter.getValue());
            Platform.runLater(() -> {
                ticketTableView.getItems().clear();
                ticketTableView.getItems().addAll(filtered);
                ticketPunchDateFilter.setTooltip(new Tooltip(TOOLTIP_STRING));
            });
        }
    }

    /**
     * Clears the selected ticket filter toggle button.
     * @param event the event
     */
    @FXML
    void onTicketFilterClear(final ActionEvent event) {
        final Toggle selectedToggle = radioBtnToggle.getSelectedToggle();
        if (Objects.nonNull(selectedToggle)) {
            selectedToggle.setSelected(false);
            ticketTableView.getItems().clear();
            Platform.runLater(() -> ticketTableView.getItems().addAll(new TicketControllerImpl().getData()));
        }
    }

    /**
     * Filters the tickets by the 'single day' type.
     * @param event the event
     */
    @FXML
    void onTicketSingleDayFilter(final ActionEvent event) {
        ticketTableView.getItems().clear();
        Platform.runLater(() -> ticketTableView.getItems().addAll(new TicketControllerImpl().filterBySingleDayTicket()));
    }

    /**
     * Filters the tickets by the 'season' type.
     * @param event the event
     */
    @FXML
    void onTicketSeasonFilter(final ActionEvent event) {
        ticketTableView.getItems().clear();
        Platform.runLater(() -> ticketTableView.getItems().addAll(new TicketControllerImpl().filterBySeasonTicket()));
    }

    /**
     * Opens the ticket selector popup.
     * @param event the event
     */
    @FXML
    void onAddTicketBtnPress(final ActionEvent event) {
        TicketSelectorScreenController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/ticket-selector.fxml", "Select an option");
    }

    /**
     * Opens the ticket selector popup.
     * @param event the event
     */
    @FXML
    void onEditTicketBtnPress(final ActionEvent event) {
        TicketSelectorScreenController.setEditMode(true);
        TicketSelectorScreenController.setTicket(ticketTableView.getSelectionModel().getSelectedItem());
        LoadFXML.fromEventAsPopup(event, "layouts/ticket-selector.fxml", "Select an option");
    }

    /**
     * Filters the attractions by their name.
     * @param keyEvent the event
     */
    @FXML
    void onAttractionSearch(final KeyEvent keyEvent) {
        if (!keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            return;
        }
        if (attractionNameSearchField.getText().isBlank() || attractionNameSearchField.getText() == null) {
            final Collection<AttractionTableItem> allItems = ridesRadioBtn.isSelected()
                    ? new RideControllerImpl().getData()
                    : new ExhibitionControllerImpl().getData();
            Platform.runLater(() -> {
                attractionsTableView.getItems().clear();
                attractionsTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<AttractionTableItem> filtered = ridesRadioBtn.isSelected()
                    ? new RideControllerImpl().filter(attractionNameSearchField.getText())
                    : new ExhibitionControllerImpl().filter(attractionNameSearchField.getText());
            Platform.runLater(() -> {
                attractionsTableView.getItems().clear();
                attractionsTableView.getItems().addAll(filtered);
            });
        }
    }

    /**
     * Adds the specific rides columns to the attractions table view.
     * @param event the event
     */
    @FXML
    void onRideBtnClick(final ActionEvent event) {
        attractionsTableView.getColumns().clear();
        final TableColumn<AttractionTableItem, String> id = new TableColumn<>("Ride ID");
        final TableColumn<AttractionTableItem, String> name = new TableColumn<>("Name");
        final TableColumn<AttractionTableItem, LocalTime> openingTime = new TableColumn<>("Opening");
        final TableColumn<AttractionTableItem, LocalTime> closingTime = new TableColumn<>("Closing");
        final TableColumn<AttractionTableItem, String> type = new TableColumn<>("Type");
        final TableColumn<AttractionTableItem, String> intensity = new TableColumn<>("Intensity");
        final TableColumn<AttractionTableItem, LocalTime> duration = new TableColumn<>("Duration");
        final TableColumn<AttractionTableItem, Integer> maxSeats = new TableColumn<>("Max seats");
        final TableColumn<AttractionTableItem, String> description = new TableColumn<>("Description");
        final TableColumn<AttractionTableItem, Integer> minHeight = new TableColumn<>("Min height");
        final TableColumn<AttractionTableItem, Integer> maxHeight = new TableColumn<>("Max height");
        final TableColumn<AttractionTableItem, Integer> minWeight = new TableColumn<>("Min weight");
        final TableColumn<AttractionTableItem, Integer> maxWeight = new TableColumn<>("Max weight");
        final TableColumn<AttractionTableItem, Character> status = new TableColumn<>("Status");
        final TableColumn<AttractionTableItem, Double> averageRating = new TableColumn<>("Average rating");
        final TableColumn<AttractionTableItem, Integer> numRating = new TableColumn<>("Ratings");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        openingTime.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
        closingTime.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        intensity.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        maxSeats.setCellValueFactory(new PropertyValueFactory<>("maxSeats"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        minHeight.setCellValueFactory(new PropertyValueFactory<>("minHeight"));
        maxHeight.setCellValueFactory(new PropertyValueFactory<>("maxHeight"));
        minWeight.setCellValueFactory(new PropertyValueFactory<>("minWeight"));
        maxWeight.setCellValueFactory(new PropertyValueFactory<>("maxWeight"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        averageRating.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        numRating.setCellValueFactory(new PropertyValueFactory<>("ratings"));
        final List<TableColumn<AttractionTableItem, ?>> columns = List.of(id,
                name,
                openingTime,
                closingTime,
                type,
                intensity,
                duration,
                maxSeats,
                description,
                minHeight,
                maxHeight,
                minWeight,
                maxWeight,
                status,
                averageRating,
                numRating);
        attractionsTableView.getColumns().addAll(columns);
        attractionsTableView.getItems().clear();
        Platform.runLater(() -> attractionsTableView.getItems().addAll(new RideControllerImpl().getData()));
        final ObservableList<Node> vboxChildren = ((VBox) exhibitionsRadioBtn.getParent()).getChildren();
        vboxChildren.remove(vboxChildren.indexOf(exhibitionsRadioBtn) + 1);
    }

    /**
     * Adds the specific exhibition columns to the attractions table view.
     * @param event the event
     */
    @FXML
    void onExhibitionBtnClick(final ActionEvent event) {
        attractionsTableView.getColumns().clear();
        final TableColumn<AttractionTableItem, String> id = new TableColumn<>("Exhibition ID");
        final TableColumn<AttractionTableItem, String> name = new TableColumn<>("Name");
        final TableColumn<AttractionTableItem, String> type = new TableColumn<>("Type");
        final TableColumn<AttractionTableItem, String> description = new TableColumn<>("Description");
        final TableColumn<AttractionTableItem, LocalDate> date = new TableColumn<>("Date");
        final TableColumn<AttractionTableItem, LocalTime> time = new TableColumn<>("Time");
        final TableColumn<AttractionTableItem, Integer> maxSeats = new TableColumn<>("Max seats");
        final TableColumn<AttractionTableItem, Integer> spectators = new TableColumn<>("Spectators");
        final TableColumn<AttractionTableItem, Double> averageRating = new TableColumn<>("Average rating");
        final TableColumn<AttractionTableItem, Integer> numRating = new TableColumn<>("Ratings");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        maxSeats.setCellValueFactory(new PropertyValueFactory<>("maxSeats"));
        spectators.setCellValueFactory(new PropertyValueFactory<>("spectators"));
        averageRating.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        numRating.setCellValueFactory(new PropertyValueFactory<>("ratings"));
        final List<TableColumn<AttractionTableItem, ?>> columns = List.of(id,
                name,
                type,
                description,
                date,
                time,
                maxSeats,
                spectators,
                averageRating,
                numRating);
        attractionsTableView.getColumns().addAll(columns);
        attractionsTableView.getItems().clear();
        final ExhibitionController exhibitionController = new ExhibitionControllerImpl();
        Platform.runLater(() -> attractionsTableView.getItems().addAll(exhibitionController.getData()));
        // Creating checkbox and adding an event handler.
        final CheckBox plannedExhibitionsCheckBox = new CheckBox("View planned");
        plannedExhibitionsCheckBox.setOnAction(action -> {
            attractionsTableView.getItems().clear();
            if (plannedExhibitionsCheckBox.isSelected()) {
                Platform.runLater(() -> attractionsTableView.getItems().addAll(exhibitionController.viewPlannedExhibitions()));
            } else {
                Platform.runLater(() -> attractionsTableView.getItems().addAll(exhibitionController.getData()));
            }
        });
        final ObservableList<Node> vboxChildren = ((VBox) exhibitionsRadioBtn.getParent()).getChildren();
        vboxChildren.add(vboxChildren.indexOf(exhibitionsRadioBtn) + 1, plannedExhibitionsCheckBox);
        advancedAttractionBtn.getItems().clear();
        final MenuItem planExhibitionItem = new MenuItem("Plan exhibition");
        planExhibitionItem.setOnAction(a -> {
            final ExhibitionTableItem selectedExhibition = (ExhibitionTableItem) attractionsTableView.getSelectionModel()
                    .getSelectedItem();
            if (selectedExhibition == null) {
                showAlertForUnselectedRowInTableView("exhibition");
                return;
            }
            if (selectedExhibition.getDate() != null
                    || selectedExhibition.getTime() != null
                    || selectedExhibition.getMaxSeats() != 0) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText("This exhibition cannot be planned.")
                        .show();
                return;
            }
            final GridPane gridPane = new GridPane();

            final DatePicker datePicker = new DatePicker();
            gridPane.setVgap(4);
            gridPane.setHgap(8);
            gridPane.addRow(0, new Label("Date:"), datePicker);
            final Spinner<Integer> timeHours = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
            timeHours.getValueFactory().setWrapAround(true);
            final Spinner<Integer> timeMinutes = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
            timeMinutes.getValueFactory().setWrapAround(true);
            final HBox hBox = new HBox(5, timeHours, new Label(":"), timeMinutes);
            hBox.setAlignment(Pos.CENTER_LEFT);
            gridPane.addRow(1, new Label("Time:"), hBox);
            final Spinner<Integer> seatsSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                    Integer.MAX_VALUE));
            gridPane.addRow(2, new Label("Max seats:"), seatsSpinner);
            new AlertBuilder(Alert.AlertType.CONFIRMATION)
                    .setHeaderText("Plan an exhibition")
                    .setContent(gridPane)
                    .setOnClose(node -> {
                        final LocalDate oldDate = selectedExhibition.getDate();
                        final LocalTime oldTime = selectedExhibition.getTime();
                        final int oldSeats = selectedExhibition.getMaxSeats();
                        selectedExhibition.setDate(datePicker.getValue());
                        selectedExhibition.setTime(LocalTime.of(timeHours.getValue(), timeMinutes.getValue()));
                        selectedExhibition.setMaxSeats(seatsSpinner.getValue());
                        try {
                            exhibitionController.planExhibition(selectedExhibition);
                        } catch (final DataAccessException e) {
                            selectedExhibition.setDate(oldDate);
                            selectedExhibition.setTime(oldTime);
                            selectedExhibition.setMaxSeats(oldSeats);
                            new AlertBuilder(Alert.AlertType.ERROR)
                                    .setContentText(e.getMessage())
                                    .show();
                            return;
                        }
                        attractionsTableView.getItems().set(attractionsTableView.getItems().indexOf(selectedExhibition),
                                selectedExhibition);
                    })
                    .show();
        });
        final MenuItem spectatorsItem = new MenuItem("Update spectators number");
        spectatorsItem.setOnAction(a -> {
            final ExhibitionTableItem selectedExhibition = (ExhibitionTableItem) attractionsTableView.getSelectionModel()
                    .getSelectedItem();
            if (selectedExhibition == null) {
                showAlertForUnselectedRowInTableView("exhibition");
                return;
            }
            final Spinner<Integer> spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                    999,
                    selectedExhibition.getSpectators()));
            spinner.setEditable(true);
            new AlertBuilder(Alert.AlertType.CONFIRMATION)
                    .setHeaderText("Update spectators amount")
                    .setContent(spinner)
                    .setOnClose(node -> {
                        final int oldValue = selectedExhibition.getSpectators();
                        selectedExhibition.setSpectators(spinner.getValue());
                        try {
                            exhibitionController.updateSpectatorsNumber(selectedExhibition);
                        } catch (final DataAccessException e) {
                            selectedExhibition.setSpectators(oldValue);
                            new AlertBuilder(Alert.AlertType.ERROR)
                                    .setContentText(e.getMessage())
                                    .show();
                            return;
                        }
                        attractionsTableView.getItems().set(attractionsTableView.getItems().indexOf(selectedExhibition),
                                selectedExhibition);
                    })
                    .show();
        });
        final MenuItem maxSeatsItem = new MenuItem("Update max seats number");
        maxSeatsItem.setOnAction(a -> {
            final ExhibitionTableItem selectedExhibition = (ExhibitionTableItem) attractionsTableView.getSelectionModel()
                    .getSelectedItem();
            if (selectedExhibition == null) {
                showAlertForUnselectedRowInTableView("exhibition");
                return;
            }
            final Spinner<Integer> spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                    999,
                    selectedExhibition.getMaxSeats()));
            spinner.setEditable(true);
            new AlertBuilder(Alert.AlertType.CONFIRMATION)
                    .setHeaderText("Update maximum seats")
                    .setContent(spinner)
                    .setOnClose(node -> {
                        final int oldValue = selectedExhibition.getMaxSeats();
                        selectedExhibition.setMaxSeats(spinner.getValue());
                        try {
                            exhibitionController.updateMaxSeats(selectedExhibition);
                        } catch (final DataAccessException e) {
                            selectedExhibition.setMaxSeats(oldValue);
                            new AlertBuilder(Alert.AlertType.ERROR)
                                    .setContentText(e.getMessage())
                                    .show();
                            return;
                        }
                        attractionsTableView.getItems().set(attractionsTableView.getItems().indexOf(selectedExhibition),
                                selectedExhibition);
                    })
                    .show();
        });
        final MenuItem averageSpectatorsItem = new MenuItem("Get average spectators by type");
        averageSpectatorsItem.setOnAction(a -> new AlertBuilder(Alert.AlertType.INFORMATION)
                .setHeaderText("Average spectators by exhibition type")
                .setContent(createAverageSpectatorsByTypeGridPane(exhibitionController.getAverageSpectatorsByType()))
                .show());
        final MenuItem soldOutPercentage = new MenuItem("Get sold-out exhibition percentage");
        soldOutPercentage.setOnAction(a -> new AlertBuilder(Alert.AlertType.INFORMATION)
                .setHeaderText("Sold-out exhibition percentage")
                .setContentText(exhibitionController.getSoldOutExhibitionPercentage() + "%")
                .show());
        advancedAttractionBtn.getItems().addAll(planExhibitionItem,
                spectatorsItem,
                maxSeatsItem,
                averageSpectatorsItem,
                soldOutPercentage);
    }

    /**
     * Opens the add attraction screen.
     * @param event the event
     */
    @FXML
    void onAddAttraction(final ActionEvent event) {
        if (ridesRadioBtn.isSelected()) {
            RideScreenController.setEditMode(false);
            LoadFXML.fromEventAsPopup(event, "layouts/ride-form.fxml", "Add ride");
        } else {
            ExhibitionScreenController.setEditMode(false);
            LoadFXML.fromEventAsPopup(event, "layouts/exhibition-form.fxml", "Add exhibition");
        }
    }

    /**
     * Opens the edit attraction screen.
     * @param event the event
     */
    @FXML
    void onEditAttraction(final ActionEvent event) {
        final AttractionTableItem selectedAttraction = attractionsTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedAttraction)) {
            showAlertForUnselectedRowInTableView("attraction");
            return;
        }
        if (ridesRadioBtn.isSelected()) {
            RideScreenController.setEditMode(true);
            RideScreenController.setRide((RideTableItem) selectedAttraction);
            LoadFXML.fromEventAsPopup(event, "layouts/ride-form.fxml", "Edit ride");
        } else {
            ExhibitionScreenController.setEditMode(true);
            ExhibitionScreenController.setExhibition((ExhibitionTableItem) selectedAttraction);
            LoadFXML.fromEventAsPopup(event, "layouts/exhibition-form.fxml", "Edit exhibition");
        }
    }

    /**
     * Filters the shop table based on the content of the search field at each typed key.
     * @param keyEvent the event
     */
    @FXML
    void onShopSearch(final KeyEvent keyEvent) {
        if (!keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            return;
        }
        if (shopSearchField.getText().isBlank() || shopSearchField.getText() == null) {
            // TODO: Put controller as field. Extract method.
            final Collection<ShopTableItem> allItems = new ShopControllerImpl().getData();
            Platform.runLater(() -> {
                shopsTableView.getItems().clear();
                shopsTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<ShopTableItem> filtered = new ShopControllerImpl().filter(shopSearchField.getText());
            Platform.runLater(() -> {
                shopsTableView.getItems().clear();
                shopsTableView.getItems().addAll(filtered);
            });
        }
    }

    /**
     * Opens the add-shop screen.
     * @param event the event
     */
    @FXML
    void onAddShop(final ActionEvent event) {
        ShopScreenController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/shop-form.fxml", "Add shop");
    }

    /**
     * Opens the edit shop screen.
     * @param event the event
     */
    @FXML
    void onEditShop(final ActionEvent event) {
        final var selectedShop = shopsTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedShop)) {
            showAlertForUnselectedRowInTableView("shop");
            return;
        }
        ShopScreenController.setEditMode(true);
        ShopScreenController.setShop(selectedShop);
        LoadFXML.fromEventAsPopup(event, "layouts/shop-form.fxml", "Edit shop");
    }

    /**
     * Filters the maintenance table based on the content of the search field at each typed key.
     * @param keyEvent the event
     */
    @FXML
    void onMaintenanceSearch(final KeyEvent keyEvent) {
        if (!keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            return;
        }
        if (maintenanceSearchField.getText().isBlank() || maintenanceSearchField.getText() == null) {
            // TODO: Extract method.
            final Collection<MaintenanceTableItem> allItems = MAINTENANCE_CONTROLLER.getData();
            Platform.runLater(() -> {
                maintenanceTableView.getItems().clear();
                maintenanceTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<MaintenanceTableItem> filtered = MAINTENANCE_CONTROLLER
                    .filter(maintenanceSearchField.getText());
            Platform.runLater(() -> {
                maintenanceTableView.getItems().clear();
                maintenanceTableView.getItems().addAll(filtered);
            });
        }
    }

    /**
     * Filters the maintenances based on the provided date in the date picker.
     * @param event the event
     */
    @FXML
    void onMaintenanceDateSearch(final ActionEvent event) {
        final String datePickerText = Objects.requireNonNull(maintenanceSearchDatePicker.getEditor().getText());
        if (datePickerText.isBlank()) {
            // TODO: Extract method.
            final Collection<MaintenanceTableItem> allItems = MAINTENANCE_CONTROLLER.getData();
            Platform.runLater(() -> {
                maintenanceTableView.getItems().clear();
                maintenanceTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<MaintenanceTableItem> filtered = MAINTENANCE_CONTROLLER
                    .filterByDate(maintenanceSearchDatePicker.getValue());
            Platform.runLater(() -> {
                maintenanceTableView.getItems().clear();
                maintenanceTableView.getItems().addAll(filtered);
                maintenanceSearchDatePicker.setTooltip(new Tooltip(TOOLTIP_STRING));
            });
        }
    }

    /**
     * Filters the maintenances to retrieve the rides related ones.
     * @param event the event
     */
    @FXML
    void onMaintenanceRideFilter(final ActionEvent event) {
        if (maintenanceRideFilter.isSelected() && !maintenanceShopFilter.isSelected()) {
            Platform.runLater(() -> maintenanceTableView.getItems().removeAll(MAINTENANCE_CONTROLLER.filterByShops()));
        } else if (!maintenanceRideFilter.isSelected() && maintenanceShopFilter.isSelected()) {
            Platform.runLater(() -> maintenanceTableView.getItems().removeAll(MAINTENANCE_CONTROLLER.filterByRides()));
        } else {
            maintenanceTableView.getItems().clear();
            Platform.runLater(() -> maintenanceTableView.getItems().addAll(MAINTENANCE_CONTROLLER.getData()));
        }
    }

    /**
     * Filters the maintenances to retrieve the shops related ones.
     * @param event the event
     */
    @FXML
    void onMaintenanceShopFilter(final ActionEvent event) {
        if (maintenanceShopFilter.isSelected() && !maintenanceRideFilter.isSelected()) {
            Platform.runLater(() -> maintenanceTableView.getItems().removeAll(MAINTENANCE_CONTROLLER.filterByRides()));
        } else if (!maintenanceShopFilter.isSelected() && maintenanceRideFilter.isSelected()) {
            Platform.runLater(() -> maintenanceTableView.getItems().removeAll(MAINTENANCE_CONTROLLER.filterByShops()));
        } else {
            maintenanceTableView.getItems().clear();
            Platform.runLater(() -> maintenanceTableView.getItems().addAll(MAINTENANCE_CONTROLLER.getData()));
        }
    }

    /**
     * Opens the add maintenance screen.
     * @param event the event
     */
    @FXML
    void onAddMaintenance(final ActionEvent event) {
        MaintenanceScreenController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/maintenance-form.fxml", "Add maintenance");
    }

    /**
     * Opens the edit maintenance screen.
     * @param event the event
     */
    @FXML
    void onEditMaintenance(final ActionEvent event) {
        final var selectedMaintenance = maintenanceTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedMaintenance)) {
            showAlertForUnselectedRowInTableView("maintenance");
            return;
        }
        MaintenanceScreenController.setEditMode(true);
        MaintenanceScreenController.setMaintenance(selectedMaintenance);
        LoadFXML.fromEventAsPopup(event, "layouts/maintenance-form.fxml", "Edit maintenance");
    }

    /**
     * Filters the picture table view by park service ID.
     * @param keyEvent the event
     */
    @FXML
    void onPictureSearch(final KeyEvent keyEvent) {
        if (!keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            return;
        }
        if (picturesSearchField.getText().isBlank() || picturesSearchField.getText() == null) {
            // TODO: make controller into field, extract method.
            final Collection<PictureTableItem> allItems = new PictureControllerImpl().getData();
            Platform.runLater(() -> {
                picturesTableView.getItems().clear();
                picturesTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<PictureTableItem> filtered = new PictureControllerImpl().filter(picturesSearchField.getText());
            Platform.runLater(() -> {
                picturesTableView.getItems().clear();
                picturesTableView.getItems().addAll(filtered);
            });
        }
    }

    /**
     * Opens the form to add a picture.
     * @param event the event
     */
    @FXML
    void onPictureAdd(final ActionEvent event) {
        PictureScreenController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/picture-form.fxml", "Add a picture");
    }

    /**
     * Opens the form to edit a picture.
     * @param event the event
     */
    @FXML
    void onPictureEdit(final ActionEvent event) {
        final PictureTableItem selectedPicture = picturesTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedPicture)) {
            showAlertForUnselectedRowInTableView("picture");
            return;
        }
        PictureScreenController.setEditMode(true);
        PictureScreenController.setPicture(selectedPicture);
        LoadFXML.fromEventAsPopup(event, "layouts/picture-form.fxml", "Edit picture");
    }

    /**
     * Removes a picture from the DB and GUI.
     * @param event the event
     */
    @FXML
    void onPictureRemove(final ActionEvent event) {
        final PictureTableItem selectedPicture = picturesTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedPicture)) {
            new AlertBuilder(Alert.AlertType.ERROR)
                    .setContentText("You need to select a row to remove.")
                    .show();
            return;
        }
        Platform.runLater(() -> {
            try {
                picturesTableView.getItems().remove(new PictureControllerImpl().removePicture(selectedPicture));
            } catch (final DataAccessException e) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText(e.getMessage())
                        .show();
            }
        });
    }

    /**
     * Filters the reviews by the park service id.
     * @param keyEvent the event
     */
    @FXML
    void onReviewSearch(final KeyEvent keyEvent) {
        if (!keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            return;
        }
        if (reviewSearchField.getText().isBlank() || reviewSearchField.getText() == null) {
            // TODO: make controller into field, extract method.
            final Collection<ReviewTableItem> allItems = new ReviewControllerImpl().getData();
            Platform.runLater(() -> {
                reviewsTableView.getItems().clear();
                reviewsTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<ReviewTableItem> filtered = new ReviewControllerImpl().filter(reviewSearchField.getText());
            Platform.runLater(() -> {
                reviewsTableView.getItems().clear();
                reviewsTableView.getItems().addAll(filtered);
            });
        }
    }

    /**
     * Filters the review tableview by date.
     * @param event the event
     */
    @FXML
    void onReviewDateSearch(final ActionEvent event) {
        final String datePickerText = Objects.requireNonNull(reviewDateFilter.getEditor().getText());
        if (datePickerText.isBlank()) {
            // TODO: Extract method.
            final Collection<ReviewTableItem> allItems = new ReviewControllerImpl().getData();
            Platform.runLater(() -> {
                reviewsTableView.getItems().clear();
                reviewsTableView.getItems().addAll(allItems);
            });
        } else {
            final Collection<ReviewTableItem> filtered = new ReviewControllerImpl()
                    .filterByDate(reviewDateFilter.getValue());
            Platform.runLater(() -> {
                reviewsTableView.getItems().clear();
                reviewsTableView.getItems().addAll(filtered);
                reviewDateFilter.setTooltip(new Tooltip(TOOLTIP_STRING));
            });
        }
    }

    /**
     * Switches between ranged and exclusive mode for the review slider.
     * @param event the event
     */
    @FXML
    void onReviewRanged(final ActionEvent event) {
        final ObservableList<ReviewTableItem> tableItems = reviewsTableView.getItems();
        final ReviewController controller = new ReviewControllerImpl();
        tableItems.clear();
        if (rangedCheckBox.isSelected()) {
            tableItems.addAll(controller.filterByRatingRange(Math.toIntExact(Math.round(ratingFilterSlider.getValue()))));
        } else {
            tableItems.addAll(controller.filterByRating(Math.toIntExact(Math.round(ratingFilterSlider.getValue()))));
        }
    }

    /**
     * Filters the reviews based on service type.
     * @param event the event
     */
    @FXML
    void onReviewServiceFilter(final ActionEvent event) {
        final RadioButton selected = (RadioButton) reviewFilterToggle.getSelectedToggle();
        final ObservableList<ReviewTableItem> tableItems = reviewsTableView.getItems();
        final ReviewController controller = new ReviewControllerImpl();
        final Consumer<String> handleFiltering = label -> {
            tableItems.clear();
            switch (label.toLowerCase(Locale.getDefault())) {
                case "rides" -> tableItems.addAll(controller.filterByRide());
                case "exhibitions" -> tableItems.addAll(controller.filterByExhibition());
                case "shops" -> tableItems.addAll(controller.filterByShop());
                default -> throw new IllegalStateException("Unknown label: " + label);
            }
        };
        handleFiltering.accept(selected.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // Overview tab init.
        final OverviewController overviewController = new OverviewControllerImpl();
        parkNameField.setText(overviewController.getParkName());
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        openingTimeField.setText(overviewController.getOpeningTime().format(formatter));
        closingTimeField.setText(overviewController.getClosingTime().format(formatter));
        adminField.setText(overviewController.getAdministrator());
        attractionsNumField.setText(String.valueOf(overviewController.getAttractionsAmount()));
        shopsNumField.setText(String.valueOf(overviewController.getShopsAmount()));
        employeesNumField.setText(String.valueOf(overviewController.getEmployeesAmount()));
        // Adding monthly report.
        if (LocalDate.now().getDayOfMonth() == 1) {
            try {
                new ExpensesControllerImpl().addRecapForPreviousMonth();
            } catch (final IllegalStateException e) {
                new AlertBuilder(Alert.AlertType.INFORMATION)
                        .setContentText(e.getMessage())
                        .show();
            }
        }
        // Populating X axis with months.
        ((CategoryAxis) chart.getXAxis()).setCategories(FXCollections.observableList(IntStream.rangeClosed(1, 12)
                .mapToObj(Month::of)
                .map(month -> month.getDisplayName(TextStyle.SHORT, Locale.ROOT))
                .toList()));
        List.of(expensesDatePicker1, expensesDatePicker2)
                .forEach(datePicker -> datePicker.setDayCellFactory(param -> new JavaFXUtils.FirstDayDateCell()));
        /*
         * Disables or enables the delete all rows button
         * based on the presence of at least one new row.
         */
        expensesVBox.getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                deleteAllRowsBtn.setDisable(!change.wasAdded());
            }
        });
        // Sets the default first row.
        final HBox firstRow = (HBox) expensesVBox.getChildren().get(0);
        final DatePicker datePicker1 = (DatePicker) firstRow.getChildren().get(1);
        final DatePicker datePicker2 = (DatePicker) firstRow.getChildren().get(2);
        final Button clearButton = (Button) firstRow.getChildren().get(3);
        clearButton.setDisable(true);
        addListenersToDatePicker(datePicker1, datePicker2, clearButton);
        // Loading the ride tableview by default.
        onRideBtnClick(null);
        // Populating the table views.
        EmployeeScreenController.setTableView(employeeTableView);
        employeeTableView.getItems().addAll(new EmployeeControllerImpl().getData());
        EmployeeControllerImpl.setContractTableView(contractsTableView);

        ticketTableView.getItems().addAll(new TicketControllerImpl().getData());
        TicketScreenController.setTableView(ticketTableView);
        ticketPurchaseDateFilter.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
                ticketPurchaseDateFilter.getEditor().clear();
                ticketPurchaseDateFilter.setTooltip(null);
                ticketPurchaseDateFilter.setValue(null);
                ticketTableView.getItems().clear();
                ticketTableView.getItems().addAll(new TicketControllerImpl().getData());
            }
        });
        ticketPunchDateFilter.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
                ticketPunchDateFilter.getEditor().clear();
                ticketPunchDateFilter.setTooltip(null);
                ticketPunchDateFilter.setValue(null);
                ticketTableView.getItems().clear();
                ticketTableView.getItems().addAll(new TicketControllerImpl().getData());
            }
        });

        contractsTableView.getItems().addAll(new ContractControllerImpl().getData());
        ContractScreenController.setContractTableView(contractsTableView);
        ContractScreenController.setEmployeeTableView(employeeTableView);

        attractionsTableView.getItems().addAll(new RideControllerImpl().getData());
        RideScreenController.setTableView(attractionsTableView);
        ExhibitionScreenController.setTableView(attractionsTableView);

        shopsTableView.getItems().addAll(new ShopControllerImpl().getData());
        ShopScreenController.setTableView(shopsTableView);

        // Clears the maintenance search date picker field.
        maintenanceSearchDatePicker.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
                maintenanceSearchDatePicker.getEditor().clear();
                maintenanceSearchDatePicker.setTooltip(null);
                maintenanceSearchDatePicker.setValue(null);
                maintenanceTableView.getItems().clear();
                maintenanceTableView.getItems().addAll(MAINTENANCE_CONTROLLER.getData());
            }
        });
        maintenanceTableView.getItems().addAll(MAINTENANCE_CONTROLLER.getData());
        MaintenanceScreenController.setTableView(maintenanceTableView);

        picturesTableView.getItems().addAll(new PictureControllerImpl().getData());
        PictureScreenController.setTableView(picturesTableView);

        // Clears the review search date picker field.
        reviewDateFilter.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
                reviewDateFilter.getEditor().clear();
                reviewDateFilter.setTooltip(null);
                reviewDateFilter.setValue(null);
                reviewsTableView.getItems().clear();
                reviewsTableView.getItems().addAll(new ReviewControllerImpl().getData());
            }
        });
        ratingFilterSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Disabling other filters.
            reviewSearchField.setText(null);
            reviewRideFilter.setSelected(false);
            reviewExhibitionFilter.setSelected(false);
            reviewShopFilter.setSelected(false);
            // Makes the slider move in discrete steps.
            ratingFilterSlider.setValue(newValue.intValue());
            if (oldValue.intValue() != newValue.intValue()) {
                final ObservableList<ReviewTableItem> tableItems = reviewsTableView.getItems();
                final ReviewController controller = new ReviewControllerImpl();
                tableItems.clear();
                if (rangedCheckBox.isSelected()) {
                    tableItems.addAll(controller.filterByRatingRange(newValue.intValue()));
                } else {
                    tableItems.addAll(controller.filterByRating(newValue.intValue()));
                }
            }
        });
        reviewFilterToggle.getToggles().forEach(toggle -> ((RadioButton) toggle).setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE) || keyEvent.getCode().equals(KeyCode.DELETE)) {
                toggle.setSelected(false);
                reviewsTableView.getItems().clear();
                reviewsTableView.getItems().addAll(new ReviewControllerImpl().getData());
            }
        }));
        reviewsTableView.getItems().addAll(new ReviewControllerImpl().getData());
    }

    private static void addListenersToDatePicker(final DatePicker datePicker1,
                                                 final DatePicker datePicker2,
                                                 final Button clearButton) {
        datePicker1.valueProperty().addListener(observable -> clearButton.setDisable(false));
        datePicker2.valueProperty().addListener(observable -> clearButton.setDisable(false));
        datePicker1.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                clearButton.setDisable(false);
            }
        });
        datePicker2.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                clearButton.setDisable(false);
            }
        });
    }

    private static void clearDatePickers(final DatePicker datePicker1,
                                         final DatePicker datePicker2,
                                         final Button clearButton) {
        datePicker1.getEditor().setText("");
        datePicker2.getEditor().setText("");
        clearButton.setDisable(true);
    }

    private GridPane createAverageSpectatorsByTypeGridPane(final Collection<Pair<String, Integer>> typeSpectatorsPairs) {
        final GridPane gridPane = new GridPane();
        final List<Pair<String, Integer>> pairs = typeSpectatorsPairs.stream()
                .sorted(Comparator.comparing(Pair::getLeft))
                .toList();
        for (int i = 0; i < pairs.size(); i++) {
            final Label typeLabel = new Label(pairs.get(i).getLeft() + ":");
            typeLabel.setStyle("-fx-font-weight: bold");
            gridPane.addRow(i, typeLabel, new Label(pairs.get(i).getRight().toString()));
        }
        gridPane.setVgap(2);
        gridPane.setHgap(8);
        return gridPane;
    }

    /**
     * Shows a popup alert when it is attempted to edit a row without selecting one first.
     * @param rowName the row name to display in the alert
     */
    protected static void showAlertForUnselectedRowInTableView(final String rowName) {
        new AlertBuilder(Alert.AlertType.WARNING)
                .setHeaderText("Whoops, something went wrong...")
                .setContentText("A row must be selected to edit the " + rowName + " entry!")
                .show();
    }

}
