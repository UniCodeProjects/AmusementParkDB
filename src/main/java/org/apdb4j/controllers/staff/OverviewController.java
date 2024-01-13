package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Controller;

import java.time.LocalTime;

/**
 * A simple read-only controller for the overview tab.
 */
public interface OverviewController extends Controller {

    /**
     * Returns the park name.
     * @return the park name
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    String getParkName();

    /**
     * Returns the park opening time.
     * @return the park opening time
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    LocalTime getOpeningTime();

    /**
     * Returns the park closing time.
     * @return the park closing time
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    LocalTime getClosingTime();

    /**
     * Returns the park admin.
     * @return the park admin
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    String getAdministrator();

    /**
     * Returns the park attractions amount.
     * @return the park attractions amount
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    int getAttractionsAmount();

    /**
     * Returns the park shops amount.
     * @return the park shops amount
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    int getShopsAmount();

    /**
     * Returns the park employees amount.
     * @return the park employees amount
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    int getEmployeesAmount();

}
