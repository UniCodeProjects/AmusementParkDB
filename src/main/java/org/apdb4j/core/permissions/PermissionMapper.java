package org.apdb4j.core.permissions;

import org.apdb4j.util.QueryBuilder;

import java.util.List;

/**
 * Maps an Access class instance to an actual database tuple.
 * @see Access
 */
public final class PermissionMapper {

    private PermissionMapper() {
    }

    /**
     * Inserts a tuple in the database by using
     * the provided access object as data source.
     * @param source the access object
     */
    public static void insertInDB(final Access source) {
        final List<AccessType> returnValues = ((AbstractPermission) source).getReturnedAccessTypes().get();
        final var queryResult = new QueryBuilder()
                .createConnection()
                .queryAction(db -> {

                    return -1;
                })
                .closeConnection()
                .getResultAsInt();
    }

}
