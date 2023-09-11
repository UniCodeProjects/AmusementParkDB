package org.apdb4j.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessDeniedException;
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

    /**
     * Defines the access that allow the execution of the following query(ies).
     * @param required the required access
     * @param actual the executor's actual access
     * @return {@link QueryBuilder1} for fluent style
     */
    public QueryBuilder1 defineAccess(final Access required,
                                      final Access actual) throws AccessDeniedException {
        if (invalidAccess(required, actual)) {
            throw new AccessDeniedException();
        }
        return new QueryBuilder1();
    }

    /**
     * Defines the access that allow the execution of the following query(ies).
     * @param required the required access set
     * @param actual the executor's actual access
     * @return {@link QueryBuilder1} for fluent style
     */
    public QueryBuilder1 defineAccess(final Set<Access> required,
                                      final Access actual) throws AccessDeniedException {
        if (invalidAccess(required, actual)) {
            throw new AccessDeniedException();
        }
        return new QueryBuilder1();
    }

    /**
     * First builder used for chaining.
     */
    public class QueryBuilder1 {
        /**
         * Creates a JDBC connection to run the successive queries.
         * @return {@link QueryBuilder2} for fluent style
         */
        public QueryBuilder2 createConnection() {
            final var isConnectionCreated = connectionCreate();
            if (!isConnectionCreated) {
                throw new IllegalStateException("Connection could not be created.");
            }
            return new QueryBuilder2();
        }
    }

    /**
     * Second builder used for chaining.
     */
    public class QueryBuilder2 {
        /**
         * Contains the query(ies) that will be executed with jOOQ.
         * The {@code Consumer<DSLContext>} allows to run queries from the
         * provided API.
         * @param db the jOOQ DSL context
         * @return {@link QueryBuilder3} for fluent style
         */
        public QueryBuilder3 queryAction(final Consumer<DSLContext> db) {
            db.accept(DSL.using(Objects.requireNonNull(connection)));
            return new QueryBuilder3();
        }
    }

    /**
     * Third and last builder used for chaining.
     */
    public class QueryBuilder3 {
        /**
         * Closes the JDBC connection previously created,
         * this is an ending operation.
         */
        public void closeConnection() {
            final var connectionStatus = connectionClose();
            if (!connectionStatus) {
                throw new IllegalStateException("Connection could not be closed.");
            }
        }
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

    private boolean invalidAccess(final Access required, final Access actual) {
        return !actual.equals(required);
    }

    private boolean invalidAccess(final Collection<Access> required, final Access actual) {
        return required.stream().noneMatch(actual::equals);
    }

}
