package org.apdb4j.controllers.guests;

import java.util.List;
import java.util.Map;

/**
 * MVC controller for the "best park services screen" for users.
 */
public interface BestParkServicesController {

    /**
     * Retrieves the park services with the higher average rating.
     * @return the best park services according to their average rating.
     */
    List<Map<String, String>> getBestParkServices();
}
