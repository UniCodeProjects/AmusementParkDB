package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectFieldOrAsterisk;

import java.util.*;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.*;
import static org.apdb4j.db.Tables.RIDE_DETAILS;

/**
 * Implementation of a single info controller for rides.
 */
public class SingleRideInfoController implements SingleParkServiceInfoController {

    private List<Record> actualContent = new ArrayList<>();

    private static final List<Field<?>> ALL_INFO_FIELDS = List.of(RIDES.INTENSITY,
            RIDES.DURATION,
            RIDES.MAXSEATS,
            RIDES.MINHEIGHT,
            RIDES.MAXHEIGHT,
            RIDES.MINWEIGHT,
            RIDES.MAXWEIGHT,
            FACILITIES.OPENINGTIME,
            FACILITIES.CLOSINGTIME,
            PARK_SERVICES.TYPE,
            RIDE_DETAILS.STATUS);

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
        actualContent = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(ALL_INFO_FIELDS.toArray(new SelectFieldOrAsterisk[]{}))
                        .from(PARK_SERVICES)
                        .join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .join(RIDES).on(FACILITIES.FACILITYID.eq(RIDES.RIDEID))
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .where(PARK_SERVICES.NAME.eq(parkServiceName))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return formatActualContent().get(0);
    }

    private List<Map<String, String>> formatActualContent() {
        return actualContent.stream().map(ride -> Arrays.stream(ride.fields())
                        .map(field -> new ImmutablePair<>(field.getName(), ride.get(field).toString()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .toList();
    }
}
