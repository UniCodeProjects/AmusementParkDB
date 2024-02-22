package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apdb4j.core.managers.ParkServiceManager;
import org.jooq.Record;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Abstract class for {@link SingleParkServiceInfoController}s.
 */
abstract class AbstractSingleParkServiceInfoController implements SingleParkServiceInfoController {

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getParkServiceDescription(final @NonNull String parkServiceName) {
        return ParkServiceManager.getParkServiceDescription(parkServiceName);
    }

    /**
     * Formats the query result into a {@code Map}, that can be easily handled by the view component of the
     * MVC architecture.
     * @param parkServiceInfo the query result that contains all the required information about a park service.
     * @return all the information about the park service well-formatted for the view component.
     */
    protected Map<String, String> formatQueryResult(final @NonNull Record parkServiceInfo) {
        return Arrays.stream(parkServiceInfo.fields())
                .map(field -> new ImmutablePair<>(field.getName(), parkServiceInfo.get(field).toString()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
