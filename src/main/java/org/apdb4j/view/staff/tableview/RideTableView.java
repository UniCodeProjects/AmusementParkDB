package org.apdb4j.view.staff.tableview;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.NonNull;

import java.time.LocalTime;

/**
 * A ride attraction representation used by the table view in the GUI.
 * @see AttractionTableView
 * @see javafx.scene.control.TableView
 */
public class RideTableView implements AttractionTableView {

    private final StringProperty id;
    private final StringProperty name;
    private final ObjectProperty<LocalTime> openingTime;
    private final ObjectProperty<LocalTime> closingTime;
    private final StringProperty type;
    private final StringProperty intensity;
    private final IntegerProperty duration;
    private final IntegerProperty maxSeats;
    private final StringProperty description;
    private final IntegerProperty minHeight;
    private final IntegerProperty maxHeight;
    private final DoubleProperty minWeight;
    private final DoubleProperty maxWeight;
    private final StringProperty status;
    private final DoubleProperty averageRating;
    private final IntegerProperty ratings;

    /**
     * Default constructor.
     * @param id the ride id
     * @param name the ride name
     * @param openingTime the ride opening time
     * @param closingTime the ride closing time
     * @param type the ride type
     * @param intensity the ride intensity
     * @param duration the ride duration
     * @param maxSeats the ride max seats
     * @param description the ride description
     * @param minHeight the ride min height
     * @param maxHeight the ride max height
     * @param minWeight the ride min weight
     * @param maxWeight the ride max weight
     * @param status the ride status
     * @param averageRating the ride average rating
     * @param ratings the ride ratings number
     */
    public RideTableView(final @NonNull String id,
                         final @NonNull String name,
                         final @NonNull LocalTime openingTime,
                         final @NonNull LocalTime closingTime,
                         final @NonNull String type,
                         final @NonNull String intensity,
                         final int duration,
                         final int maxSeats,
                         final @NonNull String description,
                         final int minHeight,
                         final int maxHeight,
                         final double minWeight,
                         final double maxWeight,
                         final @NonNull String status,
                         final double averageRating,
                         final int ratings) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.openingTime = new SimpleObjectProperty<>(openingTime);
        this.closingTime = new SimpleObjectProperty<>(closingTime);
        this.type = new SimpleStringProperty(type);
        this.intensity = new SimpleStringProperty(intensity);
        this.duration = new SimpleIntegerProperty(duration);
        this.maxSeats = new SimpleIntegerProperty(maxSeats);
        this.description = new SimpleStringProperty(description);
        this.minHeight = new SimpleIntegerProperty(minHeight);
        this.maxHeight = new SimpleIntegerProperty(maxHeight);
        this.minWeight = new SimpleDoubleProperty(minWeight);
        this.maxWeight = new SimpleDoubleProperty(maxWeight);
        this.status = new SimpleStringProperty(status);
        this.averageRating = new SimpleDoubleProperty(averageRating);
        this.ratings = new SimpleIntegerProperty(ratings);
    }

    /**
     * Returns the ride id.
     * @return the ride id
     */
    public String getId() {
        return id.get();
    }

    /**
     * Returns the ride name.
     * @return the ride name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the ride opening time.
     * @return the ride opening time
     */
    public LocalTime getOpeningTime() {
        return openingTime.get();
    }

    /**
     * Returns the ride closing time.
     * @return the ride closing time
     */
    public LocalTime getClosingTime() {
        return closingTime.get();
    }

    /**
     * Returns the ride type.
     * @return the ride type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Returns the ride intensity.
     * @return the ride intensity
     */
    public String getIntensity() {
        return intensity.get();
    }

    /**
     * Returns the ride duration.
     * @return the ride duration
     */
    public int getDuration() {
        return duration.get();
    }

    /**
     * Returns the ride max seats.
     * @return the ride max seats
     */
    public int getMaxSeats() {
        return maxSeats.get();
    }

    /**
     * Returns the ride description.
     * @return the ride description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Returns the ride min height.
     * @return the ride min height
     */
    public int getMinHeight() {
        return minHeight.get();
    }

    /**
     * Returns the ride max height.
     * @return the ride max height
     */
    public int getMaxHeight() {
        return maxHeight.get();
    }

    /**
     * Returns the ride min weight.
     * @return the ride min weight
     */
    public double getMinWeight() {
        return minWeight.get();
    }

    /**
     * Returns the ride max weight.
     * @return the ride max weight
     */
    public double getMaxWeight() {
        return maxWeight.get();
    }

    /**
     * Returns the ride status.
     * @return the ride status
     */
    public String getStatus() {
        return status.get();
    }

    /**
     * Returns the ride average rating.
     * @return the ride average rating
     */
    public double getAverageRating() {
        return averageRating.get();
    }

    /**
     * Returns the ride ratings number.
     * @return the ride ratings number
     */
    public int getRatings() {
        return ratings.get();
    }

}
