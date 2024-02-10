package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.TableItem;

import java.util.Collection;
import java.util.List;

/**
 * An administration controller specifically used for shops.
 */
public interface ShopController extends AdministrationController, Filterable {

    /**
     * Retrieves the table items filtered by a shop id.
     * @param shopID the shop id filter
     * @param <T> the type of the {@code TableItem}
     * @return a collection of table items
     * @throws org.jooq.exception.DataAccessException if query fails
     * @see TableItem
     */
    <T extends TableItem> Collection<T> getData(String shopID);

    /**
     * Returns the existing types present in the {@code PARK_SERVICES} table.
     * @return the existing types present in the {@code PARK_SERVICES} table.
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    List<String> getExistingTypes();

}
