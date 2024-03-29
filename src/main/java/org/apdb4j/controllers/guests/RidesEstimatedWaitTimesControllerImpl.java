package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apdb4j.db.Tables.*;

/**
 * Implementation of a {@link RidesEstimatedWaitTimesController}.
 */
public class RidesEstimatedWaitTimesControllerImpl extends AbstractRideController
        implements RidesEstimatedWaitTimesController {

    /**
     * Default constructor.
     */
    public RidesEstimatedWaitTimesControllerImpl() {
        super(PARK_SERVICES.NAME, PARK_SERVICES.AVGRATING, RIDES.INTENSITY, RIDE_DETAILS.ESTIMATEDWAITTIME);
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
    public Map<String, Supplier<List<Map<String, String>>>> getSortOptionsWithActions() {
        final Stream<Map.Entry<String, Supplier<List<Map<String, String>>>>> newEntriesStream = Stream.of(
                Map.entry("Estimated wait time (" + Order.ASCENDING.getName() + ")",
                        () -> sortByEstimatedWaitTimes(Order.ASCENDING)),
                Map.entry("Estimated wait time (" + Order.DESCENDING.getName() + ")",
                        () -> sortByEstimatedWaitTimes(Order.DESCENDING)));
        return Stream.concat(newEntriesStream, super.getSortOptionsWithActions().entrySet().stream())
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Map<String, String>> getRidesWithWaitTimes() {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(getSelectFields())
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords());
    }

    private List<Map<String, String>> sortByEstimatedWaitTimes(final @NonNull Order order) {
        return Formatter.format(new QueryBuilder().createConnection()
                .queryAction(db -> db.select(getSelectFields())
                        .from(PARK_SERVICES).join(RIDES).on(PARK_SERVICES.PARKSERVICEID.eq(RIDES.RIDEID))
                        .join(RIDE_DETAILS).on(RIDES.RIDEID.eq(RIDE_DETAILS.RIDEID))
                        .orderBy(
                                switch (order) {
                            case ASCENDING -> RIDE_DETAILS.ESTIMATEDWAITTIME.asc();
                            case DESCENDING -> RIDE_DETAILS.ESTIMATEDWAITTIME.desc();
                        })
                        .fetch())
                .closeConnection()
                .getResultAsRecords());
    }
}
