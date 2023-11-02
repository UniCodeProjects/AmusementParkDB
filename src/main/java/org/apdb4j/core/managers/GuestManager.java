package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.GuestPermission;
import org.apdb4j.util.QueryBuilder;

import static org.apdb4j.db.Tables.*;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Guests} table.
 */
public final class GuestManager {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final String PERMISSION_TYPE = GuestPermission.class.getSimpleName().replace("Permission", "");

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
        if (isStaff(email)) {
            return false;
        }
        final boolean insertedAccount = AccountManager.addNewAccount(email, PERMISSION_TYPE, account);
        if (!insertedAccount) {
            return false;
        }
        final int insertedTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(GUESTS)
                        .values(guestID, name, surname, email)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        if (insertedTuples == 0) {
            return Manager.removeTupleFromDB(ACCOUNTS, account, email);
        }
        return insertedTuples == 1;
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
        final boolean insertedAccount = AccountManager.addNewAccount(email,
                username,
                password,
                PERMISSION_TYPE,
                account);
        if (!insertedAccount) {
            return false;
        }
        final int insertedTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(GUESTS)
                        .values(guestID, name, surname, email)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        if (insertedTuples == 0) {
            return Manager.removeTupleFromDB(ACCOUNTS, account, email);
        }
        return insertedTuples == 1;
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
