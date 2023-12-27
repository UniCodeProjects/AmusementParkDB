package org.apdb4j.controllers;

import org.apdb4j.view.staff.tableview.TableItem;

import java.util.Collection;

/**
 * Includes all the controllers for the staff screen.
 */
public interface AdministrationController extends Controller {

    /**
     * Retrieves the table item linked to this staff controller.
     * @param <T> the type of the {@code TableItem}
     * @return a collection of table items
     * @see TableItem
     */
    <T extends TableItem> Collection<T> getData();

    /**
     * Inserts in the database the given data.
     * @param item the data to insert
     * @param <T> the type of the {@code TableItem}
     * @return the added data
     */
    <T extends TableItem> T addData(T item);

    /**
     * Edits the database with the given data.
     * @param item the edited data
     * @param <T> the type of the {@code TableItem}
     * @return the edited data
     */
    <T extends TableItem> T editData(T item);

}
