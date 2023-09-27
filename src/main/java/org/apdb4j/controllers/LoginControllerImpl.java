package org.apdb4j.controllers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;

import static org.apdb4j.db.Tables.ACCOUNTS;

/**
 * The implementation of the login controller.
 * @see LoginController
 * @see Controller
 */
public class LoginControllerImpl implements LoginController {

    private static final QueryBuilder DB = new QueryBuilder();

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
        return resultCount.size() == 1 && resultCount.get(0).get(0, Integer.class) == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkSignUp(final @NonNull String email, final @NonNull String username, final @NonNull String password) {
        return false;
    }

}
