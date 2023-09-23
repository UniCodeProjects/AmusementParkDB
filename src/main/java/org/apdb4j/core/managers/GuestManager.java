package org.apdb4j.core.managers;

import lombok.NonNull;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Guests} table.
 */
public interface GuestManager {

    /**
     * Performs the SQL query that adds a new guest in the database.
     * @param guestID the identifier of the new guest.
     * @param name the guest's name.
     * @param surname the guest's surname.
     * @param email the guest's email.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void addNewGuest(@NonNull String guestID, @NonNull String name, @NonNull String surname,
                     @NonNull String email, @NonNull String account);

}
