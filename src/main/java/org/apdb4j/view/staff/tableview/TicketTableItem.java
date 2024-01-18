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

import java.time.LocalDate;
import java.time.Year;

/**
 * A ticket representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
@ToString
@EqualsAndHashCode
public class TicketTableItem implements TableItem {

    private final StringProperty ticketID;
    private final ObjectProperty<LocalDate> purchaseDate;
    private final ObjectProperty<LocalDate> validOn;
    private final ObjectProperty<LocalDate> validUntil;
    private final IntegerProperty remainingEntrances;
    private final StringProperty ownerID;
    private final ObjectProperty<Year> year;
    private final StringProperty type;
    private final DoubleProperty typePrice;
    private final StringProperty category;
    private final IntegerProperty duration;
    private final ObjectProperty<LocalDate> validationDate;

    /**
     * Default constructor.
     * @param ticketID the ticket id
     * @param purchaseDate the ticket purchase date
     * @param validOn the date the ticket is valid on
     * @param validUntil the date the ticket is valid until
     * @param remainingEntrances the ticket remaining entrances
     * @param ownerID the owner id of the ticket
     * @param year the ticket's year
     * @param type the ticket type
     * @param typePrice the ticket type price
     * @param category the ticket category
     * @param duration the ticket duration
     * @param validationDate the date the ticket was validated
     */
    public TicketTableItem(final @NonNull String ticketID,
                           final @NonNull LocalDate purchaseDate,
                           final LocalDate validOn,
                           final LocalDate validUntil,
                           final int remainingEntrances,
                           final @NonNull String ownerID,
                           final @NonNull Year year,
                           final @NonNull String type,
                           final double typePrice,
                           final @NonNull String category,
                           final int duration,
                           final @NonNull LocalDate validationDate) {
        this.ticketID = new SimpleStringProperty(ticketID.trim());
        this.purchaseDate = new SimpleObjectProperty<>(purchaseDate);
        this.validOn = new SimpleObjectProperty<>(validOn);
        this.validUntil = new SimpleObjectProperty<>(validUntil);
        this.remainingEntrances = new SimpleIntegerProperty(remainingEntrances);
        this.ownerID = new SimpleStringProperty(ownerID.trim());
        this.year = new SimpleObjectProperty<>(year);
        this.type = new SimpleStringProperty(type.trim());
        this.typePrice = new SimpleDoubleProperty(typePrice);
        this.category = new SimpleStringProperty(category.trim());
        this.duration = new SimpleIntegerProperty(duration);
        this.validationDate = new SimpleObjectProperty<>(validationDate);
    }

    /**
     * Returns the ticket id.
     * @return the ticket id
     */
    public String getTicketID() {
        return ticketID.get();
    }

    /**
     * Returns the ticket purchase date.
     * @return the ticket purchase date
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate.get();
    }

    /**
     * Returns the ticket valid on date.
     * @return the ticket valid on date
     */
    public LocalDate getValidOn() {
        return validOn.get();
    }

    /**
     * Returns the ticket valid until date.
     * @return the ticket valid until date
     */
    public LocalDate getValidUntil() {
        return validUntil.get();
    }

    /**
     * Returns the ticket remaining entrances.
     * @return the ticket remaining entrances
     */
    public int getRemainingEntrances() {
        return remainingEntrances.get();
    }

    /**
     * Returns the ticket owner id.
     * @return the ticket owner id
     */
    public String getOwnerID() {
        return ownerID.get();
    }

    /**
     * Returns the ticket year.
     * @return the ticket year
     */
    public Year getYear() {
        return year.get();
    }

    /**
     * Returns the ticket type.
     * @return the ticket type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Returns the ticket type price.
     * @return the ticket type price
     */
    public double getTypePrice() {
        return typePrice.get();
    }

    /**
     * Returns the ticket category.
     * @return the ticket category
     */
    public String getCategory() {
        return category.get();
    }

    /**
     * Returns the ticket duration.
     * @return the ticket duration
     */
    public int getDuration() {
        return duration.get();
    }

    /**
     * Returns the ticket validation date.
     * @return the ticket validation date
     */
    public LocalDate getValidationDate() {
        return validationDate.get();
    }

}
