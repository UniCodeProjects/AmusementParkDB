package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.util.RegexUtils;
import org.jooq.Record;
import org.jooq.Result;

import static org.apdb4j.db.Tables.*;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Accounts} table.
 */
public final class AccountManager {

    private static final QueryBuilder DB = new QueryBuilder();
    private static final String EMAIL_REGEX = "^([a-z0-9._%]+@[a-z0-9.]+\\.[a-z]{2,})$";

    private AccountManager() {
    }

    /**
     * Performs the SQL query that creates a new account with the provided email, but without
     * a username and password, that will be set to {@code null}.
     * @param email the email of the new account.
     * @param permissionType the permissions of the new account. If the provided permission type does not exist
     *                       in the database, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple insertion
     */
     public static boolean addNewAccount(final @NonNull String email, final @NonNull String permissionType,
                                         final @NonNull String account) throws AccessDeniedException {
         if (permissionTypeNotExists(permissionType)) {
             throw new IllegalArgumentException(permissionType + " is not present in the DB.");
         }
         if (RegexUtils.getMatch(email, EMAIL_REGEX).isEmpty()) {
             return false;
         }
         final int insertedTuple = DB.createConnection()
                 .queryAction(db -> db.insertInto(ACCOUNTS, ACCOUNTS.EMAIL, ACCOUNTS.PERMISSIONTYPE)
                         .values(email, permissionType)
                         .execute())
                 .closeConnection()
                 .getResultAsInt();
         return insertedTuple == 1;
     }

    /**
     * Performs the SQL query that creates a new account with the provided email,
     * and with the provided credentials.
     * @param email the email of the new account.
     * @param username the username of the new account.
     * @param password the password of the new account.
     * @param permissionType the permissions of the new account. If the provided permission type does not exist
     *                       in the database, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     *                Its value must be {@code null} when a guest is creating his account.
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewAccount(final @NonNull String email,
                                        final @NonNull String username,
                                        final @NonNull String password,
                                        final @NonNull String permissionType,
                                        final String account) throws AccessDeniedException {
        if (permissionTypeNotExists(permissionType)) {
            throw new IllegalStateException(permissionType + " is not present in the DB.");
        }
        if (RegexUtils.getMatch(email, EMAIL_REGEX).isEmpty()) {
            return false;
        }
        final int insertedTuples = DB.createConnection()
                .queryAction(db -> db.insertInto(ACCOUNTS)
                        .values(email, username, password, permissionType)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return insertedTuples == 1;
    }

    /**
     * Performs the SQL query that adds the credentials for an account that does not
     * have any credentials.
     * If this method is called on an account that already has its credentials, the query will not be executed.
     * @param email the email associated with the account. If the value of this parameter is not the email
     *              of an account, the query will not be executed.
     * @param username the username provided for the account.
     * @param password the password provided for the account.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean addCredentialsForAccount(final @NonNull String email,
                                                   final @NonNull String username,
                                                   final @NonNull String password,
                                                   final @NonNull String account) throws AccessDeniedException {
        final boolean areCredentialsPresent = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.EMAIL.eq(email))
                        .and(ACCOUNTS.USERNAME.isNotNull())
                        .and(ACCOUNTS.PASSWORD.isNotNull())
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
        if (areCredentialsPresent) {
            return false;
        }
        /*
         The query executor is a guest;
         however, they are not allowed to modify an account that is not one and not theirs.
        */
        if (isGuest(account) && !isGuest(email) && !account.equals(email)) {
            throw new AccessDeniedException("Guest account has no permission over " + email);
        }
        final int updatedTuples = DB.createConnection()
                .queryAction(db -> db.update(ACCOUNTS)
                        .set(ACCOUNTS.USERNAME, username)
                        .set(ACCOUNTS.PASSWORD, password)
                        .where(ACCOUNTS.EMAIL.eq(email))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return updatedTuples == 1;
    }

    /**
     * Performs the SQL query that changes the password of the provided account.
     * @param email the email associated with the account. If the value of this parameter is not the email of an
     *              account, the query will not be executed.
     * @param oldPassword the actual password of the account.
     *                    If the value of this parameter is not the password of the provided account,
     *                    the query will not be executed.
     * @param newPassword the new password for the account.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
     public static boolean updateAccountPassword(final @NonNull String email,
                                                 final @NonNull String oldPassword,
                                                 final @NonNull String newPassword,
                                                 final @NonNull String account) throws AccessDeniedException {
         if (isGuest(account) && !isGuest(email) && !account.equals(email)) {
             throw new AccessDeniedException("Guest account has no permission over " + email);
         }
         final int updatedTuples = DB.createConnection()
                 .queryAction(db -> db.update(ACCOUNTS)
                         .set(ACCOUNTS.PASSWORD, newPassword)
                         .where(ACCOUNTS.EMAIL.eq(email))
                         .and(ACCOUNTS.PASSWORD.eq(oldPassword))
                         .execute())
                 .closeConnection()
                 .getResultAsInt();
         return updatedTuples == 1;
     }

    /**
     * Determines if the account with the given username is an admin.
     * @param username the account's username
     * @return {@code true} if admin
     */
     public static boolean isAdminByUsername(final String username) {
         return DB.createConnection()
                 .queryAction(db -> db.selectCount()
                         .from(STAFF.join(ACCOUNTS)
                                 .on(STAFF.EMAIL.eq(ACCOUNTS.EMAIL)))
                         .where(STAFF.ISADMIN.isTrue())
                         .and(ACCOUNTS.USERNAME.eq(username))
                         .fetchOne(0, int.class))
                 .closeConnection()
                 .getResultAsInt() == 1;
     }

    /**
     * Determines if the account with the given username is an employee.
     * @param username the account's username
     * @return {@code true} if employee
     */
     public static boolean isEmployeeByUsername(final String username) {
         return DB.createConnection()
                 .queryAction(db -> db.selectCount()
                         .from(STAFF.join(ACCOUNTS)
                                 .on(STAFF.EMAIL.eq(ACCOUNTS.EMAIL)))
                         .where(STAFF.ISEMPLOYEE.isTrue())
                         .and(ACCOUNTS.USERNAME.eq(username))
                         .fetchOne(0, int.class))
                 .closeConnection()
                 .getResultAsInt() == 1;
     }

    /**
     * Determines if the account with the given username is a guest.
     * @param username the account's username
     * @return {@code true} if guest
     */
     public static boolean isGuestByUsername(final String username) {
         return DB.createConnection()
                 .queryAction(db -> db.selectCount()
                         .from(ACCOUNTS)
                         .where(ACCOUNTS.USERNAME.eq(username))
                         .and(ACCOUNTS.PERMISSIONTYPE.eq("Guest"))
                         .fetchOne(0, int.class))
                 .closeConnection()
                 .getResultAsInt() == 1;
     }

    /**
     * Determines if the given account is a guest.
     * @param email the account's email
     * @return {@code true} if it is a guest
     */
    public static boolean isGuest(final String email) {
        return DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(GUESTS)
                        .where(GUESTS.EMAIL.eq(email))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
    }

    private static boolean permissionTypeNotExists(final String permissionType) {
        final Result<Record> count = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(PERMISSIONS)
                        .where(PERMISSIONS.PERMISSIONTYPE.eq(permissionType))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        // Checking if got only one result, and it is unique (accounts are unique).
        return count.size() != 1 || count.get(0).get(0, int.class) != 1;
    }

}
