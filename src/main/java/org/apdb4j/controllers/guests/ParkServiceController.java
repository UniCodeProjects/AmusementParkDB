package org.apdb4j.controllers.guests;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apdb4j.controllers.Controller;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;

import java.util.*;

import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * MVC controller of any park service.
 */
public interface ParkServiceController extends Controller {

    /**
     * Returns the names of the sorting options.
     * Subclasses that override this method have to do it only by
     * adding elements to the collection returned by this method.
     * @return the names of the sorting options.
     */
    default Collection<String> getSortFields() {
        return Set.of("Average rating", "Name");
    }

    /**
     * Orders the shown park services by average rating, following the provided order.
     * @param order the sorting order.
     * @return the park services ordered by their average rating, following the provided order.
     */
    List<Map<String, String>> sortByAverageRating(@NonNull Order order);

    /**
     * Orders the shown park services by name, following the provided order.
     * @param order the sorting order.
     * @return the park services ordered by their name, following the provided order.
     */
    List<Map<String, String>> sortByName(@NonNull Order order);

    // TODO: add link to RideController when implemented
    /**
     * Returns an overview of all the park services handled by this controller (e.g. in a RideController is returned
     * an overview of all the rides).
     * In this method the names of the attributes and their values are formatted for the view.
     * @return a partial set of information about all the park services handled by the controller.
     */
    Collection<Map<String, String>> getOverview();

    /**
     * Returns all the info about the park service with the provided name.
     * In this method the names of the attributes and their values are formatted for the view.
     * @param parkServiceName the name of the park service.
     * @return all the info about the park service with the provided name.
     */
    Map<String, String> getAllParkServiceInfo(@NonNull String parkServiceName);

    /**
     * Returns the description of the provided park service.
     * @param parkServiceName the park service.
     * @return the description of the provided park service.
     */
    static String getParkServiceDescription(final @NonNull String parkServiceName) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.DESCRIPTION)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords().getValue(0, PARK_SERVICES.DESCRIPTION);
    }

    /**
     * Returns all the names of the filters with the related values.
     * @return all the names of the filters with the related values.
     */
    Map<String, Collection<String>> getAllFiltersWithValues();

    /**
     * Filters the shown park services according to the values of {@code maxRating} and {@code ranged}.
     * @param maxRating if {@code ranged} is true, the value of this parameter is the maximum average rating allowed.
     *                  Otherwise, it is the exact average rating allowed.
     * @param ranged if {@code true}, are returned all the rides whose average rating is between the minimum
     *               rating and {@code maxRating}.
     *               If {@code false}, are returned all the rides whose average rating is equal to {@code maxRating}.
     * @return the park services that satisfy the specified average rating constraint.
     */
    Collection<Map<String, String>> filterByAverageRating(int maxRating, boolean ranged);

    /**
     * Returns the identifier of the park service with the provided name, if exists,
     * otherwise an {@link IllegalArgumentException will be thrown}.
     * @param parkServiceName the name of the park service.
     * @return the identifier of the park service with the provided name.
     */
    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH")
    static String getParkServiceID(final @NonNull String parkServiceName) {
        final Result<Record> parkServiceID = Objects.requireNonNull(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PARK_SERVICES.PARKSERVICEID)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords());
        if (parkServiceID.isEmpty()) {
            throw new IllegalArgumentException("There is no park service with the provided name");
        } else {
            return parkServiceID.getValue(0, PARK_SERVICES.PARKSERVICEID);
        }
    }
}
