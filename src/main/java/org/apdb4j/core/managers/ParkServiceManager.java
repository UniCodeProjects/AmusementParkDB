package org.apdb4j.core.managers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
     * @return {@code true} on successful tuple update
     */
    public static boolean changeName(final @NonNull String parkServiceID, final @NonNull String newName) {
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
     * @return {@code true} on successful tuple update
     */
    public static boolean editAverageRating(final @NonNull String parkServiceID, final double newAverageRating) {
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
     * @return {@code true} on successful tuple update
     */
    public static boolean incrementReviews(final @NonNull String parkServiceID) {
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
     * @return {@code true} on successful tuple update
     */
    public static boolean changeType(final @NonNull String parkServiceID, final @NonNull String newType) {
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
     * @return {@code true} on successful tuple update
     */
    public static boolean addDescription(final @NonNull String parkServiceID,
                                         final @NonNull String description) {
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
     * @return the park's best services.
     */
    public static @NonNull List<Record> getBestParkServices() {
        return DB.createConnection()
                .queryAction(db -> db.select()
                        .from(PARK_SERVICES)
                        .orderBy(PARK_SERVICES.AVGRATING.desc())
                        .limit(5)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    /**
     * Returns the description of the provided park service.
     * If there is no park service with the provided name, an {@link IllegalArgumentException} is thrown.
     * @param parkServiceName the park service.
     * @return the description of the provided park service.
     */
    public static String getParkServiceDescription(final @NonNull String parkServiceName) {
        final boolean isParkServiceName = DB.createConnection()
                .queryAction(db -> db.selectCount()
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetchOne(0, int.class))
                .closeConnection()
                .getResultAsInt() == 1;
        if (!isParkServiceName) {
            throw new IllegalArgumentException(parkServiceName + " is not a valid park service name");
        }
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.DESCRIPTION)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords().getValue(0, PARK_SERVICES.DESCRIPTION);
    }

    /**
     * Returns the identifier of the park service with the provided name, if exists,
     * otherwise an {@link IllegalArgumentException} will be thrown.
     * @param parkServiceName the name of the park service.
     * @return the identifier of the park service with the provided name.
     */
    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH")
    public static String getParkServiceID(final @NonNull String parkServiceName) {
        final Result<Record> parkServiceID = Objects.requireNonNull(DB.createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.PARKSERVICEID)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords());
        if (parkServiceID.isEmpty()) {
            throw new IllegalArgumentException(parkServiceName + " is not a valid park service name");
        } else {
            return parkServiceID.getValue(0, PARK_SERVICES.PARKSERVICEID);
        }
    }
}
