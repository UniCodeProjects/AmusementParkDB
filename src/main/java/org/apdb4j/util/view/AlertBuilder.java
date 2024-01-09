package org.apdb4j.util.view;

import javafx.scene.control.Alert;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Builder class for GUI alerts.
 * @see Alert
 */
@SuppressWarnings("PMD.LinguisticNaming")
public class AlertBuilder {

    private Alert.AlertType type;
    private String header;
    private String content;

    /**
     * Sets the alert type.
     * @param type the alert type
     * @return {@code this} for fluent style
     */
    public AlertBuilder setAlertType(final Alert.AlertType type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the header text.
     * @param text the header
     * @return {@code this} for fluent style
     */
    public AlertBuilder setHeaderText(final String text) {
        header = text;
        return this;
    }

    /**
     * Sets the content text.
     * @param text the content
     * @return {@code this} for fluent style
     */
    public AlertBuilder setContentText(final String text) {
        content = text;
        return this;
    }

    /**
     * Builds and shows the alert.
     * <p>
     * If any of the previous setters was not called before this method a default value is inserted.
     */
    public void show() {
        final var alert = new Alert(ObjectUtils.defaultIfNull(type, Alert.AlertType.NONE));
        alert.setHeaderText(StringUtils.defaultIfBlank(header, getDefaultHeader()));
        alert.setContentText(StringUtils.defaultString(content));
        alert.show();
    }

    private String getDefaultHeader() {
        return switch (ObjectUtils.defaultIfNull(type, Alert.AlertType.NONE)) {
            case ERROR -> "An error has occurred.";
            case WARNING -> "Attention!";
            case INFORMATION -> "Info.";
            case CONFIRMATION -> "Confirm your choice.";
            default -> "";
        };
    }

}
