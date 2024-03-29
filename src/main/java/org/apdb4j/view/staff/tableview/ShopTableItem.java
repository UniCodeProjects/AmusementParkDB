package org.apdb4j.view.staff.tableview;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.NonNull;

import java.time.LocalTime;
import java.time.YearMonth;

/**
 * A shop representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
public class ShopTableItem implements TableItem {

    private final StringProperty id;
    private final StringProperty name;
    private final ObjectProperty<LocalTime> openingTime;
    private final ObjectProperty<LocalTime> closingTime;
    private final StringProperty type;
    private final StringProperty description;
    private final DoubleProperty expenses;
    private final DoubleProperty revenue;
    private final ObjectProperty<YearMonth> yearMonth;

    /**
     * Default constructor.
     * @param id the shop id
     * @param name the shop name
     * @param openingTime the shop opening time
     * @param closingTime the shop closing time
     * @param type the shop type
     * @param description the shop description
     * @param expense the shop expenses
     * @param revenue the shop revenue
     * @param yearMonth the yearMonth related to the shop expenses and revenues
     */
    public ShopTableItem(final @NonNull String id,
                         final @NonNull String name,
                         final @NonNull LocalTime openingTime,
                         final @NonNull LocalTime closingTime,
                         final @NonNull String type,
                         final @NonNull String description,
                         final Double expense,
                         final Double revenue,
                         final YearMonth yearMonth) {
        this.id = new SimpleStringProperty(id.trim());
        this.name = new SimpleStringProperty(name.trim());
        this.openingTime = new SimpleObjectProperty<>(openingTime);
        this.closingTime = new SimpleObjectProperty<>(closingTime);
        this.type = new SimpleStringProperty(type.trim());
        this.description = new SimpleStringProperty(description.trim());
        this.expenses = expense == null ? null : new SimpleDoubleProperty(expense);
        this.revenue = revenue == null ? null : new SimpleDoubleProperty(revenue);
        this.yearMonth = yearMonth == null ? null : new SimpleObjectProperty<>(yearMonth);
    }

    /**
     * Returns the shop id.
     * @return the shop id
     */
    public String getId() {
        return id.get();
    }

    /**
     * Returns the shop name.
     * @return the shop name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the shop opening time.
     * @return the shop opening time
     */
    public LocalTime getOpeningTime() {
        return openingTime.get();
    }

    /**
     * Returns the shop closing time.
     * @return the shop closing time
     */
    public LocalTime getClosingTime() {
        return closingTime.get();
    }

    /**
     * Returns the shop type.
     * @return the shop type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Returns the shop description.
     * @return the shop description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Returns the shop expenses.
     * @return the shop expenses
     */
    public Double getExpenses() {
        return expenses == null ? null : expenses.get();
    }

    /**
     * Returns the shop revenue.
     * @return the shop revenue
     */
    public Double getRevenue() {
        return revenue == null ? null : revenue.get();
    }

    /**
     * Returns the month related to the expenses/revenues.
     * @return the month related to the expenses/revenues
     */
    public YearMonth getYearMonth() {
        return yearMonth == null ? null : yearMonth.get();
    }

}
