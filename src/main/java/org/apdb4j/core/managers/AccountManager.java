package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Objects;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.PERMISSIONS;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Accounts} table.
 */
public class AccountManager {

    private static final QueryBuilder DB = new QueryBuilder();

    /**
     * Performs the SQL query that creates a new account with the provided email, but without
     * username and password, that will be set to {@code null}.
     * @param email the email of the new account.
     * @param permissionType the permissions of the new account. If the provided permission type does not exist
     *                       in the database, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
     public static void addNewAccount(@NonNull String email, @NonNull String permissionType, @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
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
    public static boolean addNewAccount(final @NonNull String email, final @NonNull String username, final @NonNull String password,
                              final @NonNull String permissionType,
                              final String account) throws AccessDeniedException {
        final Result<Record> count = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(PERMISSIONS)
                        .where(PERMISSIONS.PERMISSIONTYPE.eq(permissionType))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        // Checking if got only one result, and it is unique (accounts are unique).
        if (count.size() != 1 || count.get(0).get(0, Integer.class) != 1) {
            throw new IllegalStateException(permissionType + " is not present in the DB.");
        }
        if (Objects.nonNull(account)) {
            // todo: create permissions.
//            DB.definePermissions((Access) null, "");
        }
        final int tuplesAdded = DB.createConnection()
                .queryAction(db -> db.insertInto(ACCOUNTS, ACCOUNTS.EMAIL, ACCOUNTS.USERNAME, ACCOUNTS.PASSWORD, ACCOUNTS.PERMISSIONTYPE)
                        .values(email, username, password, permissionType)
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return tuplesAdded == 1;
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
     */
    public static void addCredentialsForAccount(@NonNull String email, @NonNull String username, @NonNull String password,
                                  @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Performs the SQL query that changes the password of the provided account.
     * @param email the email associated with the account. If the value of this parameter is not the email of an
     *              account, the query will not be executed.
     * @param actualPassword the actual password of the account. If the value of this parameter is not the
     *                       password of the provided account, the query will not be executed.
     * @param newPassword the new password for the account.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
     public static void updateAccountPassword(@NonNull String email, @NonNull String actualPassword, @NonNull String newPassword,
                               @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
     }

}
