package org.apdb4j.view.staff.tableview;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;

/**
 * A maintenance representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
@ToString
@EqualsAndHashCode
public class MaintenanceTableItem implements TableItem {

    private final StringProperty facilityID;
    private final DoubleProperty price;
    private final StringProperty description;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty employeeIDs;

    /**
     * Default constructor.
     * @param facilityID the facility ID
     * @param price the maintenance price
     * @param description the maintenance description
     * @param date the maintenance date
     * @param employeeIDs the IDs of the employees involved in the maintenance
     */
    public MaintenanceTableItem(final @NonNull String facilityID,
                                final double price,
                                final @NonNull String description,
                                final @NonNull LocalDate date,
                                final @NonNull String employeeIDs) {
        this.facilityID = new SimpleStringProperty(facilityID.trim());
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description.trim());
        this.date = new SimpleObjectProperty<>(date);
        this.employeeIDs = new SimpleStringProperty(employeeIDs.trim());
    }

    /**
     * Returns the facility ID linked to this maintenance.
     * @return the facility ID linked to this maintenance
     */
    public String getFacilityID() {
        return facilityID.get();
    }

    /**
     * Returns the maintenance price.
     * @return the maintenance price
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Returns the maintenance description.
     * @return the maintenance description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Returns the maintenance date.
     * @return the maintenance date
     */
    public LocalDate getDate() {
        return date.get();
    }

    /**
     * Returns the employee IDs linked to this maintenance.
     * @return the employee IDs linked to this maintenance
     */
    public String getEmployeeIDs() {
        return employeeIDs.get();
    }

}
