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
import org.apache.commons.lang3.ObjectUtils;
import org.apdb4j.controllers.staff.ShopController;
import org.apdb4j.controllers.staff.ShopControllerImpl;
import org.apdb4j.util.IDGenerationUtils;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.view.FXMLController;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.ShopTableItem;
import org.jooq.exception.DataAccessException;

import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.IntStream;

import static org.apdb4j.db.Tables.COSTS;

/**
 * The FXML controller for the shop screen.
 */
public class ShopScreenController extends PopupInitializer implements FXMLController {

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
    private static boolean addCostMode;
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
        final ShopTableItem shopItem = new ShopTableItem(
                editMode || addCostMode ? shop.getId() : IDGenerationUtils.generateShopID(),
                nameField.getText(),
                LocalTime.of(openingHourSpinner.getValue(), openingMinuteSpinner.getValue()),
                LocalTime.of(closingHourSpinner.getValue(), closingMinuteSpinner.getValue()),
                typeComboBox.getValue(),
                descriptionTextArea.getText(),
                editMode || addCostMode ? expensesSpinner.getValue() : null,
                editMode || addCostMode ? revenueSpinner.getValue() : null,
                editMode || addCostMode ? YearMonth.of(yearSpinner.getValue(), monthChoiceBox.getValue()) : null);
        gridPane.getScene().getWindow().hide();
        final int selectedIndex = tableView.getItems().indexOf(shop);
        if (editMode) {
            try {
                CONTROLLER.editData(shopItem);
            } catch (final DataAccessException e) {
                new AlertBuilder(Alert.AlertType.ERROR)
                        .setContentText(e.getMessage())
                        .show();
                return;
            }
            Platform.runLater(() -> {
                tableView.getItems().set(selectedIndex, shopItem);
                tableView.getSelectionModel().select(selectedIndex);
            });
        } else if (addCostMode) {
            Platform.runLater(() -> {
                try {
                    if (costTableIsPresent(shopItem.getId())) {
                        tableView.getItems().add(CONTROLLER.addMonthlyCost(shopItem));
                    } else {
                        tableView.getItems().set(selectedIndex, CONTROLLER.addMonthlyCost(shopItem));
                    }
                } catch (final DataAccessException e) {
                    new AlertBuilder(Alert.AlertType.ERROR)
                            .setContentText(e.getMessage())
                            .show();
                }
            });
        } else {
            Platform.runLater(() -> {
                try {
                    tableView.getItems().add(CONTROLLER.addData(shopItem));
                } catch (final DataAccessException e) {
                    new AlertBuilder(Alert.AlertType.ERROR)
                            .setContentText(e.getCause().getMessage())
                            .show();
                }
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
        if (!editMode && !addCostMode) {
            List.of(expensesSpinner, revenueSpinner, monthChoiceBox, yearSpinner).forEach(c -> c.setDisable(true));
            return;
        }
        if (addCostMode && !editMode) {
            List.of(nameField,
                    openingHourSpinner,
                    openingMinuteSpinner,
                    closingHourSpinner,
                    closingMinuteSpinner,
                    typeComboBox,
                    descriptionTextArea).forEach(c -> c.setDisable(true));
        }
        if (editMode) {
            monthChoiceBox.setDisable(true);
            yearSpinner.setDisable(true);
        }
        nameField.setText(shop.getName());
        openingHourSpinner.getValueFactory().setValue(shop.getOpeningTime().getHour());
        openingMinuteSpinner.getValueFactory().setValue(shop.getOpeningTime().getMinute());
        closingHourSpinner.getValueFactory().setValue(shop.getClosingTime().getHour());
        closingMinuteSpinner.getValueFactory().setValue(shop.getClosingTime().getMinute());
        typeComboBox.setValue(shop.getType());
        descriptionTextArea.setText(shop.getDescription());
        expensesSpinner.getValueFactory().setValue(ObjectUtils.defaultIfNull(shop.getExpenses(), 0.0));
        revenueSpinner.getValueFactory().setValue(ObjectUtils.defaultIfNull(shop.getRevenue(), 0.0));
        monthChoiceBox.setValue(ObjectUtils.defaultIfNull(shop.getYearMonth(), YearMonth.now()).getMonth());
        yearSpinner.getValueFactory().setValue(ObjectUtils.defaultIfNull(shop.getYearMonth(), YearMonth.now()).getYear());
    }

    private boolean costTableIsPresent(final String shopId) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.selectCount()
                        .from(COSTS)
                        .where(COSTS.SHOPID.eq(shopId))
                        .fetchSingleInto(int.class))
                .closeConnection()
                .getResultAsInt() >= 1;
    }

}
