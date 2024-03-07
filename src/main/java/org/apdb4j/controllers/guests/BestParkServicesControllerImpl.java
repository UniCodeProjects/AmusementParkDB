package org.apdb4j.controllers.guests;

import org.apdb4j.core.managers.ParkServiceManager;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link BestParkServicesController}.
 */
public class BestParkServicesControllerImpl implements BestParkServicesController {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, String>> getBestParkServices() {
        return Formatter.format(ParkServiceManager.getBestParkServices());
    }
}
