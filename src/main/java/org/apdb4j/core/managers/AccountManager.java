package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.util.RegexUtils;
import org.jooq.exception.DataAccessException;

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
     * @return {@code true} on successful tuple insertion
     */
     public static boolean addNewAccount(final @NonNull String email, final @NonNull String permissionType) {
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
     * @return {@code true} on successful tuple insertion
     */
    public static boolean addNewAccount(final @NonNull String email,
                                        final @NonNull String username,
                                        final @NonNull String password,
                                        final @NonNull String permissionType) {
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
     * @return {@code true} on successful tuple update
     */
    public static boolean addCredentialsForAccount(final @NonNull String email,
                                                   final @NonNull String username,
                                                   final @NonNull String password) {
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
     * @return {@code true} on successful tuple update
     */
     public static boolean updateAccountPassword(final @NonNull String email,
                                                 final @NonNull String oldPassword,
                                                 final @NonNull String newPassword) {
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

    /**
     * Retrieves the "person ID" of the owner of the account with the provided {@code username}.<br/>
     * The "person ID" is {@link org.apdb4j.db.tables.Guests#GUESTID} if the owner of the account is a guest<br/>
     * or {@link org.apdb4j.db.tables.Staff#STAFFID} if the owner of the account is a staff member.
     *
     * @param username the username of an account.
     * @return the identifier of the owner of the account with the provided username.
     */
    public static @NonNull String getPersonID(final @NonNull String username) {
        final QueryBuilder queryBuilder = new QueryBuilder();
        final boolean isUsernameValid = queryBuilder
                .createConnection()
                .queryAction(db -> db.selectCount()
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.USERNAME.eq(username))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
        if (!isUsernameValid) {
            throw new IllegalArgumentException("There is no account with the provided username");
        }
        final String accountEmail = getAccountEmail(username);
        final var personIDField = isGuest(accountEmail) ? GUESTS.GUESTID : STAFF.STAFFID;
        final var table = isGuest(accountEmail) ? GUESTS : STAFF;
        final var joinField = isGuest(accountEmail) ? GUESTS.EMAIL : STAFF.EMAIL;
        return queryBuilder
                .createConnection()
                .queryAction(db -> db.select(personIDField)
                        .from(ACCOUNTS)
                        .join(table)
                        .on(ACCOUNTS.EMAIL.eq(joinField))
                        .where(ACCOUNTS.USERNAME.eq(username))
                        .fetch())
                .closeConnection()
                .getResultAsRecords().get(0).get(personIDField);
    }

    /**
     * Retrieves the account email given a username.
     * @param username the account's username
     * @return the username
     */
    public static @NonNull String getAccountEmail(final @NonNull String username) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(ACCOUNTS.EMAIL)
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.USERNAME.eq(username))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .get(0)
                .get(ACCOUNTS.EMAIL);
    }

    /**
     * Retrieves the name of the owner of the account with the provided username, if such account exists.
     * Otherwise, an {@link IllegalArgumentException} will be thrown.
     * @param accountUsername the username of the account.
     * @return the name of the owner of the account with the provided username.
     */
    public static @NonNull String getAccountOwnerName(final @NonNull String accountUsername) {
        if (isInvalidUsername(accountUsername)) {
            throw new IllegalArgumentException("There is no account with the username: " + accountUsername);
        }
        return isGuestByUsername(accountUsername) ? new QueryBuilder().createConnection()
                .queryAction(db -> db.select(GUESTS.NAME)
                        .from(GUESTS)
                        .where(GUESTS.EMAIL.eq(getAccountEmail(accountUsername)))
                        .fetch()).closeConnection().getResultAsRecords().getValue(0, GUESTS.NAME)
                : new QueryBuilder().createConnection()
                .queryAction(db -> db.select(STAFF.NAME)
                        .from(STAFF)
                        .where(STAFF.EMAIL.eq(getAccountEmail(accountUsername)))
                        .fetch())
                .closeConnection().getResultAsRecords().getValue(0, STAFF.NAME);
    }

    /**
     * Retrieves the surname of the owner of the account with the provided username, if such account exists.
     * Otherwise, an {@link IllegalArgumentException} will be thrown.
     * @param accountUsername the username of the account.
     * @return the surname of the owner of the account with the provided username.
     */
    public static @NonNull String getAccountOwnerSurname(final @NonNull String accountUsername) {
        if (isInvalidUsername(accountUsername)) {
            throw new IllegalArgumentException("There is no account with the username: " + accountUsername);
        }
        return isGuestByUsername(accountUsername) ? new QueryBuilder().createConnection()
                .queryAction(db -> db.select(GUESTS.SURNAME)
                        .from(GUESTS)
                        .where(GUESTS.EMAIL.eq(getAccountEmail(accountUsername)))
                        .fetch())
                .closeConnection().getResultAsRecords().getValue(0, GUESTS.SURNAME)
                : new QueryBuilder().createConnection()
                .queryAction(db -> db.select(STAFF.SURNAME)
                        .from(STAFF)
                        .where(STAFF.EMAIL.eq(getAccountEmail(accountUsername)))
                        .fetch())
                .closeConnection().getResultAsRecords().getValue(0, STAFF.SURNAME);
    }

    /**
     * Changes the name of the owner of the account with the provided email, if such account exists.
     * @param accountEmail the email of the account.
     * @param newName the new name of the account owner.
     * @return {@code true} if the update is successfully, {@code false} otherwise (e.g. if an account with the provided
     *         email does not exist).
     */
    public static boolean changeAccountOwnerName(final @NonNull String accountEmail, final @NonNull String newName) {
        try {
            return isGuest(accountEmail) ? new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(GUESTS)
                            .set(GUESTS.NAME, newName)
                            .where(GUESTS.EMAIL.eq(accountEmail))
                            .execute())
                    .closeConnection().getResultAsInt() == 1
                    : new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(STAFF)
                            .set(STAFF.NAME, newName)
                            .where(STAFF.EMAIL.eq(accountEmail))
                            .execute())
                    .closeConnection().getResultAsInt() == 1;
        } catch (final DataAccessException e) {
            return false;
        }
    }

    /**
     * Changes the surname of the owner of the account with the provided email, if such account exists.
     * @param accountEmail the email of the account.
     * @param newSurname the new surname of the account owner.
     * @return {@code true} if the update is successfully, {@code false} otherwise (e.g. if an account with the provided
     *         email does not exist).
     */
    public static boolean changeAccountOwnerSurname(final @NonNull String accountEmail, final @NonNull String newSurname) {
        try {
            return isGuest(accountEmail) ? new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(GUESTS)
                            .set(GUESTS.SURNAME, newSurname)
                            .where(GUESTS.EMAIL.eq(accountEmail))
                            .execute())
                    .closeConnection().getResultAsInt() == 1
                    : new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(STAFF)
                            .set(STAFF.SURNAME, newSurname)
                            .where(STAFF.EMAIL.eq(accountEmail))
                            .execute())
                    .closeConnection().getResultAsInt() == 1;
        } catch (final DataAccessException e) {
            return false;
        }
    }

    /**
     * Changes the username of the account with the provided email, if such account exists.
     * @param accountEmail the email of the account.
     * @param newUsername the new username of the account.
     * @return {@code true} if the update is successfully, {@code false} otherwise (e.g. if an account with the provided
     *         email does not exist or if {@code newUsername} is a username of another account).
     */
    public static boolean changeUsername(final @NonNull String accountEmail, final @NonNull String newUsername) {
        try {
            return new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(ACCOUNTS)
                            .set(ACCOUNTS.USERNAME, newUsername)
                            .where(ACCOUNTS.EMAIL.eq(accountEmail))
                            .execute())
                    .closeConnection().getResultAsInt() == 1;
        } catch (final DataAccessException e) {
            return false;
        }
    }

    /**
     * Changes the email of the account whose email is {@code actualEmail}, if such account exists.
     * @param actualEmail the current email of the account.
     * @param newEmail the new email of the account.
     * @return {@code true} if the update is successfully, {@code false} otherwise (e.g. if an account with
     *         {@code actualEmail} does not exist or {@code newEmail} is an email of another account).
     */
    public static boolean changeEmail(final @NonNull String actualEmail, final @NonNull String newEmail) {
        try {
            return new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(ACCOUNTS)
                            .set(ACCOUNTS.EMAIL, newEmail)
                            .where(ACCOUNTS.EMAIL.eq(actualEmail))
                            .execute())
                    .closeConnection().getResultAsInt() == 1;
        } catch (final DataAccessException e) {
            return false;
        }
    }

    // TODO: improve method: three queries are performed instead of one
    private static boolean isInvalidUsername(final @NonNull String username) {
        return !isGuestByUsername(username) && !isAdminByUsername(username) && !isEmployeeByUsername(username);
    }

}
