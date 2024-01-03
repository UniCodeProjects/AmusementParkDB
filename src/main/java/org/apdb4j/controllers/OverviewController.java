package org.apdb4j.controllers;

import java.time.LocalTime;

/**
 * A simple read-only controller for the overview tab.
 */
public interface OverviewController extends Controller {

    /**
     * Returns the park name.
     * @return the park name
     */
    String getParkName();

    /**
     * Returns the park opening time.
     * @return the park opening time
     */
    LocalTime getOpeningTime();

    /**
     * Returns the park closing time.
     * @return the park closing time
     */
    LocalTime getClosingTime();

    /**
     * Returns the park admin.
     * @return the park admin
     */
    String getAdministrator();

    /**
     * Returns the park attractions amount.
     * @return the park attractions amount
     */
    int getAttractionsAmount();

    /**
     * Returns the park shops amount.
     * @return the park shops amount
     */
    int getShopsAmount();

    /**
     * Returns the park employees amount.
     * @return the park employees amount
     */
    int getEmployeesAmount();

}
