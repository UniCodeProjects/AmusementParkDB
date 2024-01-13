package org.apdb4j.controllers;

import org.apdb4j.view.staff.tableview.TableItem;

import java.util.Collection;

/**
 * Represents a controller that can be filtered in some way.
 */
public interface Filterable {

    /**
     * Filters all the data in this table view based on the given string.
     * @param s the string to filter on
     * @param <T> the type of the {@code TableItem}
     * @return the filtered data
     * @throws org.jooq.exception.DataAccessException if filtering fails
     */
    <T extends TableItem> Collection<T> filter(String s);

}
