package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import java.time.LocalTime;

import static org.apdb4j.db.Tables.FACILITIES;

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
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    public static boolean changeOpeningTime(final @NonNull String facilityID, final @NonNull LocalTime newOpeningTime) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(FACILITIES)
                        .set(FACILITIES.OPENINGTIME, newOpeningTime)
                        .where(FACILITIES.FACILITYID.eq(facilityID))
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that changes the closing time of the given facility.
     * @param facilityID the facility identifier. If the value of this parameter is not the identifier of a facility,
     *                   the query will not be executed.
     * @param newClosingTime the new closing time of the given facility.
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    public static boolean changeClosingTime(final @NonNull String facilityID, final @NonNull LocalTime newClosingTime) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(FACILITIES)
                        .set(FACILITIES.CLOSINGTIME, newClosingTime)
                        .where(FACILITIES.FACILITYID.eq(facilityID))
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

}
