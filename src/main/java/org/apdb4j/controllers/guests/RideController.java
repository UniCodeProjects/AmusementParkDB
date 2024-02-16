package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.*;
import org.jooq.Record;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.*;

/**
 * MVC controller for rides.
 */
public class RideController implements ParkServiceController {

    private static final List<Field<?>> OVERVIEW_FIELDS = List.of(PARK_SERVICES.NAME, PARK_SERVICES.AVGRATING, RIDES.INTENSITY);
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
    private List<Record> actualContent = new ArrayList<>();

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
    // TODO: this method is the same for all ParkServiceController, if it is used the actualContent field. Put in a superclass.
    @Override
    public List<Map<String, String>> sortByAverageRating(final @NonNull Order order) {
        actualContent = actualContent.stream().sorted((ride1, ride2) -> switch (order) {
            case ASCENDING -> ride1.get(PARK_SERVICES.AVGRATING).compareTo(ride2.get(PARK_SERVICES.AVGRATING));
            case DESCENDING -> ride2.get(PARK_SERVICES.AVGRATING).compareTo(ride1.get(PARK_SERVICES.AVGRATING));
        }).toList();
        return formatActualContent();
    }

    /**
     * {@inheritDoc}
     */
    // TODO: same as above.
    @Override
    public List<Map<String, String>> sortByName(final @NonNull Order order) {
        actualContent = actualContent.stream().sorted((ride1, ride2) -> switch (order) {
            case ASCENDING -> ride1.get(PARK_SERVICES.NAME).compareTo(ride2.get(PARK_SERVICES.NAME));
            case DESCENDING -> ride2.get(PARK_SERVICES.NAME).compareTo(ride1.get(PARK_SERVICES.NAME));
        }).toList();
        return formatActualContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Map<String, String>> getOverview() {
        actualContent = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS.toArray(new SelectFieldOrAsterisk[]{}))
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return formatActualContent();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Collection<String>> getAllFiltersWithValues() {
        return Map.of("Type", new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(PARK_SERVICES.TYPE),
                "Intensity", new QueryBuilder().createConnection()
                        .queryAction(db -> db.selectDistinct(RIDES.INTENSITY)
                                .from(RIDES)
                                .fetch())
                        .closeConnection()
                        .getResultAsRecords()
                        .getValues(RIDES.INTENSITY));
    }

    /**
     * {@inheritDoc}
     */
    // TODO: re-calculates actualContent each time from scratch, without considering the previous filters.
    @Override
    public Collection<Map<String, String>> filterByAverageRating(final int maxRating, final boolean ranged) {
        actualContent = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS.toArray(new SelectFieldOrAsterisk[]{}))
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .where(ranged ? PARK_SERVICES.AVGRATING.le(BigDecimal.valueOf(maxRating))
                                : PARK_SERVICES.AVGRATING.eq(BigDecimal.valueOf(maxRating)))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return formatActualContent();
    }

    private List<Map<String, String>> formatActualContent() {
        return actualContent.stream().map(ride -> Arrays.stream(ride.fields())
                .map(field -> new ImmutablePair<>(field.getName(), ride.get(field).toString()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .toList();
    }
}
