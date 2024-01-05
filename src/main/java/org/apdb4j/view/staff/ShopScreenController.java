package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.controllers.ShopControllerImpl;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.ShopTableItem;

import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * The FXML controller for the shop screen.
 */
public class ShopScreenController extends PopupInitializer {

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
                monthChoiceBox.getValue());
        gridPane.getScene().getWindow().hide();
        if (!editMode) {
            Platform.runLater(() -> tableView.getItems().add(new ShopControllerImpl().addData(shopItem)));
        } else {
            Platform.runLater(() -> {
                final int selectedIndex = tableView.getItems().indexOf(shop);
                tableView.getItems().remove(shop);
                tableView.getItems().add(selectedIndex, new ShopControllerImpl().editData(shopItem));
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        IntStream.rangeClosed(1, 12).forEach(month -> monthChoiceBox.getItems().add(Month.of(month)));
        typeComboBox.getItems().addAll(getExistingTypes());
        monthChoiceBox.setValue(YearMonth.now().getMonth());
        if (!editMode) {
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
        monthChoiceBox.setValue(shop.getMonth());
    }

    private List<String> getExistingTypes() {
        return Arrays.stream(new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                        .from(PARK_SERVICES)
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .sortAsc(PARK_SERVICES.TYPE)
                .intoArray(PARK_SERVICES.TYPE))
                .toList();
    }

}
