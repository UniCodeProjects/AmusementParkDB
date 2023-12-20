package org.apdb4j.view;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.tableview.AttractionTableView;
import org.apdb4j.view.tableview.EmployeeTableView;
import org.apdb4j.view.tableview.ExhibitionTableView;
import org.apdb4j.view.tableview.RideTableView;
import org.apdb4j.view.tableview.ShopTableView;
import org.apdb4j.view.tableview.TicketTableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The FXML controller for the staff UI.
 */
public class StaffScreenController implements Initializable {

    @FXML
    private Button addRowBtn;
    @FXML
    private Button deleteAllRowsBtn;
    @FXML
    private LineChart<?, ?> chart;
    @FXML
    private Button clearBtn;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ToggleGroup radioBtnToggle;
    @FXML
    private VBox vBox;
    @FXML
    private TableView<EmployeeTableView> employeeTableView;
    @FXML
    private TableView<TicketTableView> ticketTableView;
    @FXML
    private TableView<AttractionTableView> attractionsTableView;
    @FXML
    private TableView<ShopTableView> shopsTableView;
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
     * Clears the selected toggle button.
     * @param event the event
     */
    @FXML
    void onClearBtn(final ActionEvent event) {
        final Toggle selectedToggle = radioBtnToggle.getSelectedToggle();
        if (Objects.nonNull(selectedToggle)) {
            selectedToggle.setSelected(false);
        }
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
        datePicker2.setPromptText("End date");
        datePicker2.setMaxWidth(Double.MAX_VALUE);

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
            vBox.getChildren().remove(container);
            addRowCounter--;
            if (addRowCounter < MAX_ROWS) {
                addRowBtn.setDisable(false);
            }
        });

        vBox.getChildren().add(container);
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
        final HBox firstRow = (HBox) vBox.getChildren().get(0);
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
        vBox.getChildren().remove(1, vBox.getChildrenUnmodifiable().size());
        addRowCounter = 1;
        addRowBtn.setDisable(false);
    }

    /**
     * Opens the employee hiring popup screen.
     * @param event the event
     */
    @FXML
    void onEmployeeHire(final ActionEvent event) {
        HireController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/hire-employee-screen.fxml", "Hire employee", 0.4, 0.6);
    }

    /**
     * Opens the edit employee popup screen.
     * @param event the event
     */
    @FXML
    void onEmployeeEdit(final ActionEvent event) {
        final EmployeeTableView selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedEmployee)) {
            showAlertForUnselectedRowInTableView("employee");
            return;
        }
        HireController.setEditMode(true);
        HireController.setEmployee(selectedEmployee);
        LoadFXML.fromEventAsPopup(event, "layouts/hire-employee-screen.fxml", "Edit employee", 0.4, 0.6);
    }

    /**
     * Opens the ticket selector popup.
     * @param event the event
     */
    @FXML
    void onAddTicketBtnPress(final ActionEvent event) {
        TicketSelectorController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/ticket-selector.fxml", "Select an option");
    }

    /**
     * Opens the ticket selector popup.
     * @param event the event
     */
    @FXML
    void onEditTicketBtnPress(final ActionEvent event) {
        TicketSelectorController.setEditMode(true);
        TicketSelectorController.setTicket(ticketTableView.getSelectionModel().getSelectedItem());
        LoadFXML.fromEventAsPopup(event, "layouts/ticket-selector.fxml", "Select an option");
    }

    /**
     * Adds the specific rides columns to the attractions table view.
     * @param event the event
     */
    @FXML
    void onRideBtnClick(final ActionEvent event) {
        attractionsTableView.getColumns().clear();
        final TableColumn<AttractionTableView, String> id = new TableColumn<>("Ride ID");
        final TableColumn<AttractionTableView, String> name = new TableColumn<>("Name");
        final TableColumn<AttractionTableView, LocalTime> openingTime = new TableColumn<>("Opening");
        final TableColumn<AttractionTableView, LocalTime> closingTime = new TableColumn<>("Closing");
        final TableColumn<AttractionTableView, String> type = new TableColumn<>("Type");
        final TableColumn<AttractionTableView, String> intensity = new TableColumn<>("Intensity");
        final TableColumn<AttractionTableView, LocalTime> duration = new TableColumn<>("Duration");
        final TableColumn<AttractionTableView, Integer> maxSeats = new TableColumn<>("Max seats");
        final TableColumn<AttractionTableView, String> description = new TableColumn<>("Description");
        final TableColumn<AttractionTableView, Integer> minHeight = new TableColumn<>("Min height");
        final TableColumn<AttractionTableView, Integer> maxHeight = new TableColumn<>("Max height");
        final TableColumn<AttractionTableView, Integer> minWeight = new TableColumn<>("Min weight");
        final TableColumn<AttractionTableView, Integer> maxWeight = new TableColumn<>("Max weight");
        final TableColumn<AttractionTableView, Character> status = new TableColumn<>("Status");
        final TableColumn<AttractionTableView, Double> averageRating = new TableColumn<>("Average rating");
        final TableColumn<AttractionTableView, Integer> numRating = new TableColumn<>("Ratings");
        final List<TableColumn<AttractionTableView, ?>> columns = List.of(id,
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
    }

    /**
     * Adds the specific exhibition columns to the attractions table view.
     * @param event the event
     */
    @FXML
    void onExhibitionBtnClick(final ActionEvent event) {
        attractionsTableView.getColumns().clear();
        final TableColumn<AttractionTableView, String> id = new TableColumn<>("Exhibition ID");
        final TableColumn<AttractionTableView, String> name = new TableColumn<>("Name");
        final TableColumn<AttractionTableView, String> type = new TableColumn<>("Type");
        final TableColumn<AttractionTableView, String> description = new TableColumn<>("Description");
        final TableColumn<AttractionTableView, LocalDate> date = new TableColumn<>("Date");
        final TableColumn<AttractionTableView, LocalTime> time = new TableColumn<>("Time");
        final TableColumn<AttractionTableView, Integer> maxSeats = new TableColumn<>("Max seats");
        final TableColumn<AttractionTableView, Integer> spectators = new TableColumn<>("Spectators");
        final TableColumn<AttractionTableView, Double> averageRating = new TableColumn<>("Average rating");
        final TableColumn<AttractionTableView, Integer> numRating = new TableColumn<>("Ratings");
        final List<TableColumn<AttractionTableView, ?>> columns = List.of(id,
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
    }

    /**
     * Opens the add attraction screen.
     * @param event the event
     */
    @FXML
    void onAddAttraction(final ActionEvent event) {
        if (ridesRadioBtn.isSelected()) {
            AddRideController.setEditMode(false);
            LoadFXML.fromEventAsPopup(event, "layouts/add-ride.fxml", "Add ride", 0.4, 0.6);
        } else {
            AddExhibitionController.setEditMode(false);
            LoadFXML.fromEventAsPopup(event, "layouts/add-exhibition.fxml", "Add exhibition", 0.4, 0.5);
        }
    }

    /**
     * Opens the edit attraction screen.
     * @param event the event
     */
    @FXML
    void onEditAttraction(final ActionEvent event) {
        final AttractionTableView selectedAttraction = attractionsTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedAttraction)) {
            showAlertForUnselectedRowInTableView("attraction");
            return;
        }
        if (ridesRadioBtn.isSelected()) {
            AddRideController.setEditMode(true);
            AddRideController.setRide((RideTableView) selectedAttraction);
            LoadFXML.fromEventAsPopup(event, "layouts/add-ride.fxml", "Edit ride", 0.4, 0.6);
        } else {
            AddExhibitionController.setEditMode(true);
            AddExhibitionController.setExhibition((ExhibitionTableView) selectedAttraction);
            LoadFXML.fromEventAsPopup(event, "layouts/add-exhibition.fxml", "Edit exhibition", 0.4, 0.5);
        }
    }

    /**
     * Opens the add-shop screen.
     * @param event the event
     */
    @FXML
    void onAddShop(final ActionEvent event) {
        AddShopController.setEditMode(false);
        LoadFXML.fromEventAsPopup(event, "layouts/add-shop.fxml", "Add shop", 0.4, 0.5);
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
        AddShopController.setEditMode(true);
        AddShopController.setShop(selectedShop);
        LoadFXML.fromEventAsPopup(event, "layouts/add-shop.fxml", "Edit shop", 0.4, 0.5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        /*
         * Disables or enables the delete all rows button
         * based on the presence of at least one new row.
         */
        vBox.getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                deleteAllRowsBtn.setDisable(!change.wasAdded());
            }
        });
        // Sets the default first row.
        final HBox firstRow = (HBox) vBox.getChildren().get(0);
        final DatePicker datePicker1 = (DatePicker) firstRow.getChildren().get(1);
        final DatePicker datePicker2 = (DatePicker) firstRow.getChildren().get(2);
        final Button clearButton = (Button) firstRow.getChildren().get(3);
        clearButton.setDisable(true);
        addListenersToDatePicker(datePicker1, datePicker2, clearButton);
        // Loading the ride tableview by default.
        onRideBtnClick(null);
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

    /**
     * Shows a popup alert when it is attempted to edit a row without selecting one first.
     * @param rowName the row name to display in the alert
     */
    protected static void showAlertForUnselectedRowInTableView(final String rowName) {
        final var alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("An error has occurred.");
        alert.setContentText("A row must be selected to edit the " + rowName + " entry!");
        alert.show();
    }

}
