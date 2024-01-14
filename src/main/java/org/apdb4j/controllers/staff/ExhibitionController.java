package org.apdb4j.controllers.staff;

import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.ExhibitionTableItem;

import java.util.Collection;
import java.util.List;

/**
 * An administration controller specifically used for exhibitions.
 */
public interface ExhibitionController extends AdministrationController, Filterable {

    /**
     * Plans an exhibition by defining values for the {@code EXHIBITION_DETAILS} table.
     * @param exhibition the exhibition table item
     * @param <T> the {@code ExhibitionTableItem} type
     * @return the table item
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    <T extends ExhibitionTableItem> T planExhibition(T exhibition);

    /**
     * Returns the average spectators by park service type.
     * @return a collection of pairs of [type - average spectators]
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    Collection<Pair<String, Integer>> getAverageSpectatorsByType();

    /**
     * Returns the percentage of sold out exhibitions.
     * @return the percentage of sold out exhibitions
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    double getSoldOutExhibitionPercentage();

    /**
     * Returns the planned exhibitions.
     * @param <T> the {@code ExhibitionTableItem} type
     * @return a collection of {@code ExhibitionTableItem}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    <T extends ExhibitionTableItem> Collection<T> viewPlannedExhibitions();

    /**
     * Returns the {@code PARK_SERVICES} types that are present in the DB.
     * @return the {@code PARK_SERVICES} types that are present in the DB
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    List<String> getExistingTypes();

}
