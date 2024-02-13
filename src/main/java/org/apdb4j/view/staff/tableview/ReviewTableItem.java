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
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A review representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
@ToString
@EqualsAndHashCode
public class ReviewTableItem implements TableItem {

    private final StringProperty reviewID;
    private final StringProperty serviceID;
    private final DoubleProperty rating;
    private final StringProperty description;
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<LocalTime> time;
    private final StringProperty author;

    /**
     * Default constructor.
     * @param reviewID the review ID
     * @param serviceID the park service ID related to this review
     * @param rating the review rating
     * @param description the review description
     * @param date the review date
     * @param time the review time
     * @param author the review author
     */
    public ReviewTableItem(final @NonNull String reviewID,
                           final @NonNull String serviceID,
                           final double rating,
                           final String description,
                           final @NonNull LocalDate date,
                           final @NonNull LocalTime time,
                           final @NonNull String author) {
        this.reviewID = new SimpleStringProperty(reviewID);
        this.serviceID = new SimpleStringProperty(serviceID.trim());
        this.rating = new SimpleDoubleProperty(rating);
        this.description = new SimpleStringProperty(StringUtils.defaultString(description).trim());
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.author = new SimpleStringProperty(author);
    }

    /**
     * Returns the review ID of this item.
     * @return the review ID of this item
     */
    public String getReviewID() {
        return reviewID.get();
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

    /**
     * Returns the review date.
     * @return the review date
     */
    public LocalDate getDate() {
        return date.get();
    }

    /**
     * Returns the review time.
     * @return the review time
     */
    public LocalTime getTime() {
        return time.get();
    }

    /**
     * Returns the review's author.
     * @return the review's author
     */
    public String getAuthor() {
        return author.get();
    }

}
