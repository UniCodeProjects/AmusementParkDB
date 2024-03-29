package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.TableItem;

import java.util.Collection;
import java.util.Optional;

/**
 * Specialised controller for handling employees.
 */
public interface EmployeeController extends AdministrationController, Filterable {

    /**
     * Inserts in the database the given data.
     * @param employeeItem the employee data to insert
     * @param contractItem the contract data to insert
     * @param <T> the type of the {@code TableItem}
     * @return the added employee data
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    <T extends TableItem> T addData(T employeeItem, T contractItem);

    /**
     * Removes from the DB the selected employee item.
     * @param employeeItem the employee to fire
     * @param <T> the {@code EmployeeTableItem} type
     * @return an optional containing the fired employee, empty if no employee was fired
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    <T extends TableItem> Optional<T> fire(T employeeItem);

    /**
     * Retrieves all the table items of fired employees.
     * @param <T> the type of the {@code TableItem}
     * @return a collection of fired employees table items
     * @throws org.jooq.exception.DataAccessException if query fails
     * @see TableItem
     */
    <T extends TableItem> Collection<T> getFiredData();

}
