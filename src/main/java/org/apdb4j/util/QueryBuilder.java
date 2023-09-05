package org.apdb4j.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * This builder class makes managing queries with jOOQ easy and fluent.
 */
public class QueryBuilder {

    private Connection connection;
    private boolean isConnectionCreated;
    private boolean isQueryExecuted;

    /**
     * Creates a JDBC connection to run the successive queries.
     * @return {@code this} for fluent style
     */
    public QueryBuilder createConnection() {
        if (isConnectionCreated || isQueryExecuted) {
            throw new IllegalStateException();
        }
        isConnectionCreated = connectionCreate();
        if (!isConnectionCreated) {
            throw new IllegalStateException("Connection could not be created.");
        }
        return this;
    }

    /**
     * Creates a JDBC connection to run the successive queries
     * given the connection details such as DB's url and credentials.
     * @param dbUrl the database's url
     * @param username the username
     * @param password the password
     * @return {@code this} for fluent style
     */
    public QueryBuilder createConnection(final String dbUrl, final String username, final String password) {
        if (isConnectionCreated || isQueryExecuted) {
            throw new IllegalStateException();
        }
        isConnectionCreated = connectionCreate(dbUrl, username, password);
        if (!isConnectionCreated) {
            throw new IllegalStateException("Connection could not be created.");
        }
        return this;
    }

    /**
     * Closes the JDBC connection previously created,
     * this is an ending operation.
     */
    public void closeConnection() {
        if (!isConnectionCreated || !isQueryExecuted) {
            throw new IllegalStateException();
        }
        final var connectionStatus = connectionClose();
        if (!connectionStatus) {
            throw new IllegalStateException("Connection could not be closed.");
        }
        isConnectionCreated = false;
        isQueryExecuted = false;
    }

    /**
     * Contains the query(ies) that will be executed with jOOQ.
     * The {@code Consumer<DSLContext>} allows to run queries from the
     * provided API.
     * @param db the jOOQ DSL context
     * @return {@code this} for fluent style
     */
    public QueryBuilder queryAction(final Consumer<DSLContext> db) {
        if (!isConnectionCreated || isQueryExecuted) {
            throw new IllegalStateException();
        }
        db.accept(DSL.using(Objects.requireNonNull(connection)));
        isQueryExecuted = true;
        return this;
    }

    private boolean connectionCreate() {
        final Dotenv dotenv = Dotenv.load();
        final var dbUrl = dotenv.get("DB_URL");
        final var username = dotenv.get("DB_USERNAME");
        final var password = dotenv.get("DB_PASSWORD");
        return connectionCreate(dbUrl, username, password);
    }

    private boolean connectionCreate(final String dbUrl, final String username, final String password) {
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            return connection.isValid(0);
        } catch (final SQLException e) {
            return false;
        }
    }

    private boolean connectionClose() {
        if (Objects.isNull(connection)) {
            return false;
        }
        try {
            connection.close();
            return connection.isClosed();
        } catch (final SQLException e) {
            return false;
        }
    }

}
