package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
     * @param employeeNIDs the employees that are responsible for the maintenance.<br/>
     *                     The query will not be executed if: <ul>
     *                     <li>at least one of the values of this parameter is not the national identifier of an employee, or</li>
     *                     <li>at least one of the provided employees has not a valid contract on the provided date</li>
     *                     </ul>
     * @return {@code true} if the maintenance is inserted.
     */
    public static boolean addNewMaintenance(final @NonNull String facilityID,
                                            final double price,
                                            final @NonNull String description,
                                            final @NonNull LocalDate date,
                                            final @NonNull String... employeeNIDs) {
        if (employeeNIDs.length == 0 || !areAllContractsValid(date, employeeNIDs)) {
            return false;
        } else {
            new QueryBuilder().createConnection()
                    .queryAction(db -> {
                        db.transaction(configuration -> {
                            final var dslContext = configuration.dsl();
                            dslContext.insertInto(MAINTENANCES)
                                    .values(facilityID, price, description, date)
                                    .execute();
                            for (final var employee : employeeNIDs) {
                                dslContext.insertInto(RESPONSIBILITIES)
                                        .values(facilityID, date, employee)
                                        .execute();
                            }
                        });
                        return 1;
                    }).closeConnection();
            return true;
        }
    }

    /**
     * Performs the SQL query that retrieves all the facilities with the date of their last maintenance.
     * @return all the park's facilities, with the date of their last maintenance. The date is {@code null} if the
     *         facility has never been maintained.
     */
    public static @NonNull Map<String, LocalDate> sortFacilitiesByLastMaintenanceDate() {
        final var sortedMaintenances = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(FACILITIES.FACILITYID, MAINTENANCES.DATE)
                        .from(FACILITIES)
                        .leftOuterJoin(MAINTENANCES).on(FACILITIES.FACILITYID.eq(MAINTENANCES.FACILITYID))
                        .orderBy(MAINTENANCES.DATE.desc())
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        final Map<String, LocalDate> result = new HashMap<>();
        for (final var maintenance : sortedMaintenances) {
            final var facilityMaintained = maintenance.get(MAINTENANCES.FACILITYID);
            final var maintenanceDate = maintenance.get(MAINTENANCES.DATE);
            if (!result.containsKey(facilityMaintained)
                    && (maintenanceDate == null || maintenanceDate.isBefore(LocalDate.now()))) {
                result.put(facilityMaintained, maintenanceDate);
            }
        }
        if (result.size() < sortedMaintenances.size()) {
            for (final var maintenance : sortedMaintenances) {
                final var facilityMaintained = maintenance.get(MAINTENANCES.FACILITYID);
                if (!result.containsKey(facilityMaintained)) {
                    result.put(facilityMaintained, null);
                }
            }
        }
        return result;
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

}
