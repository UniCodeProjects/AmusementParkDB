package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Result;
import org.jooq.Record;
import org.jooq.SelectFieldOrAsterisk;

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

}
