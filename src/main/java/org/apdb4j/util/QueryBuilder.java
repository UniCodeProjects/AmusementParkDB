package org.apdb4j.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.apdb4j.core.permissions.Permission;
import org.apdb4j.core.permissions.PermissionDeniedException;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * This builder class makes managing queries with jOOQ easy and fluent.
 */
public class QueryBuilder {

    private Connection connection;
    private boolean isPermissionSet;
    private boolean isConnectionCreated;
    private boolean isQueryExecuted;
    private static final String INVALID_METHOD_ORDER_MSG = "Invalid method order";

    /**
     * Defines the permissions that allow the execution of the following query(ies).
     * @param required the required permissions
     * @param actual the executor's actual permissions
     * @return {@code this} for fluent style
     */
    public QueryBuilder definePermissions(final Permission required,
                                          final Permission actual) throws PermissionDeniedException {
        if (isConnectionCreated || isQueryExecuted) {
            throw new IllegalStateException(INVALID_METHOD_ORDER_MSG);
        }
        if (invalidPermissions(required, actual)) {
            throw new PermissionDeniedException();
        }
        isPermissionSet = true;
        return this;
    }

    /**
     * Defines the permissions that allow the execution of the following query(ies).
     * @param required the required permissions set
     * @param actual the executor's actual permissions
     * @return {@code this} for fluent style
     */
    public QueryBuilder definePermissions(final Set<Permission> required,
                                          final Permission actual) throws PermissionDeniedException {
        if (isConnectionCreated || isQueryExecuted) {
            throw new IllegalStateException(INVALID_METHOD_ORDER_MSG);
        }
        if (invalidPermissions(required, actual)) {
            throw new PermissionDeniedException();
        }
        isPermissionSet = true;
        return this;
    }

    /**
     * Creates a JDBC connection to run the successive queries.
     * @return {@code this} for fluent style
     */
    public QueryBuilder createConnection() {
        if (isConnectionCreated || isQueryExecuted || !isPermissionSet) {
            throw new IllegalStateException(INVALID_METHOD_ORDER_MSG);
        }
        isConnectionCreated = connectionCreate();
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
        if (!isConnectionCreated || !isQueryExecuted || !isPermissionSet) {
            throw new IllegalStateException(INVALID_METHOD_ORDER_MSG);
        }
        final var connectionStatus = connectionClose();
        if (!connectionStatus) {
            throw new IllegalStateException("Connection could not be closed.");
        }
        isPermissionSet = false;
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
        if (!isConnectionCreated || isQueryExecuted || !isPermissionSet) {
            throw new IllegalStateException(INVALID_METHOD_ORDER_MSG);
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

    private boolean invalidPermissions(final Permission required, final Permission actual) {
        return !actual.equals(required);
    }

    private boolean invalidPermissions(final Collection<Permission> required, final Permission actual) {
        return required.stream().noneMatch(actual::equals);
    }

}
