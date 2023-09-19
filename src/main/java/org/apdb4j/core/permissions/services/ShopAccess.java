package org.apdb4j.core.permissions.services;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to shops.
 */
public interface ShopAccess extends FacilityAccess {

    /**
     * The access permission for the {@code Revenue} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfShopRevenue();

    /**
     * The access permission for the {@code Expenses} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfShopExpenses();

    /**
     * The access permission for the {@code Month} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfShopMonth();

    /**
     * The access permission for the {@code Year} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfShopYear();

}
