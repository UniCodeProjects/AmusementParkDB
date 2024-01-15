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
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * An exhibition attraction representation used by the table view in the GUI.
 * @see AttractionTableItem
 * @see javafx.scene.control.TableView
 */
public class ExhibitionTableItem implements AttractionTableItem {

    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty type;
    private final StringProperty description;
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<LocalTime> time;
    private final IntegerProperty maxSeats;
    private final IntegerProperty spectators;
    private final DoubleProperty averageRating;
    private final IntegerProperty ratings;

    /**
     * Default constructor.
     * @param id the exhibition id
     * @param name the exhibition name
     * @param type the exhibition type
     * @param description the exhibition description
     * @param date the exhibition date
     * @param time the exhibition time
     * @param maxSeats the exhibition max seats
     * @param spectators the exhibition spectators number
     * @param averageRating the exhibition average rating
     * @param ratings the exhibition number of ratings
     */
    public ExhibitionTableItem(final @NonNull String id,
                               final @NonNull String name,
                               final @NonNull String type,
                               final String description,
                               final LocalDate date,
                               final LocalTime time,
                               final int maxSeats,
                               final int spectators,
                               final double averageRating,
                               final int ratings) {
        this.id = new SimpleStringProperty(id.trim());
        this.name = new SimpleStringProperty(name.trim());
        this.type = new SimpleStringProperty(type.trim());
        this.description = new SimpleStringProperty(StringUtils.defaultString(description).trim());
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.maxSeats = new SimpleIntegerProperty(maxSeats);
        this.spectators = new SimpleIntegerProperty(spectators);
        this.averageRating = new SimpleDoubleProperty(averageRating);
        this.ratings = new SimpleIntegerProperty(ratings);
    }

    /**
     * Returns the exhibition id.
     * @return the exhibition id
     */
    public String getId() {
        return id.get();
    }

    /**
     * Returns the exhibition name.
     * @return the exhibition name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the exhibition type.
     * @return the exhibition type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Returns the exhibition description.
     * @return the exhibition description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Returns the exhibition date.
     * @return the exhibition date
     */
    public LocalDate getDate() {
        return date.get();
    }

    /**
     * Returns the exhibition time.
     * @return the exhibition time
     */
    public LocalTime getTime() {
        return time.get();
    }

    /**
     * Returns the exhibition max seats.
     * @return the exhibition max seats
     */
    public int getMaxSeats() {
        return maxSeats.get();
    }

    /**
     * Returns the exhibition spectators number.
     * @return the exhibition spectators number
     */
    public int getSpectators() {
        return spectators.get();
    }

    /**
     * Returns the exhibition average rating.
     * @return the exhibition average rating
     */
    public double getAverageRating() {
        return averageRating.get();
    }

    /**
     * Returns the exhibition number of ratings.
     * @return the exhibition number of ratings
     */
    public int getRatings() {
        return ratings.get();
    }

    /**
     * Sets the exhibition id.
     * @param id the id
     */
    public void setId(final String id) {
        this.id.set(id);
    }

    /**
     * Sets the exhibition name.
     * @param name the name
     */
    public void setName(final String name) {
        this.name.set(name);
    }

    /**
     * Sets the exhibition type.
     * @param type the type
     */
    public void setType(final String type) {
        this.type.set(type);
    }

    /**
     * Sets the exhibition description.
     * @param description the description
     */
    public void setDescription(final String description) {
        this.description.set(description);
    }

    /**
     * Sets the exhibition date.
     * @param date the date
     */
    public void setDate(final LocalDate date) {
        this.date.set(date);
    }

    /**
     * Sets the exhibition time.
     * @param time the time
     */
    public void setTime(final LocalTime time) {
        this.time.set(time);
    }

    /**
     * Sets the exhibition maximum seats.
     * @param maxSeats the maximum seats value
     */
    public void setMaxSeats(final int maxSeats) {
        this.maxSeats.set(maxSeats);
    }

    /**
     * Sets the exhibition spectators value.
     * @param spectators the spectators value
     */
    public void setSpectators(final int spectators) {
        this.spectators.set(spectators);
    }

    /**
     * Sets the exhibition average rating.
     * @param averageRating the average rating value
     */
    public void setAverageRating(final double averageRating) {
        this.averageRating.set(averageRating);
    }

    /**
     * Sets the exhibition rating amount.
     * @param ratings the amount of ratings
     */
    public void setRatings(final int ratings) {
        this.ratings.set(ratings);
    }

}
