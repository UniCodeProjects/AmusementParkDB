package org.apdb4j.controllers.guests;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.controllers.Controller;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Record;
import org.jooq.Result;

import java.util.*;
import java.util.function.Supplier;

import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * MVC controller of any park service.
 */
public interface ParkServiceController extends Controller {

    /**
     * Returns the names of the sorting options with the related action.
     * @return the names of the sorting options with the related action.
     */
    Map<String, Supplier<List<Map<String, String>>>> getSortOptionsWithActions();

    /**
     * Returns all the names of the filters with the related values and the action that should be performed when the user
     * wants to apply that filter with that value.
     * @return all the names of the filters with the related values and action for each value.
     */
    Map<String, Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>> getFiltersWithValuesAndAction();

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
