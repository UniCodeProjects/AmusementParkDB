package org.apdb4j.core.managers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Result;
import org.jooq.Record;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;

import java.util.Objects;

/**
 * An SQL query manager for general purpose operations.
 */
public final class Manager {

    private Manager() {
    }

    /**
     * Performs the SQL query that retrieves all the tuples of the table with the provided name.
     * @param tableName the table on which the query is performed. If in the database does not exist any table
     *                  with the provided name, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return all the tuples of the table with the provided name.
     */
    public static @NonNull Result<Record> viewAllInfoFromTable(final @NonNull String tableName, final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select().from(tableName).fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    /**
     * Performs the SQL query that retrieves a projection on the provided attributes of the tuples of the given table.
     * @param tableName the table on which the query is performed. If in the database does not exist any table
     *                  with the provided name, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @param attributes the names of the attributes on which the projection is made. If at least one of these attributes'
     *                   names is not the name of an attribute of the provided table, the query will not be executed.
     * @return all the tuples of the table projected on the given attributes.
     */
    public static @NonNull Result<Record> viewPartialInfoFromTable(final @NonNull String tableName, final @NonNull String account,
                                                                   final @NonNull SelectFieldOrAsterisk... attributes) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(attributes).from(tableName).fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    /**
     * Removes from the provided table the tuple whose primary key has the provided values.
     * @param table the table.
     * @param account the account that is performing this operation. If the account has not the permission to do
     *                this operation, the query will not be executed.
     * @param pkValues the primary key values of the tuple to remove. In case the primary key is composed by multiple attributes,
     *                 the values must be passed in the same order as their related attributes show in the primary key
     *                 declaration.
     *                 Otherwise, the deletion will not be successful.
     * @return {@code true} if the deletion is successful, {@code false} otherwise.
     */
    public static boolean removeTupleFromDB(final @NonNull Table<Record> table, final @NonNull String account,
                                            final @NonNull Object... pkValues) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.execute(generateDeletionQuery(table, pkValues)))
                .closeConnection()
                .getResultAsInt() == 1;
    }

    private static String generateDeletionQuery(final @NonNull Table<Record> table, final @NonNull Object... pkValues) {
        final var pk = getPrimaryKey(table);
        if (pk.length != pkValues.length) {
            throw new IllegalArgumentException("Provided less or more values for the table primary key");
        }
        final var builder = new StringBuilder();
        final String concatString = "' and ";
        for (int i = 0; i < pk.length; i++) {
            builder.append(pk[i]).append(" = '").append(pkValues[i]).append(concatString);
            if (i == pk.length - 1) {
                builder.replace(builder.length() - concatString.length() + 1, builder.length(), "");
            }
        }
        return "delete from " + table.getName() + " where " + builder;
    }

    @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH",
            justification = "False positive. Check done with Objects.requireNonNull()")
    private static String[] getPrimaryKey(final Table<Record> table) {
        final var str = Objects.requireNonNull(table.getPrimaryKey()).toString();
        final var finalStr = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
        final var arr = finalStr.split(", ");
        for (var i = 0; i < arr.length; i++) {
            arr[i] = arr[i].replace("\"", ""); // NOPMD // unable to use StringBuilder
        }
        return arr;
    }

}
