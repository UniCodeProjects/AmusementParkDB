package org.apdb4j.controllers;

import org.apdb4j.view.staff.tableview.TableItem;

import java.util.Collection;

/**
 * Specialised controller for handling employees.
 */
public interface EmployeeController extends AdministrationController {

    /**
     * Removes from the DB the selected employee item.
     * @param employeeItem the employee to fire
     * @param <T> the {@code EmployeeTableItem} type
     * @return the fired employee
     */
    <T extends TableItem> T fire(T employeeItem);

    /**
     * Retrieves all the table items of fired employees.
     * @param <T> the type of the {@code TableItem}
     * @return a collection of fired employees table items
     * @see TableItem
     */
    <T extends TableItem> Collection<T> getFiredData();

}
