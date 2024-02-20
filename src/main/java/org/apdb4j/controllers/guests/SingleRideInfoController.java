package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import java.util.*;

import static org.apdb4j.db.Tables.*;

/**
 * Implementation of a single info controller for rides.
 */
public class SingleRideInfoController extends AbstractSingleParkServiceInfoController {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getAllParkServiceInfo(final @NonNull String parkServiceName) {
        return formatQueryResult(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(RIDES.INTENSITY,
                                RIDES.DURATION,
                                RIDES.MAXSEATS,
                                RIDES.MINHEIGHT,
                                RIDES.MAXHEIGHT,
                                RIDES.MINWEIGHT,
                                RIDES.MAXWEIGHT,
                                FACILITIES.OPENINGTIME,
                                FACILITIES.CLOSINGTIME,
                                PARK_SERVICES.TYPE,
                                RIDE_DETAILS.STATUS)
                        .from(PARK_SERVICES)
                        .join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .join(RIDES).on(FACILITIES.FACILITYID.eq(RIDES.RIDEID))
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords().get(0));
    }
}
