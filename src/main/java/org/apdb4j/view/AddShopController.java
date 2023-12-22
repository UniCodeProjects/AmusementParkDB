package org.apdb4j.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import org.apdb4j.view.tableview.ShopTableView;

import java.time.format.TextStyle;
import java.util.Locale;

/**
 * The FXML controller for the add-shop screen.
 */
public class AddShopController extends PopupInitializer implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nameField;
    @FXML
    private TextField openingHourField;
    @FXML
    private TextField openingMinuteField;
    @FXML
    private TextField closingHourField;
    @FXML
    private TextField closingMinuteField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Spinner<Double> expensesSpinner;
    @FXML
    private Spinner<Double> revenueSpinner;
    @FXML
    private TextField monthField;
    @FXML
    private Button acceptAndCloseBtn;
    @Setter
    private static boolean editMode;
    @Setter
    private static ShopTableView shop;

    /**
     * Default constructor.
     */
    public AddShopController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void editMode() {
        if (!editMode) {
            return;
        }
        nameField.setText(shop.getName());
        openingHourField.setText(String.valueOf(shop.getOpeningTime().getHour()));
        openingMinuteField.setText(String.valueOf(shop.getOpeningTime().getMinute()));
        closingHourField.setText(String.valueOf(shop.getClosingTime().getHour()));
        closingMinuteField.setText(String.valueOf(shop.getClosingTime().getMinute()));
        typeChoiceBox.setValue(shop.getType());
        descriptionTextArea.setText(shop.getDescription());
        expensesSpinner.getValueFactory().setValue(shop.getExpenses());
        revenueSpinner.getValueFactory().setValue(shop.getRevenue());
        monthField.setText(shop.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }

}
