package org.apdb4j.controllers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

import java.util.Optional;

import static org.apdb4j.db.Tables.ACCOUNTS;

/**
 * The implementation of the login controller.
 * @see LoginController
 * @see Controller
 */
public class LoginControllerImpl implements LoginController {

    private static final QueryBuilder DB = new QueryBuilder();
    private String errorMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkSignIn(final @NonNull String username, final @NonNull String password) {
        final Result<Record> resultCount = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.USERNAME.eq(username))
                        .and(ACCOUNTS.PASSWORD.eq(password))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        // Checking if got only one result, and it is unique (accounts are unique).
        if (resultCount.size() == 1 && resultCount.get(0).get(0, Integer.class) == 1) {
            return true;
        }
        errorMessage = "No account found, please check and try again.";
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkSignUp(final @NonNull String email, final @NonNull String username, final @NonNull String password) {
        int queryResult;
        try {
            queryResult = DB.createConnection()
                    .queryAction(db -> db.insertInto(ACCOUNTS, ACCOUNTS.EMAIL, ACCOUNTS.USERNAME, ACCOUNTS.PASSWORD)
                            .values(email, username, password)
                            .execute())
                    .closeConnection()
                    .getResultAsInt();
        } catch (final DataAccessException e) {
            errorMessage = e.getCause().toString();
            return false;
        }
        return queryResult == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStaff(final @NonNull String username) {
        final Result<Record> resultCount = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.USERNAME.eq(username))
                        .and(ACCOUNTS.PERMISSIONTYPE.eq("Admin"))
                        .or(ACCOUNTS.PERMISSIONTYPE.eq("Staff"))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        // Checking if got only one result, and it is unique (accounts are unique).
        return resultCount.size() == 1 && resultCount.get(0).get(0, Integer.class) == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGuest(final @NonNull String username) {
        final Result<Record> resultCount = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.USERNAME.eq(username))
                        .and(ACCOUNTS.PERMISSIONTYPE.eq("Guest"))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        // Checking if got only one result, and it is unique (accounts are unique).
        return resultCount.size() == 1 && resultCount.get(0).get(0, Integer.class) == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

}
