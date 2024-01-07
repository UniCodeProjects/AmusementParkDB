package org.apdb4j.controllers;

import org.apdb4j.view.staff.tableview.TableItem;

import java.time.LocalDate;
import java.util.Collection;

/**
 * An administration controller specifically designed for managing maintenances.
 */
public interface MaintenanceController extends AdministrationController, Filterable {

    /**
     * Filters all the data in this table view based on the given local date.
     * @param date the date to filter by
     * @param <T> the type of the {@code TableItem}
     * @return the filtered data
     */
    <T extends TableItem> Collection<T> filterByDate(LocalDate date);

}
