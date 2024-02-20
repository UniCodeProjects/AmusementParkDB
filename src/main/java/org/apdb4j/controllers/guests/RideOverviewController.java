package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.*;
import org.jooq.Record;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.apdb4j.db.Tables.*;

/**
 * MVC overview controller for rides.
 */
public class RideOverviewController implements ParkServiceOverviewController {

    private static final List<Field<?>> OVERVIEW_FIELDS = List.of(PARK_SERVICES.NAME, PARK_SERVICES.AVGRATING, RIDES.INTENSITY);
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
        return Map.of("Average rating (" + Order.ASCENDING.getName() + ")", () -> sortByAverageRating(Order.ASCENDING),
                "Average rating (" + Order.DESCENDING.getName() + ")", () -> sortByAverageRating(Order.DESCENDING),
                "Name (" + Order.ASCENDING.getName() + ")", () -> sortByName(Order.ASCENDING),
                "Name (" + Order.DESCENDING.getName() + ")", () -> sortByName(Order.DESCENDING));
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
    // TODO: re-calculates actualContent each time from scratch, without considering the previous filters.
    @Override
    public Map<String, Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>> getFiltersWithValuesAndAction() {
        return Map.of("Type", new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(PARK_SERVICES.TYPE).stream()
                .map(type -> new ImmutablePair<String, Supplier<List<Map<String, String>>>>(type, () -> filterByType(type)))
                .toList(),
                "Intensity", new QueryBuilder().createConnection()
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

    // TODO: this method is the same for all ParkServiceController, if it is used the actualContent field. Put in a superclass.
    private List<Map<String, String>> sortByAverageRating(final @NonNull Order order) {
        actualContent = actualContent.stream().sorted((ride1, ride2) -> switch (order) {
            case ASCENDING -> ride1.get(PARK_SERVICES.AVGRATING).compareTo(ride2.get(PARK_SERVICES.AVGRATING));
            case DESCENDING -> ride2.get(PARK_SERVICES.AVGRATING).compareTo(ride1.get(PARK_SERVICES.AVGRATING));
        }).toList();
        return formatActualContent();
    }

    // TODO: same as above.
    private List<Map<String, String>> sortByName(final @NonNull Order order) {
        actualContent = actualContent.stream().sorted((ride1, ride2) -> switch (order) {
            case ASCENDING -> ride1.get(PARK_SERVICES.NAME).compareTo(ride2.get(PARK_SERVICES.NAME));
            case DESCENDING -> ride2.get(PARK_SERVICES.NAME).compareTo(ride1.get(PARK_SERVICES.NAME));
        }).toList();
        return formatActualContent();
    }

    private List<Map<String, String>> filterByType(final String type) {
        actualContent = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS.toArray(new SelectFieldOrAsterisk[]{}))
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .where(PARK_SERVICES.TYPE.eq(type))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return formatActualContent();
    }

    private List<Map<String, String>> filterByIntensity(final String intensity) {
        actualContent = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS.toArray(new SelectFieldOrAsterisk[]{}))
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .where(RIDES.INTENSITY.eq(intensity))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return formatActualContent();
    }
}
