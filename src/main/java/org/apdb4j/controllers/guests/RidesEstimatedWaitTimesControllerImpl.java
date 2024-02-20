package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectFieldOrAsterisk;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.*;

/**
 * Implementation of a {@link RidesEstimatedWaitTimesController}.
 */
public class RidesEstimatedWaitTimesControllerImpl implements RidesEstimatedWaitTimesController {

    private static final List<Field<?>> ESTIMATED_WAIT_TIMES_OVERVIEW_FIELDS = List.of(PARK_SERVICES.NAME,
            RIDES.INTENSITY,
            RIDE_DETAILS.ESTIMATEDWAITTIME);
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
    @Override
    public Map<String, Supplier<List<Map<String, String>>>> getSortOptionsWithActions() {
        return Map.of("Estimated wait time (" + Order.ASCENDING.getName() + ")", () -> sortByEstimatedWaitTimes(Order.ASCENDING),
                "Estimated wait time (" + Order.DESCENDING.getName() + ")", () -> sortByEstimatedWaitTimes(Order.DESCENDING),
                "Name (" + Order.ASCENDING.getName() + ")", () -> sortByName(Order.ASCENDING),
                "Name (" + Order.DESCENDING.getName() + ")", () -> sortByName(Order.DESCENDING));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>> getFiltersWithValuesAndAction() {
        return Map.of("Intensity", new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(RIDES.INTENSITY)
                        .from(RIDES)
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(RIDES.INTENSITY).stream()
                .map(intensity -> new ImmutablePair<String, Supplier<List<Map<String, String>>>>(intensity, () ->
                        filterByIntensity(intensity)))
                .toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Map<String, String>> getRidesWithWaitTimes() {
        actualContent = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(ESTIMATED_WAIT_TIMES_OVERVIEW_FIELDS.toArray(new SelectFieldOrAsterisk[]{}))
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return formatActualContent();
    }

    private List<Map<String, String>> filterByIntensity(final String intensity) {
        actualContent = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(ESTIMATED_WAIT_TIMES_OVERVIEW_FIELDS.toArray(new SelectFieldOrAsterisk[]{}))
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .where(RIDES.INTENSITY.eq(intensity))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return formatActualContent();
    }

    private List<Map<String, String>> sortByName(final @NonNull Order order) {
        actualContent = actualContent.stream().sorted((ride1, ride2) -> switch (order) {
            case ASCENDING -> ride1.get(PARK_SERVICES.NAME).compareTo(ride2.get(PARK_SERVICES.NAME));
            case DESCENDING -> ride2.get(PARK_SERVICES.NAME).compareTo(ride1.get(PARK_SERVICES.NAME));
        }).toList();
        return formatActualContent();
    }

    private List<Map<String, String>> sortByEstimatedWaitTimes(final @NonNull Order order) {
        actualContent = actualContent.stream().sorted((ride1, ride2) -> switch (order) {
            case ASCENDING -> ride1.get(RIDE_DETAILS.ESTIMATEDWAITTIME).compareTo(ride2.get(RIDE_DETAILS.ESTIMATEDWAITTIME));
            case DESCENDING -> ride2.get(RIDE_DETAILS.ESTIMATEDWAITTIME).compareTo(ride1.get(RIDE_DETAILS.ESTIMATEDWAITTIME));
        }).toList();
        return formatActualContent();
    }

    private List<Map<String, String>> formatActualContent() {
        return actualContent.stream().map(ride -> Arrays.stream(ride.fields())
                        .map(field -> new ImmutablePair<>(field.getName(), ride.get(field).toString()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .toList();
    }
}
