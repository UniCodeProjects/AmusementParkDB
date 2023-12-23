package org.apdb4j.view.staff.tableview;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A review representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
@ToString
@EqualsAndHashCode
public class ReviewTableItem implements TableItem {

    private final StringProperty serviceID;
    private final DoubleProperty rating;
    private final StringProperty description;

    /**
     * Default constructor.
     * @param serviceID the park service ID related to this review
     * @param rating the review rating
     * @param description the review description
     */
    public ReviewTableItem(final @NonNull String serviceID,
                           final double rating,
                           final @NonNull String description) {
        this.serviceID = new SimpleStringProperty(serviceID);
        this.rating = new SimpleDoubleProperty(rating);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Returns the park service ID related to this review.
     * @return the park service ID related to this review
     */
    public String getServiceID() {
        return serviceID.get();
    }

    /**
     * Returns the review rating.
     * @return the review rating
     */
    public double getRating() {
        return rating.get();
    }

    /**
     * Returns the review description.
     * @return the review description
     */
    public String getDescription() {
        return description.get();
    }

}
