package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;

import java.math.BigDecimal;
import java.util.List;

import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.ParkServices} table.
 */
public final class ParkServiceManager {

    private static final QueryBuilder DB = new QueryBuilder();

    private ParkServiceManager() {
    }

    /**
     * Performs the SQL query that changes the name of the given park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newName the new name of the provided park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean changeName(final @NonNull String parkServiceID, final @NonNull String newName,
                                     final @NonNull String account) {
        final int updatedTuples = DB.createConnection()
                .queryAction(db -> db.update(PARK_SERVICES)
                        .set(PARK_SERVICES.NAME, newName)
                        .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return updatedTuples == 1;
    }

    /**
     * Performs the SQL query that modifies the average rating of the provided park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newAverageRating the new average rating of the given park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean editAverageRating(final @NonNull String parkServiceID, final double newAverageRating,
                                            final @NonNull String account) {
        final int updatedTuples = DB.createConnection()
                .queryAction(db -> db.update(PARK_SERVICES)
                        .set(PARK_SERVICES.AVGRATING, BigDecimal.valueOf(newAverageRating))
                        .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return updatedTuples == 1;
    }

    /**
     * Performs the SQL query that increments of one unit the number of reviews for the provided park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean incrementReviews(final @NonNull String parkServiceID, final @NonNull String account) {
        final int beforeNum = DB.createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NUMREVIEWS)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
        final int updatedTuples = DB.createConnection()
                .queryAction(db -> db.update(PARK_SERVICES)
                        .set(PARK_SERVICES.NUMREVIEWS, PARK_SERVICES.NUMREVIEWS.add(1))
                        .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        final int afterNum = DB.createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.NUMREVIEWS)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt();
        return updatedTuples == 1 && afterNum - beforeNum == 1;
    }

    /**
     * Performs the SQL query that changes the type of the given park service.
     * @param parkServiceID the park service identifier. If the value of this parameter is not the identifier of a
     *                      park service, the query will not be executed.
     * @param newType the new type of the provided park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean changeType(final @NonNull String parkServiceID, final @NonNull String newType,
                                     final @NonNull String account) {
        final int updatedTuples = DB.createConnection()
                .queryAction(db -> db.update(PARK_SERVICES)
                        .set(PARK_SERVICES.TYPE, newType)
                        .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return updatedTuples == 1;
    }

    /**
     * Performs the SQL query that adds a description for the provided park service. If the park service
     * already has a description, this one will be overwritten by the given one.
     * @param parkServiceID the park service. If the value of this parameter is not the identifier of a park service,
     *                      the query will not be executed.
     * @param description the description to add for the provided park service.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} on successful tuple update
     */
    public static boolean addDescription(final @NonNull String parkServiceID,
                                         final @NonNull String description,
                                         final @NonNull String account) {
        final int updatedTuples = DB.createConnection()
                .queryAction(db -> db.update(PARK_SERVICES)
                        .set(PARK_SERVICES.DESCRIPTION, description)
                        .where(PARK_SERVICES.PARKSERVICEID.eq(parkServiceID))
                        .execute())
                .closeConnection()
                .getResultAsInt();
        return updatedTuples == 1;
    }

    /**
     * Performs the SQL query that retrieves the park's best services according to their average rating.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return the park's best services.
     */
    public static @NonNull List<Record> getBestParkServices(final @NonNull String account) {
        return DB.createConnection()
                .queryAction(db -> db.select()
                        .from(PARK_SERVICES)
                        .orderBy(PARK_SERVICES.AVGRATING.desc())
                        .limit(5)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

}
