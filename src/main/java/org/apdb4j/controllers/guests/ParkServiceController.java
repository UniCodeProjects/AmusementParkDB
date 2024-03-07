package org.apdb4j.controllers.guests;

import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.controllers.Controller;

import java.util.*;
import java.util.function.Supplier;

/**
 * MVC controller of any park service.
 */
public interface ParkServiceController extends Controller {

    /**
     * Returns the names of the sorting options with the related action.
     * @return the names of the sorting options with the related action.
     */
    Map<String, Supplier<List<Map<String, String>>>> getSortOptionsWithActions();

    /**
     * Returns all the names of the filters with the related values and the action that should be performed when the user
     * wants to apply that filter with that value.
     * @return all the names of the filters with the related values and action for each value.
     */
    Map<String, Collection<? extends Pair<String, Supplier<List<Map<String, String>>>>>> getFiltersWithValuesAndAction();
}
