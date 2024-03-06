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

import static org.apdb4j.db.Tables.FACILITIES;
import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * MVC overview controller for shops.
 */
public class ShopOverviewController extends AbstractParkServiceController implements ParkServiceOverviewController {

    private static final List<SelectFieldOrAsterisk> OVERVIEW_FIELDS = List.of(PARK_SERVICES.NAME,
            PARK_SERVICES.AVGRATING,
            PARK_SERVICES.TYPE);
    private static final String RESTAURANT_TYPE = "Restaurant";
    private final boolean isRestaurant;

    /**
     * Default constructor. It configures the controller to show only shops' overview, without considering restaurants.
     */
    public ShopOverviewController() {
        this(false);
    }

    /**
     * Creates a new instance of this class defining whether
     * it will refer to restaurants or to the shops that are not restaurants.
     * @param isRestaurant {@code true} if the new instance should refer to restaurants, meaning that it will manage only
     *                                 the shops that are restaurants. Otherwise, if this parameter is set to {@code false},
     *                                 the behavior of this constructor will be the same of
     *                                 {@link ShopOverviewController#ShopOverviewController()}.
     * @see ShopOverviewController#ShopOverviewController()
     */
    protected ShopOverviewController(final boolean isRestaurant) {
        this.isRestaurant = isRestaurant;
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
    protected List<Map<String, String>> sortByName(final @NonNull Order order) {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES).join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .where(FACILITIES.ISSHOP.isTrue())
                        .and(isRestaurant ? PARK_SERVICES.TYPE.containsIgnoreCase(RESTAURANT_TYPE)
                                : PARK_SERVICES.TYPE.notContainsIgnoreCase(RESTAURANT_TYPE))
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
    // CPD-OFF
    @Override
    protected List<Map<String, String>> sortByAverageRating(final @NonNull Order order) {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES).join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .where(FACILITIES.ISSHOP.isTrue())
                        .and(isRestaurant ? PARK_SERVICES.TYPE.containsIgnoreCase(RESTAURANT_TYPE)
                                : PARK_SERVICES.TYPE.notContainsIgnoreCase(RESTAURANT_TYPE))
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
                        .from(PARK_SERVICES).join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .where(FACILITIES.ISSHOP.isTrue())
                        .and(isRestaurant ? PARK_SERVICES.TYPE.containsIgnoreCase(RESTAURANT_TYPE)
                                : PARK_SERVICES.TYPE.notContainsIgnoreCase(RESTAURANT_TYPE))
                        .fetch())
                .closeConnection()
                .getResultAsRecords()
                .getValues(PARK_SERVICES.TYPE)
                .stream()
                .map(type -> new ImmutablePair<String, Supplier<List<Map<String, String>>>>(type, () -> filterByType(type)))
                .toList());
    }
    // CPD-ON

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Map<String, String>> getOverview() {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES).join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .where(FACILITIES.ISSHOP.isTrue())
                        .and(isRestaurant ? PARK_SERVICES.TYPE.containsIgnoreCase(RESTAURANT_TYPE)
                                : PARK_SERVICES.TYPE.notContainsIgnoreCase(RESTAURANT_TYPE))
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
                        .from(PARK_SERVICES).join(FACILITIES).on(PARK_SERVICES.PARKSERVICEID.eq(FACILITIES.FACILITYID))
                        .where(FACILITIES.ISSHOP.isTrue())
                        .and(ranged ? PARK_SERVICES.AVGRATING.le(BigDecimal.valueOf(maxRating))
                                : PARK_SERVICES.AVGRATING.eq(BigDecimal.valueOf(maxRating)))
                        .and(isRestaurant ? PARK_SERVICES.TYPE.containsIgnoreCase(RESTAURANT_TYPE)
                                : PARK_SERVICES.TYPE.notContainsIgnoreCase(RESTAURANT_TYPE))
                        .fetch())
                .closeConnection().getResultAsRecords());
    }

    private List<Map<String, String>> filterByType(final @NonNull String type) {
        // There is no check on FACILITIES.ISSHOP, because the types that will be used in this method are only shop types.
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(OVERVIEW_FIELDS)
                        .from(PARK_SERVICES)
                        .where(PARK_SERVICES.TYPE.eq(type))
                        .fetch())
                .closeConnection().getResultAsRecords());
    }
}
