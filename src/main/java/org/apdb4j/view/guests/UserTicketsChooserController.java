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
import org.apdb4j.view.BackableAbstractFXMLController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to buy either tickets or season tickets, or both.
 */
public class UserTicketsChooserController extends BackableAbstractFXMLController {

    private static final Insets TICKET_TYPE_LABEL_MARGIN = new Insets(5, 0, 10, 5);
    private static final Insets CATEGORY_CONTAINER_MARGIN = new Insets(0, 0, 5, 0);
    private static final int CHECKBOX_PREF_WIDTH = 140;
    private static final Insets CHECKBOX_MARGIN = new Insets(3, 0, 0, 5);
    private static final int SPINNERS_PREF_WIDTH = 70;
    private static final Insets SPINNERS_MARGIN = new Insets(0, 0, 0, 26);
    private static final int SPINNER_MAX_VALUE = 9999;
    @FXML
    private ListView<Label> cart;
    @FXML
    private VBox ticketsAndSeasonTicketsContainer;
    @FXML
    private Label totalPrice;
    private final List<String> ticketTypes;
    private final List<String> customersCategories;

    /**
     * Creates a new instance of this class with the provided ticket types and customers categories.
     * @param ticketTypes all the ticket types available in the current year's price list.
     * @param customersCategories all the categories of customers in the current year's price list.
     */
    public UserTicketsChooserController(final @NonNull List<String> ticketTypes,
                                        final @NonNull List<String> customersCategories) {
        this.customersCategories = new ArrayList<>(customersCategories);
        this.ticketTypes = new ArrayList<>(ticketTypes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        for (final String ticketType : ticketTypes) {
            final Label ticketTypeLabel = new Label(ticketType);
            ticketTypeLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 16));
            ticketsAndSeasonTicketsContainer.getChildren().add(ticketTypeLabel);
            VBox.setMargin(ticketTypeLabel, TICKET_TYPE_LABEL_MARGIN);
            for (final String category : customersCategories) {
                final HBox categoryContainer = new HBox();
                ticketsAndSeasonTicketsContainer.getChildren().add(categoryContainer);
                VBox.setMargin(categoryContainer, CATEGORY_CONTAINER_MARGIN);
                final CheckBox categoryCheckbox = new CheckBox(category + " (price)");
                categoryContainer.getChildren().add(categoryCheckbox);
                categoryCheckbox.setAllowIndeterminate(false);
                categoryCheckbox.setPrefWidth(CHECKBOX_PREF_WIDTH);
                HBox.setMargin(categoryCheckbox, CHECKBOX_MARGIN);
                final Spinner<Integer> quantitySpinner = new Spinner<>(0, SPINNER_MAX_VALUE, 0);
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
                        quantitySpinner.setDisable(!isSelected));
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
}
