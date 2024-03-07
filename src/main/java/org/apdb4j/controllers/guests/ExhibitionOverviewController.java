package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.util.QueryBuilder;
import org.jooq.SelectFieldOrAsterisk;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * MVC overview controller for exhibitions.
 */
public class ExhibitionOverviewController extends AbstractParkServiceController implements ParkServiceOverviewController {

    private static final List<SelectFieldOrAsterisk> OVERVIEW_FIELDS = List.of(PARK_SERVICES.NAME,
            PARK_SERVICES.AVGRATING,
            PARK_SERVICES.TYPE);

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
    protected final List<Map<String, String>> sortByName(final @NonNull Order order) {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.ISEXHIBITION.isTrue())
                        .orderBy(
                                switch (order) {
                                    case ASCENDING -> PARK_SERVICES.NAME.asc();
                                    case DESCENDING -> PARK_SERVICES.NAME.desc();
                                })
                        .fetch())
                .closeConnection().getResultAsRecords());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final List<Map<String, String>> sortByAverageRating(final @NonNull Order order) {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.ISEXHIBITION.isTrue())
                        .orderBy(
                                switch (order) {
                                    case ASCENDING -> PARK_SERVICES.AVGRATING.asc();
                                    case DESCENDING -> PARK_SERVICES.AVGRATING.desc();
                                })
                        .fetch())
                .closeConnection().getResultAsRecords());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>> getFiltersWithValuesAndAction() {
        return Map.of("Type", new QueryBuilder().createConnection()
                .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.ISEXHIBITION.isTrue())
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(PARK_SERVICES.TYPE).stream()
                .map(type -> new ImmutablePair<String, Supplier<List<Map<String, String>>>>(type, () -> filterByType(type)))
                .toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Map<String, String>> getOverview() {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.ISEXHIBITION.isTrue())
                        .fetch())
                .closeConnection().getResultAsRecords());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Map<String, String>> filterByAverageRating(final int maxRating, final boolean ranged) {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES)
                        .where(ranged ? PARK_SERVICES.AVGRATING.le(BigDecimal.valueOf(maxRating))
                                : PARK_SERVICES.AVGRATING.eq(BigDecimal.valueOf(maxRating)))
                        .and(PARK_SERVICES.ISEXHIBITION.isTrue())
                        .fetch())
                .closeConnection().getResultAsRecords());
    }

    private List<Map<String, String>> filterByType(final @NonNull String type) {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.TYPE.eq(type))
                        .and(PARK_SERVICES.ISEXHIBITION.isTrue())
                        .fetch())
                .closeConnection().getResultAsRecords());
    }
}
