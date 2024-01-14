package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;

import java.util.List;

/**
 * An administration controller specifically used for rides.
 */
public interface RideController extends AdministrationController, Filterable {

    /**
     * Returns the {@code PARK_SERVICES} types that are present in the DB.
     * @return the {@code PARK_SERVICES} types that are present in the DB
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    List<String> getExistingTypes();

    /**
     * Returns the ride intensities that are present in the DB.
     * @return the ride intensities that are present in the DB
      @throws org.jooq.exception.DataAccessException if query fails
     */
    List<String> getExistingIntensities();

}
