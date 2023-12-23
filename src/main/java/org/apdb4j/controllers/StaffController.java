package org.apdb4j.controllers;

import org.apdb4j.view.staff.tableview.TableItem;

import java.util.Collection;

/**
 * Includes all the controllers for the staff screen.
 */
public interface StaffController extends Controller {

    /**
     * Retrieves the table item linked to this staff controller.
     * @param <T> the type of the {@code TableItem}
     * @return a collection of table items
     * @see TableItem
     */
    <T extends TableItem> Collection<T> getData();

}