package org.apdb4j.core.managers;

import java.time.LocalTime;

import lombok.NonNull;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Facilities} table.
 */
public interface FacilityManager {

    /**
     * Performs the SQL query that changes the opening time of the given facility.
     * @param facilityID the facility identifier. If the value of this parameter is not the identifier of a facility,
     *                   the query will not be executed.
     * @param newOpeningTime the new opening time of the provided facility.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void changeOpeningTime(@NonNull String facilityID, @NonNull LocalTime newOpeningTime,
                           @NonNull String account);

    /**
     * Performs the SQL query that changes the closing time of the given facility.
     * @param facilityID the facility identifier. If the value of this parameter is not the identifier of a facility,
     *                   the query will not be executed.
     * @param newClosingTime the new closing time of the given facility.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void changeClosingTime(@NonNull String facilityID, @NonNull LocalTime newClosingTime,
                           @NonNull String account);

}
