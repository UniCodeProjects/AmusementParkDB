package org.apdb4j.core.managers;

import java.time.LocalDate;
import java.util.List;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;

import static org.apdb4j.db.Tables.MAINTENANCES;
import static org.apdb4j.db.Tables.RESPONSIBILITIES;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Maintenances} table.
 */
public final class MaintenanceManager {

    private MaintenanceManager() {
    }

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
     * @return {@code true} if the maintenances is inserted, {@code false} otherwise.
     */
    public static boolean addNewMaintenance(final @NonNull String facilityID,
                                  final double price,
                                  final @NonNull String description,
                                  final @NonNull LocalDate date,
                                  final @NonNull String account,
                                  final @NonNull String... employeeNIDs) {
        final var maintenancesInserted = new QueryBuilder().createConnection()
                .queryAction(db -> db.insertInto(MAINTENANCES)
                        .values(facilityID, price, description, date)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        final var builder = new QueryBuilder();
        var responsibilitiesInserted = 0;
        for (final var employee : employeeNIDs) {
            responsibilitiesInserted += builder.createConnection()
                    .queryAction(db -> db.insertInto(RESPONSIBILITIES)
                            .values(facilityID, date, employee)
                            .execute())
                    .closeConnection()
                    .getResultAsInt();
        }
        return maintenancesInserted == 1 && responsibilitiesInserted == employeeNIDs.length;
    }

    /**
     * Performs the SQL query that retrieves a list of all the facilities sorted
     * by the date of their last maintenance.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return a list containing all the park's facilities, sorted by the date of their last maintenance.
     */
    public static @NonNull List<Record> sortFacilitiesByLastMaintenanceDate(final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select()
                        .from(MAINTENANCES)
                        .orderBy(MAINTENANCES.DATE.desc())
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    // viewAllPlannedMaintenances();
    // methods that add/edit/delete one or more employees (for maintenances not already carried out).

}
