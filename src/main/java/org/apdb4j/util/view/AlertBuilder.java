package org.apdb4j.util.view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.function.Consumer;

/**
 * Builder class for GUI alerts.
 * @see Alert
 */
@SuppressWarnings("PMD.LinguisticNaming")
public class AlertBuilder {

    private final Alert alert;
    private boolean isHeaderSet;
    private boolean isContentSet;
    private Node contentNode;

    /**
     * Creates a new alert builder with the specified type.
     * @param type the alert type
     */
    public AlertBuilder(final Alert.AlertType type) {
        alert = new Alert(type);
    }

    /**
     * Sets the header text.
     * @param text the header
     * @return {@code this} for fluent style
     */
    public AlertBuilder setHeaderText(final String text) {
        alert.setHeaderText(text);
        isHeaderSet = true;
        return this;
    }

    /**
     * Sets the content text.
     * @param text the content
     * @return {@code this} for fluent style
     */
    public AlertBuilder setContentText(final String text) {
        alert.setContentText(text);
        isContentSet = true;
        return this;
    }

    /**
     * Sets the content node.
     * @param node the content
     * @return {@code this} for fluent style
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public AlertBuilder setContent(final Node node) {
        alert.getDialogPane().setContent(node);
        contentNode = node;
        isContentSet = true;
        return this;
    }

    /**
     * Sets the operation to run after the "OK" button is pressed.
     * @param operation the operation
     * @return {@code this} for fluent style
     */
    public AlertBuilder setOnClose(final Runnable operation) {
        alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, e -> operation.run());
        return this;
    }

    /**
     * Sets the operation to run using the previously set node after the "OK" button is pressed.
     * @param operation the operation
     * @return {@code this} for fluent style
     */
    public AlertBuilder setOnClose(final Consumer<Node> operation) {
        if (!isContentSet) {
            throw new IllegalStateException("No content was previously set.");
        }
        alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, e -> operation.accept(contentNode));
        return this;
    }

    /**
     * Shows the alert.
     * <p>
     * If any of the previous setters was not called before this method a default value is inserted.
     */
    public void show() {
        if (!isHeaderSet) {
            alert.setHeaderText(getDefaultHeader());
        }
        if (!isContentSet) {
            alert.setContentText("");
        }
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    private String getDefaultHeader() {
        return switch (alert.getAlertType()) {
            case ERROR -> "An error has occurred.";
            case WARNING -> "Attention!";
            case INFORMATION -> "Info.";
            case CONFIRMATION -> "Confirm your choice.";
            default -> "";
        };
    }

}
