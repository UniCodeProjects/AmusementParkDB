package org.apdb4j.controllers.guests;

import java.util.Collection;
import java.util.Map;

/**
 * MVC controller that provides the information needed by the screen that shows
 * all rides' estimated wait times.
 */
public interface RidesEstimatedWaitTimesController extends ParkServiceController {

    /**
     * Retrieves all the rides names with their estimated wait times and other relevant information.
     * @return all the rides with the information needed by the screen that shows the park's rides
     *         with their estimated wait times.
     */
    Collection<Map<String, String>> getRidesWithWaitTimes();
}
