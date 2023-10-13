package org.apdb4j.util;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.Permission;
import org.apdb4j.core.permissions.uid.AppPermissionUID;
import org.apdb4j.core.permissions.uid.DBPermissionUID;
import org.apdb4j.core.permissions.uid.ReturnSequence;
import org.apdb4j.core.permissions.uid.UID;
import org.apdb4j.core.permissions.uid.UIDParser;
import org.apdb4j.core.permissions.uid.UIDSection;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * This builder class makes managing queries with jOOQ easy and fluent.
 */
public class QueryBuilder {

    private Connection connection;
    private Object queryResult;

    /**
     * Defines the requiredPermission that allows the execution of the following query(ies).
     * @param permission the object that defines the permission for the query
     * @return {@link QueryBuilder} for fluent style
     * @throws AccessDeniedException when the requiredPermission objects are incompatible
     */
    public QueryBuilder definePermissions(final @NonNull Permission permission) throws AccessDeniedException {
        if (invalidAccess(permission)) {
            throw new AccessDeniedException();
        }
        return this;
    }

    /**
     * Defines the requiredPermission that allows the execution of the following query(ies).
     * @param permissions the objects that define the permissions for the query
     * @return {@link QueryBuilder} for fluent style
     * @throws AccessDeniedException when the requiredPermission objects are incompatible
     */
    public QueryBuilder definePermissions(final @NonNull Set<? extends Permission> permissions)
            throws AccessDeniedException {
        if (invalidAccess(permissions)) {
            throw new AccessDeniedException();
        }
        return this;
    }

    /**
     * Creates a JDBC connection to run the successive queries.
     * @return {@link QueryBuilder1} for fluent style
     */
    public QueryBuilder1 createConnection() {
        final var isConnectionCreated = connectionCreate();
        if (!isConnectionCreated) {
            throw new IllegalStateException("Connection could not be created.");
        }
        return new QueryBuilder1();
    }

    /**
     * First builder used for chaining.
     */
    public class QueryBuilder1 {
        /**
         * Contains the query(ies) that will be executed with jOOQ.
         * The {@code Consumer<DSLContext>} allows to run queries from the
         * provided API.
         * @param db the jOOQ DSL context
         * @return {@link QueryBuilder2} for fluent style
         */
        public QueryBuilder2 queryAction(final Function<DSLContext, Object> db) {
            queryResult = db.apply(DSL.using(Objects.requireNonNull(connection)));
            return new QueryBuilder2();
        }
    }

    /**
     * Second builder used for chaining.
     */
    public class QueryBuilder2 {
        /**
         * Closes the JDBC connection previously created.
         * @return {@link QueryBuilder3} for fluent style
         */
        public QueryBuilder3 closeConnection() {
            final var connectionStatus = connectionClose();
            if (!connectionStatus) {
                throw new IllegalStateException("Connection could not be closed.");
            }
            return new QueryBuilder3();
        }
    }
    /**
     * Third builder used for chaining.
     */
    public class QueryBuilder3 {
        /**
         * Retrieves the query result as a {@link Result}{@code <}{@link Record}{@code >}.
         * @return the query result
         */
        @SuppressWarnings("unchecked")
        public Result<Record> getResultAsRecords() {
            return (Result<Record>) Objects.requireNonNull(queryResult);
        }

        /**
         * Retrieves the query result as an int.
         * @return the query result
         */
        public int getResultAsInt() {
            return (int) Objects.requireNonNull(queryResult);
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

    private boolean invalidAccess(final @NonNull Permission permission) {
        final UID uidFromDb;
        try {
            uidFromDb = new DBPermissionUID(permission.email()).getUid();
        } catch (final NoSuchElementException e) {
            // The provided email does not exist in the DB.
            return true;
        }
        // The UID from the database and the one just generated are not equals.
        if (!Objects.equals(new AppPermissionUID(permission.requiredPermission()).getUid(), uidFromDb)) {
            return true;
        }
        final List<UIDSection> parsed = UIDParser.parse(uidFromDb.uid());
        // If all attributes are empty and READ and WRITE are set to GLOBAL, it is an admin permission.
        if (isAdmin(parsed)) {
            return false;
        }
        // Otherwise, it is a normal permission.
        final var returnSequences = parsed.stream()
                .map(UIDSection::returnSequence)
                .flatMap(Collection::stream)
                .toList();
        return returnSequences.stream()
                .noneMatch(returnSequence -> returnSequence.equals(new ReturnSequence(permission.values())));
    }

    private boolean isAdmin(final List<UIDSection> parsed) {
        return parsed.stream()
                .map(UIDSection::returnSequence)
                .allMatch(returnSequences -> returnSequences.stream()
                        .allMatch(returnSequence -> returnSequence.getAttribute().isEmpty()
                                && returnSequence.getRead().equals(AccessType.Read.GLOBAL)
                                && returnSequence.getWrite().equals(AccessType.Write.GLOBAL)));
    }

    private boolean invalidAccess(final @NonNull Set<? extends Permission> permissions) {
        return permissions.stream().anyMatch(this::invalidAccess);
    }

}
