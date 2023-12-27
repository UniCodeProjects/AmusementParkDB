package org.apdb4j.view.staff;

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
import org.apdb4j.controllers.EmployeeControllerImpl;
import org.apdb4j.util.view.LoadFXML;
import org.apdb4j.view.staff.tableview.AttractionTableItem;
import org.apdb4j.view.staff.tableview.ContractTableItem;
import org.apdb4j.view.staff.tableview.EmployeeTableItem;
import org.apdb4j.view.staff.tableview.ExhibitionTableItem;
import org.apdb4j.view.staff.tableview.MaintenanceTableItem;
import org.apdb4j.view.staff.tableview.ReviewTableItem;
import org.apdb4j.view.staff.tableview.RideTableItem;
import org.apdb4j.view.staff.tableview.ShopTableItem;
import org.apdb4j.view.staff.tableview.TicketTableItem;

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
    private TableView<EmployeeTableItem> employeeTableView;
    @FXML
    private TableView<ContractTableItem> contractsTableView;
    @FXML
    private TableView<TicketTableItem> ticketTableView;
    @FXML
    private TableView<AttractionTableItem> attractionsTableView;
    @FXML
    private TableView<ShopTableItem> shopsTableView;
    @FXML
    private TableView<MaintenanceTableItem> maintenanceTableView;
    @FXML
    private TableView<ReviewTableItem> reviewsTableView;
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
        // Populating the table views.
        EmployeeScreenController.setTableView(employeeTableView);
        employeeTableView.getItems().addAll(new EmployeeControllerImpl().getData());
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
