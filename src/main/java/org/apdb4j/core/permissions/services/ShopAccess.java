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
    @NonNull AccessType canAccessShopRevenue();

    /**
     * The access permission for the {@code Expenses} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessShopExpenses();

    /**
     * The access permission for the {@code Month} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessShopMonth();

    /**
     * The access permission for the {@code Year} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessShopYear();

}
