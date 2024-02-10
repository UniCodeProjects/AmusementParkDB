package org.apdb4j.view.staff.tableview;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.Year;

/**
 * A ticket type representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
@ToString
@EqualsAndHashCode
public class TicketTypeTableItem implements TableItem {

    private final StringProperty type;
    private final StringProperty category;
    private final ObjectProperty<Year> year;
    private final DoubleProperty price;
    private final IntegerProperty duration;

    /**
     * Default constructor.
     * @param type the ticket type
     * @param category the ticket type category
     * @param year the ticket type year
     * @param price the ticket type price
     * @param duration the ticket type duration
     */
    public TicketTypeTableItem(final @NonNull String type,
                               final @NonNull String category,
                               final @NonNull Year year,
                               final double price,
                               final int duration) {
        this.type = new SimpleStringProperty(type.trim());
        this.category = new SimpleStringProperty(category.trim());
        this.year = new SimpleObjectProperty<>(year);
        this.price = new SimpleDoubleProperty(price);
        this.duration = new SimpleIntegerProperty(duration);
    }

    /**
     * Returns the ticket type.
     * @return the ticket type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Sets the ticket type.
     * @param type the type
     */
    public void setType(final String type) {
        this.type.set(type);
    }

    /**
     * Returns the ticket type category.
     * @return the ticket type category
     */
    public String getCategory() {
        return category.get();
    }

    /**
     * Sets the ticket type category.
     * @param category the category
     */
    public void setCategory(final String category) {
        this.category.set(category);
    }

    /**
     * Returns the ticket type year.
     * @return the ticket type year
     */
    public Year getYear() {
        return year.get();
    }

    /**
     * Sets the ticket type year.
     * @param year the year
     */
    public void setYear(final Year year) {
        this.year.set(year);
    }

    /**
     * Returns the ticket type price.
     * @return the ticket type price
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Sets the ticket type price.
     * @param price the price
     */
    public void setPrice(final double price) {
        this.price.set(price);
    }

    /**
     * Returns the ticket type duration.
     * @return the ticket type duration
     */
    public int getDuration() {
        return duration.get();
    }

    /**
     * Sets the ticket type duration.
     * @param duration the duration
     */
    public void setDuration(final int duration) {
        this.duration.set(duration);
    }

}
