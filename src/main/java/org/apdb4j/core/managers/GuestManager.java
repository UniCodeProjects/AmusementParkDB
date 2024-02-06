package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.util.QueryBuilder;

import static org.apdb4j.db.Tables.GUESTS;
import static org.apdb4j.db.Tables.STAFF;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Guests} table.
 */
public final class GuestManager {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final String PERMISSION_TYPE = "Guest";

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
                                      final @NonNull String email, final @NonNull String account) throws AccessDeniedException {
        if (isStaff(email)) {
            return false;
        }
        DB.createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        AccountManager.addNewAccount(email, PERMISSION_TYPE, account);
                        configuration.dsl()
                                .insertInto(GUESTS)
                                .values(guestID, email, name, surname)
                                .execute();
                    });
                    return 1;
                })
                .closeConnection();
        return true;
    }

    /**
     * Performs the SQL query that adds a new guest in the database.
     * @param guestID the identifier of the new guest.
     * @param name the guest's name.
     * @param surname the guest's surname.
     * @param email the guest's email.
     * @param username the username of the new account.
     * @param password the password of the new account.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewGuest(final @NonNull String guestID,
                                      final @NonNull String name,
                                      final @NonNull String surname,
                                      final @NonNull String email,
                                      final @NonNull String username,
                                      final @NonNull String password,
                                      final @NonNull String account) throws AccessDeniedException {
        if (isStaff(email)) {
            return false;
        }
        DB.createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        AccountManager.addNewAccount(email,
                                username,
                                password,
                                PERMISSION_TYPE,
                                account);
                        configuration.dsl()
                                .insertInto(GUESTS)
                                .values(guestID, email, name, surname)
                                .execute();
                    });
                    return 1;
                })
                .closeConnection();
        return true;
    }

    private static boolean isStaff(final String email) {
        return DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(STAFF)
                        .where(STAFF.EMAIL.eq(email))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
    }

}
