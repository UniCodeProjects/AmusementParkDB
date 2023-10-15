package org.apdb4j.core.managers;

import java.time.LocalTime;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
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
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    public static void changeOpeningTime(final @NonNull String facilityID, final @NonNull LocalTime newOpeningTime,
                                         final @NonNull String account) {
        new QueryBuilder().createConnection()
                .queryAction(db -> db.update(FACILITIES)
                        .set(FACILITIES.OPENINGTIME, newOpeningTime)
                        .where(FACILITIES.FACILITYID.eq(facilityID))
                        .execute())
                .closeConnection();
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
        new QueryBuilder().createConnection()
                .queryAction(db -> db.update(FACILITIES)
                        .set(FACILITIES.CLOSINGTIME, newClosingTime)
                        .where(FACILITIES.FACILITYID.eq(facilityID))
                        .execute())
                .closeConnection();
    }

}
