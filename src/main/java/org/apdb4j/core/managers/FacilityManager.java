package org.apdb4j.core.managers;

import java.time.LocalTime;

import lombok.NonNull;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Facilities} table.
 */
public final class FacilityManager {

    private FacilityManager() {
    }

    /**
     * Performs the SQL query that changes the opening time of the given facility.
     * @param facilityID the facility identifier. If the value of this parameter is not the identifier of a facility,
     *                   the query will not be executed.
     * @param newOpeningTime the new opening time of the provided facility.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void changeOpeningTime(final @NonNull String facilityID, final @NonNull LocalTime newOpeningTime,
                                         final @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that changes the closing time of the given facility.
     * @param facilityID the facility identifier. If the value of this parameter is not the identifier of a facility,
     *                   the query will not be executed.
     * @param newClosingTime the new closing time of the given facility.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void changeClosingTime(final @NonNull String facilityID, final @NonNull LocalTime newClosingTime,
                                         final @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
