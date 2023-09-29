package org.apdb4j.core.managers;

import java.time.LocalDate;
import java.util.List;

import lombok.NonNull;
import org.jooq.Record;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Maintenances} table.
 */
public class MaintenanceManager {

    /**
     * Performs the SQL query that adds a new maintenance carried out by the provided employees
     * for the given facility.
     * @param facilityID the facility that needs to be maintained. If the value of this parameter is not the
     *                   identifier of a facility, the query will not be executed.
     * @param price the price of the maintenance.
     * @param description the description of the maintenance.
     * @param date the date on which the maintenance will be carried out.
     * @param account the account that performs this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @param employeeNIDs the employees that are responsible for the maintenance. If also one of the values
     *                     of this parameter is not the national identifier of an employee,
     *                     the query will not be executed.
     */
    static void addNewMaintenance(@NonNull String facilityID,
                           double price,
                           @NonNull String description,
                           @NonNull LocalDate date,
                           @NonNull String account,
                           @NonNull String... employeeNIDs) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that retrieves a list of all the facilities sorted
     * by the date of their last maintenance.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return a list containing all the park's facilities, sorted by the date of their last maintenance.
     */
    static @NonNull List<Record> sortFacilitiesByLastMaintenanceDate(@NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // viewAllPlannedMaintenances();
    // methods that add/edit/delete one or more employees (for maintenances not already carried out).

}
