package org.apdb4j.core.managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;

import static org.apdb4j.db.Tables.*;

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
     * @param employeeNIDs the employees that are responsible for the maintenance.<br/>
     *                     The query will not be executed if: <ul>
     *                     <li>at least one of the values of this parameter is not the national identifier of an employee, or</li>
     *                     <li>at least one of the provided employees has not a valid contract on the provided date</li>
     *                     </ul>
     * @return {@code true} if the maintenance is inserted, {@code false} otherwise.
     */
    public static boolean addNewMaintenance(final @NonNull String facilityID,
                                  final double price,
                                  final @NonNull String description,
                                  final @NonNull LocalDate date,
                                  final @NonNull String account,
                                  final @NonNull String... employeeNIDs) {
        if (!areAllContractsValid(date, employeeNIDs)) {
            return false;
        } else {
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

    // Checks if the contracts of the provided employees are valid in the provided date
    private static boolean areAllContractsValid(final LocalDate targetDate, final String... employeeNIDs) {
        final QueryBuilder queryBuilder = new QueryBuilder();
        final Collection<LocalDate> employeesContractsEndDate = new ArrayList<>();
        int validContracts = 0;
        for (final var employee : employeeNIDs) {
            queryBuilder.createConnection()
                    .queryAction(db -> db.select(CONTRACTS.ENDDATE)
                            .from(CONTRACTS)
                            .where(CONTRACTS.EMPLOYEENID.eq(employee))
                            .fetch())
                    .closeConnection()
                    .getResultAsRecords().forEach(record -> employeesContractsEndDate.add(record.get(CONTRACTS.ENDDATE)));
        }
        for (final var date : employeesContractsEndDate) {
            if (Objects.isNull(date) || date.isAfter(targetDate)) {
                validContracts++;
            }
        }
        return validContracts == employeeNIDs.length;
    }

    // viewAllPlannedMaintenances();
    // methods that add/edit/delete one or more employees (for maintenances not already carried out).

}
