package org.apdb4j.view.guests;

import edu.umd.cs.findbugs.annotations.NonNull;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.converter.IntegerStringConverter;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.util.*;

/**
 * FXML controller for the screen that allows the user to buy either tickets or season tickets, or both.
 */
public class UserTicketsChooserController extends BackableAbstractFXMLController {

    private static final Insets TICKET_TYPE_LABEL_MARGIN = new Insets(5, 0, 10, 5);
    private static final Insets CATEGORY_CONTAINER_MARGIN = new Insets(0, 0, 5, 0);
    private static final int CHECKBOXES_PREF_WIDTH = 140;
    private static final Insets CHECKBOXES_MARGIN = new Insets(3, 0, 0, 5);
    private static final int SPINNERS_PREF_WIDTH = 70;
    private static final Insets SPINNERS_MARGIN = new Insets(0, 0, 0, 26);
    private static final int SPINNERS_MAX_VALUE = 9999;
    private static final String EURO_SYMBOL = "\u20AC";
    @FXML
    private ListView<Label> cart;
    @FXML
    private VBox ticketsAndSeasonTicketsContainer;
    @FXML
    private Label totalPriceTitle;
    @FXML
    private Label totalPrice;
    private final Map<String, Set<Pair<String, Double>>> ticketTypesAndCategoriesWithPrice;

    /**
     * Creates a new instance of this class with the provided ticket types and customers categories.
     * @param ticketTypesAndCategoriesWithPrice a map that contains, for each ticket type, all the categories of the
     *                                          customers, each one paired with the price of that ticket type.
     */
    public UserTicketsChooserController(
            final @NonNull Map<String, Set<Pair<String, Double>>> ticketTypesAndCategoriesWithPrice) {
        this.ticketTypesAndCategoriesWithPrice = Map.copyOf(ticketTypesAndCategoriesWithPrice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        for (final String ticketType : ticketTypesAndCategoriesWithPrice.keySet()) {
            final Label ticketTypeLabel = new Label(ticketType);
            ticketTypeLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 16));
            ticketsAndSeasonTicketsContainer.getChildren().add(ticketTypeLabel);
            VBox.setMargin(ticketTypeLabel, TICKET_TYPE_LABEL_MARGIN);
            final Set<Pair<String, Double>> categoriesWithPrice = ticketTypesAndCategoriesWithPrice.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals(ticketType))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .get(); // no check with isPresent() because the value is always present
            for (final Pair<String, Double> categoryWithPrice : categoriesWithPrice) {
                final HBox categoryContainer = new HBox();
                ticketsAndSeasonTicketsContainer.getChildren().add(categoryContainer);
                VBox.setMargin(categoryContainer, CATEGORY_CONTAINER_MARGIN);
                final CheckBox categoryCheckbox =
                        new CheckBox(categoryWithPrice.getKey()
                                + " (" + EURO_SYMBOL + formatAsPrice(categoryWithPrice.getValue()) + ")");
                categoryContainer.getChildren().add(categoryCheckbox);
                categoryCheckbox.setAllowIndeterminate(false);
                categoryCheckbox.setPrefWidth(CHECKBOXES_PREF_WIDTH);
                HBox.setMargin(categoryCheckbox, CHECKBOXES_MARGIN);
                final Spinner<Integer> quantitySpinner = new Spinner<>(0, SPINNERS_MAX_VALUE, 0);
                quantitySpinner.getValueFactory().setConverter(new ExceptionHandledIntegerStringConverter());
                categoryContainer.getChildren().add(quantitySpinner);
                quantitySpinner.setEditable(true);
                quantitySpinner.setDisable(true);
                quantitySpinner.getEditor().setOnAction(event -> {
                    try {
                        Integer.parseInt(quantitySpinner.getEditor().getText());
                    } catch (NumberFormatException e) {
                        quantitySpinner.getEditor().setText("0");
                    }
                });
                categoryCheckbox.selectedProperty().addListener((observable, wasSelected, isSelected) ->
                        quantitySpinner.setDisable(wasSelected));
                categoryCheckbox.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
                    if (wasSelected && Double.compare(Double.parseDouble(totalPrice.getText()), 0) > 0) {
                        final Double newValue = Double.parseDouble(totalPrice.getText())
                                - (categoryWithPrice.getValue() * quantitySpinner.getValue());
                        totalPrice.setText(formatAsPrice(newValue));
                    } else if (isSelected && !totalPrice.getText().isEmpty()) {
                        final Double newValue = Double.parseDouble(totalPrice.getText())
                                + (categoryWithPrice.getValue() * quantitySpinner.getValue());
                        totalPrice.setText(formatAsPrice(newValue));
                    }
                });
                quantitySpinner.valueProperty().addListener((observable, previousAmount, newAmount) -> {
                    final int diff = newAmount - previousAmount;
                    if (diff > 0) {
                        if (totalPrice.getText().isEmpty()) {
                            final Double newTotalPrice = categoryWithPrice.getValue() * diff;
                            totalPrice.setText(formatAsPrice(newTotalPrice));
                        } else {
                            final Double newTotalPrice = Double.parseDouble(totalPrice.getText())
                                    + (categoryWithPrice.getValue() * diff);
                            totalPrice.setText(formatAsPrice(newTotalPrice));
                        }
                    } else if (diff < 0) {
                        final Double newTotalPrice = Double.parseDouble(totalPrice.getText())
                                + (categoryWithPrice.getValue() * diff);
                        totalPrice.setText(formatAsPrice(newTotalPrice));
                    }
                });
                quantitySpinner.setPrefWidth(SPINNERS_PREF_WIDTH);
                HBox.setMargin(quantitySpinner, SPINNERS_MARGIN);
            }
        }
    }

    /**
     * An {@link IntegerStringConverter} that handles {@code NumberFormatException} in method
     * {@link IntegerStringConverter#fromString(String)}.
     */
    private static final class ExceptionHandledIntegerStringConverter extends IntegerStringConverter {

        /**
         * Converts the string provided into an object defined by the specific converter.
         * Format of the string and type of the resulting object is defined by the specific converter.
         * When the input string is not convertible into an {@code Integer}, the method returns 0.
         * @param value the {@code String} to convert
         * @return an {@code Integer} representation of the provided {@code String}, 0 when the conversion
         * is not possible.
         */
        @Override
        public Integer fromString(final String value) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    private String formatAsPrice(final @NonNull Double price) {
        String doubleString = price.toString();
        final String decimalDigits = doubleString.substring(doubleString.indexOf('.') + 1);
        if (decimalDigits.length() == 1) {
            doubleString += "0";
        }
        return doubleString;
    }
}
