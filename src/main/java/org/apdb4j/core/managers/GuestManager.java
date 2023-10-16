package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import static org.apdb4j.db.Tables.GUESTS;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Guests} table.
 */
public final class GuestManager {

    private GuestManager() {
    }

    /**
     * Performs the SQL query that adds a new guest in the database.
     * @param guestID the identifier of the new guest.
     * @param name the guest's name.
     * @param surname the guest's surname.
     * @param email the guest's email.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewGuest(final @NonNull String guestID, final @NonNull String name, final @NonNull String surname,
                                      final @NonNull String email, final @NonNull String account) {
        final int insertedTuples = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.insertInto(GUESTS)
                        .values(guestID, name, surname, email)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return insertedTuples == 1;
    }

}
