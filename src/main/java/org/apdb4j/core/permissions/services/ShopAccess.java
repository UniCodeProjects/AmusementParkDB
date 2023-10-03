package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessSettings;

/**
 * The access related to shops.
 */
public interface ShopAccess extends FacilityAccess {

    /**
     * The access permission for the {@code Revenue} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfShopRevenue();

    /**
     * The access permission for the {@code Expenses} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfShopExpenses();

    /**
     * The access permission for the {@code Month} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfShopMonth();

    /**
     * The access permission for the {@code Year} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfShopYear();

}
