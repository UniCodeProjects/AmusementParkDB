package org.apdb4j.util;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.Attribute;
import org.apdb4j.core.permissions.Permission;
import org.apdb4j.core.permissions.PermissionHandler;
import org.apdb4j.core.permissions.UserTypePermission;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.TableField;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static org.apdb4j.db.Tables.ACCOUNTS;

/**
 * This builder class makes managing queries with jOOQ easy and fluent.
 */
public class QueryBuilder {

    private Connection connection;
    private Object queryResult;

    /**
     * Defines the requiredPermission that allows the execution of the following query(ies).
     * @param attribute the attribute
     * @param userEmail the user's email address
     * @param forUserType the list that contains the required user types
     * @param read the required {@code read} value
     * @param write the required {@code write} value
     * @return {@link QueryBuilder} for fluent style
     * @throws AccessDeniedException when the requiredPermission objects are incompatible
     */
    public QueryBuilder definePermissions(final TableField<Record, ?> attribute,
                                          final String userEmail,
                                          final List<String> forUserType,
                                          final boolean read,
                                          final boolean write) throws AccessDeniedException {
        if (invalidAccess(attribute, userEmail, forUserType, read, write)) {
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

    private boolean invalidAccess(final @NonNull TableField<Record, ?> attribute,
                                  final @NonNull String userEmail,
                                  final List<String> requiredForUserType,
                                  final boolean requiredRead,
                                  final boolean requiredWrite) {
        // TODO: add support for "self"
        final String userType = new QueryBuilder()
                .createConnection()
                .queryAction(db -> db.select(ACCOUNTS.PERMISSIONTYPE)
                        .from(ACCOUNTS)
                        .where(ACCOUNTS.EMAIL.eq(userEmail))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValue(0, ACCOUNTS.PERMISSIONTYPE);
        final Attribute permissions = PermissionHandler.getPermissions(attribute, userType);
        final List<Permission> result = permissions.userTypePermissions().stream()
                .filter(userTypePermission -> userTypePermission.userType().equalsIgnoreCase(userType))
                .map(UserTypePermission::permissions)
                .flatMap(List::stream)
                .filter(permission -> containsAllUserTypes(permission.forUserType(), requiredForUserType))
                .toList();
        if (result.isEmpty()) {
            return true;
        }
        if (result.size() > 1) {
            throw new IllegalStateException("Found " + result.size() + " matches for: "
                    + attribute.getQualifiedName().unquotedName() + " " + userEmail);
        }
        return result.get(0).read() != requiredRead || result.get(0).write() != requiredWrite;
    }

    private boolean containsAllUserTypes(final List<String> actualForUserType, final List<String> requiredForUserType) {
        return actualForUserType.stream()
                .allMatch(actual -> requiredForUserType.stream()
                        .anyMatch(required -> required.equalsIgnoreCase(actual)));
    }

//    public static void main(String[] args) throws AccessDeniedException {
//        new QueryBuilder().definePermissions(ACCOUNTS.EMAIL, "mariorossi@gmail.com", List.of("self"), true, true);
//    }

}
