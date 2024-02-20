package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.SelectFieldOrAsterisk;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.apdb4j.db.Tables.PARK_SERVICES;
import static org.apdb4j.db.Tables.RIDES;
import static org.apdb4j.db.Tables.RIDE_DETAILS;

/**
 * Abstract class that contains functionalities that are common to all the {@link ParkServiceController}s that deal
 * with rides.
 */
abstract class AbstractRideController extends AbstractParkServiceController {

    private final List<SelectFieldOrAsterisk> selectFields;

    /**
     * Default constructor. Sets the select fields with the provided ones.
     * @param selectFields the select fields that have to be used in the queries to the database.
     */
    AbstractRideController(final @NonNull SelectFieldOrAsterisk... selectFields) {
        this.selectFields = List.of(selectFields);
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
    protected List<SelectFieldOrAsterisk> getSelectFields() {
        return selectFields;
    }

    private List<Map<String, String>> filterByIntensity(final String intensity) {
        setActualContent(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(selectFields)
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .where(RIDES.INTENSITY.eq(intensity))
                        .fetch())
                .closeConnection()
                .getResultAsRecords());
        return formatActualContent();
    }
}
