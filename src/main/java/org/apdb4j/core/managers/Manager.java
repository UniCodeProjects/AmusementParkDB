package org.apdb4j.core.managers;

import lombok.NonNull;
import org.jooq.Result;
import org.jooq.Record;

/**
 * An SQL query manager for general purpose operations.
 */
public class Manager {

    /**
     * Performs the SQL query that retrieves all the tuples of the table with the provided name.
     * @param tableName the table on which the query is performed. If in the database does not exist any table
     *                  with the provided name, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return all the tuples of the table with the provided name.
     */
    static @NonNull Result<Record> viewAllInfoFromTable(@NonNull String tableName, @NonNull String account) {
        throw new UnsupportedOperationException("Not implemented yet");
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
    static @NonNull Result<Record> viewPartialInfoFromTable(@NonNull String tableName, @NonNull String account,
                                                     @NonNull String... attributes) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
