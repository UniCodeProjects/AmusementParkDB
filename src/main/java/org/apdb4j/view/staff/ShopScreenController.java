package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.ShopController;
import org.apdb4j.controllers.ShopControllerImpl;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.ShopTableItem;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The FXML controller for the shop screen.
 */
public class ShopScreenController extends PopupInitializer {

    private static final ShopController CONTROLLER = new ShopControllerImpl();
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> openingHourSpinner;
    @FXML
    private Spinner<Integer> openingMinuteSpinner;
    @FXML
    private Spinner<Integer> closingHourSpinner;
    @FXML
    private Spinner<Integer> closingMinuteSpinner;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Spinner<Double> expensesSpinner;
    @FXML
    private Spinner<Double> revenueSpinner;
    @FXML
    private ChoiceBox<Month> monthChoiceBox;
    @FXML
    private Spinner<Integer> yearSpinner;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static ShopTableItem shop;
    @Setter
    private static TableView<ShopTableItem> tableView;

    /**
     * Default constructor.
     */
    public ShopScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * Adds the new shop in the DB and adds the new row in the tableview.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        final ShopTableItem shopItem = new ShopTableItem(editMode ? shop.getId() : "SH-00",    // TODO: use id generator.
                nameField.getText(),
                LocalTime.of(openingHourSpinner.getValue(), openingMinuteSpinner.getValue()),
                LocalTime.of(closingHourSpinner.getValue(), closingMinuteSpinner.getValue()),
                typeComboBox.getValue(),
                descriptionTextArea.getText(),
                expensesSpinner.getValue(),
                revenueSpinner.getValue(),
                YearMonth.of(yearSpinner.getValue(), monthChoiceBox.getValue()));
        gridPane.getScene().getWindow().hide();
        if (!editMode) {
            Platform.runLater(() -> {
                try {
                    tableView.getItems().add(CONTROLLER.addData(shopItem));
                } catch (final SQLException e) {
                    final var alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("An error has occurred.");
                    alert.setContentText(CONTROLLER.getErrorMessage().orElse(""));
                    alert.show();
                }
            });
        } else {
            Platform.runLater(() -> {
                final int selectedIndex = tableView.getItems().indexOf(shop);
                CONTROLLER.editData(shopItem);
                final List<ShopTableItem> allShopEntries = tableView.getItems().stream()
                        .filter(shopTableItem -> shopTableItem.getId().equals(shop.getId()))
                        .toList();
                tableView.getItems().removeAll(allShopEntries);
                tableView.getItems().addAll(selectedIndex, CONTROLLER.getData(shop.getId()));
                // Re-sorting the tableview by the shop id column.
                tableView.getSortOrder().add(tableView.getColumns().get(0));
                tableView.sort();
                tableView.getSelectionModel().select(selectedIndex);
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        IntStream.rangeClosed(1, 12).forEach(month -> monthChoiceBox.getItems().add(Month.of(month)));
        typeComboBox.getItems().addAll(CONTROLLER.getExistingTypes());
        if (!editMode) {
            monthChoiceBox.setValue(YearMonth.now().getMonth());
            yearSpinner.getValueFactory().setValue(Year.now().getValue());
            return;
        }
        nameField.setText(shop.getName());
        openingHourSpinner.getValueFactory().setValue(shop.getOpeningTime().getHour());
        openingMinuteSpinner.getValueFactory().setValue(shop.getOpeningTime().getMinute());
        closingHourSpinner.getValueFactory().setValue(shop.getClosingTime().getHour());
        closingMinuteSpinner.getValueFactory().setValue(shop.getClosingTime().getMinute());
        typeComboBox.setValue(shop.getType());
        descriptionTextArea.setText(shop.getDescription());
        expensesSpinner.getValueFactory().setValue(shop.getExpenses());
        revenueSpinner.getValueFactory().setValue(shop.getRevenue());
        monthChoiceBox.setValue(shop.getYearMonth().getMonth());
        monthChoiceBox.setDisable(true);
        yearSpinner.getValueFactory().setValue(shop.getYearMonth().getYear());
        yearSpinner.setDisable(true);
    }

}
