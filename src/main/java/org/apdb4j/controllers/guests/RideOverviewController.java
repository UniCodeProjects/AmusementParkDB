package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.util.QueryBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apdb4j.db.Tables.*;

/**
 * MVC overview controller for rides.
 */
public class RideOverviewController extends AbstractRideController implements ParkServiceOverviewController {

    /**
     * Default constructor.
     */
    public RideOverviewController() {
        super(PARK_SERVICES.NAME, PARK_SERVICES.AVGRATING, RIDES.INTENSITY);
    }

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
    public Collection<Map<String, String>> getOverview() {
        setActualContent(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(getSelectFields())
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords());
        return formatActualContent();
    }

    /**
     * {@inheritDoc}
     */
    // TODO: re-calculates actualContent each time from scratch, without considering the previous filters.
    @Override
    public Map<String, Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>> getFiltersWithValuesAndAction() {
        return Stream.concat(Stream
                                .<Map.Entry<String, Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>>>of(
                Map.entry("Type", new QueryBuilder().createConnection()
                        .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                                .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                                .fetch())
                        .closeConnection()
                        .getResultAsRecords()
                        .getValues(PARK_SERVICES.TYPE).stream()
                        .map(type -> new ImmutablePair<String, Supplier<List<Map<String, String>>>>(type, () ->
                                filterByType(type)))
                        .toList())),
                super.getFiltersWithValuesAndAction().entrySet().stream())
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * {@inheritDoc}
     */
    // TODO: re-calculates actualContent each time from scratch, without considering the previous filters.
    @Override
    public Collection<Map<String, String>> filterByAverageRating(final int maxRating, final boolean ranged) {
        setActualContent(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(getSelectFields())
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .where(ranged ? PARK_SERVICES.AVGRATING.le(BigDecimal.valueOf(maxRating))
                                : PARK_SERVICES.AVGRATING.eq(BigDecimal.valueOf(maxRating)))
                        .fetch())
                .closeConnection()
                .getResultAsRecords());
        return formatActualContent();
    }

    private List<Map<String, String>> filterByType(final String type) {
        setActualContent(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(getSelectFields())
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .where(PARK_SERVICES.TYPE.eq(type))
                        .fetch())
                .closeConnection()
                .getResultAsRecords());
        return formatActualContent();
    }
}
