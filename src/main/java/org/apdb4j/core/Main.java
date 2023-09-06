package org.apdb4j.core;

import org.apdb4j.core.permissions.Permission;
import org.apdb4j.core.permissions.PermissionDeniedException;
import org.apdb4j.util.QueryBuilder;
//import org.jooq.Record;
//import org.jooq.Result;

import static org.apdb4j.db.Tables.*;

/**
 * Main class.
 */
public final class Main {

    private Main() {
    }

    /**
     * Main method.
     * @param args main args.
     */
    public static void main(final String[] args) throws PermissionDeniedException {
        // DB connection sample.
        final var p = new Permission() {
        };
        final var builder = new QueryBuilder();
        builder.definePermissions(p, p)
                .createConnection()
                .queryAction(db -> db.insertInto(PRICE_LISTS).values(2015).execute())
                .closeConnection();

//        builder.definePermissions(p, p)
//                .createConnection()
//                .queryAction(db -> {
//                    final Result<Record> result = db
//                            .select()
//                            .from(PRICE_LISTS)
//                            .fetch();
//                    for (final Record r : result) {
//                        final var year = r.getValue(PRICE_LISTS.YEAR);
//                        System.out.println("YEAR: " + year);
//                    }
//                })
//                .closeConnection();
    }
}
