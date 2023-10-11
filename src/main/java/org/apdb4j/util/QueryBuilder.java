package org.apdb4j.util;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.core.permissions.AccessSetting;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.uid.AppPermissionUID;
import org.apdb4j.core.permissions.uid.DBPermissionUID;
import org.apdb4j.core.permissions.uid.PackageInterfaceSequence;
import org.apdb4j.core.permissions.uid.ReturnSequence;
import org.apdb4j.core.permissions.uid.Sequence;
import org.apdb4j.core.permissions.uid.UID;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * This builder class makes managing queries with jOOQ easy and fluent.
 */
public class QueryBuilder {

    private Connection connection;
    private Object queryResult;

    /**
     * Defines the access that allows the execution of the following query(ies).
     * @param requiredAccess the required permission
     * @param requiredTable the required table
     * @param recordTableField the required table field
     * @param values the required access setting values
     * @param actualAccountEmail the account's email used to check its permissions from the database
     * @return {@link QueryBuilder} for fluent style
     * @throws AccessDeniedException when the access objects are incompatible
     */
    public QueryBuilder definePermissions(final @NonNull Access requiredAccess,
                                          final @NonNull TableField<Record, ?> recordTableField,
                                          final @NonNull AccessSetting values,
                                          final @NonNull String actualAccountEmail) throws AccessDeniedException {
        if (invalidAccess(requiredAccess, recordTableField, values, actualAccountEmail)) {
            throw new AccessDeniedException();
        }
        return this;
    }

    /**
     * Defines the access that allows the execution of the following query(ies).
     * @param required the required permissions
     * @param actualAccountEmail the account's email used to check its permissions from the database
     * @return {@link QueryBuilder} for fluent style
     * @throws AccessDeniedException when the access objects are incompatible
     */
    public QueryBuilder definePermissions(final @NonNull Set<Access> required,
                                          final @NonNull String actualAccountEmail) throws AccessDeniedException {
        if (invalidAccess(required, actualAccountEmail)) {
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

    private boolean invalidAccess(final @NonNull Access requiredAccess,
                                  final @NonNull TableField<Record, ?> recordTableField,
                                  final @NonNull AccessSetting values,
                                  final @NonNull String actualAccountEmail) {
        final UID uidFromDb;
        try {
            uidFromDb = new DBPermissionUID(actualAccountEmail).getUid();
        } catch (final NoSuchElementException e) {
            System.out.println("Catching...");
            return true;
        }
        if (Objects.equals(new AppPermissionUID(requiredAccess).getUid(), uidFromDb)) {
            System.out.println("UID !=");
            return false;
        }
        // TODO: null pointer from getInterface.
        final var interfaceFromHash = ((PackageInterfaceSequence) PackageInterfaceSequence.getFromHash(uidFromDb.uid())).getInterface();
        if (interfaceFromHash.equals(requiredAccess.getClass())) {
            return false;
        }
        final ReturnSequence returnSequenceFromHash = (ReturnSequence) ReturnSequence.getFromHash(uidFromDb.uid());
        final Optional<TableField<Record, ?>> attributeFromHash = returnSequenceFromHash.getAttribute();
        if (attributeFromHash.isPresent() && attributeFromHash.get().equals(recordTableField)
                && values.getReadAccess().equals(getReadAccessFromDb(uidFromDb, returnSequenceFromHash))
                && values.getWriteAccess().equals(getWriteAccessFromDb(uidFromDb, returnSequenceFromHash))) {
            return false;
        }
        if (attributeFromHash.isEmpty()
                && values.getReadAccess().equals(getReadAccessFromDb(uidFromDb, returnSequenceFromHash))
                && values.getWriteAccess().equals(getWriteAccessFromDb(uidFromDb, returnSequenceFromHash))) {
            return false;
        }
        return true;
    }

    private Pair<AccessType.Read, Set<Class<? extends Access>>> getReadAccessFromDb(final UID uid, final Sequence returnSequence) {
        final AccessType.Read readFromHash = ((ReturnSequence) returnSequence).getRead();
        final Set<Class<? extends Access>> readTargetsFromHash = ((ReturnSequence) ReturnSequence.getFromHash(uid.uid())).getReadTargets();
        return Pair.of(readFromHash, readTargetsFromHash);
    }

    private Pair<AccessType.Write, Set<Class<? extends Access>>> getWriteAccessFromDb(final UID uid, final Sequence returnSequence) {
        final AccessType.Write writeFromHash = ((ReturnSequence) returnSequence).getWrite();
        final Set<Class<? extends Access>> writeTargetsFromHash = ((ReturnSequence) ReturnSequence.getFromHash(uid.uid())).getWriteTargets();
        return Pair.of(writeFromHash, writeTargetsFromHash);
    }

    private boolean invalidAccess(final @NonNull Collection<Access> required, final @NonNull String actual) {
        return required.stream()
                .noneMatch(req -> Objects.equals(new AppPermissionUID(req).getUid(), new DBPermissionUID(actual).getUid()));
    }

}
