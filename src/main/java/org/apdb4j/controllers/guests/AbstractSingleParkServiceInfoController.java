package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.core.managers.ParkServiceManager;
import org.apdb4j.core.managers.PictureManager;
import org.jooq.Record;

import java.util.List;
import java.util.Map;

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
     * {@inheritDoc}
     */
    @Override
    public List<String> getPhotosPath(final @NonNull String parkServiceName) {
        return PictureManager.getPictures(parkServiceName);
    }

    /**
     * Formats the query result into a {@code Map}, that can be easily handled by the view component of the
     * MVC architecture.
     * @param parkServiceInfo the query result that contains all the required information about a park service.
     * @return all the information about the park service well-formatted for the view component.
     */
    protected Map<String, String> formatQueryResult(final @NonNull Record parkServiceInfo) {
        return Formatter.format(List.of(parkServiceInfo)).get(0);
    }
}
