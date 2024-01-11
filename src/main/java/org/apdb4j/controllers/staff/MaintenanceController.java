package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.MaintenanceTableItem;

import java.time.LocalDate;
import java.util.Collection;

/**
 * An administration controller specifically designed for managing maintenances.
 */
public interface MaintenanceController extends AdministrationController, Filterable {

    /**
     * Filters all the data in this table view based on the given local date.
     * @param date the date to filter by
     * @param <T> the {@code MaintenanceTableItem} type
     * @return the filtered data
     */
    <T extends MaintenanceTableItem> Collection<T> filterByDate(LocalDate date);

    /**
     * Filters all the data in this table view to retrieve the ride related maintenances.
     * @param <T> the {@code MaintenanceTableItem} type
     * @return the filtered data
     */
    <T extends MaintenanceTableItem> Collection<T> filterByRides();

    /**
     * Filters all the data in this table view to retrieve the shop related maintenances.
     * @param <T> the {@code MaintenanceTableItem} type
     * @return the filtered data
     */
    <T extends MaintenanceTableItem> Collection<T> filterByShops();

}
