package org.apdb4j.core.permissions.facilities;

/**
 * The permissions related to shops.
 */
public interface ShopAccess extends FacilityAccess {

    /**
     * The access permission for the {@code Revenue} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessShopRevenue();

    /**
     * The access permission for the {@code Expenses} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessShopExpenses();

    /**
     * The access permission for the {@code Month} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessShopMonth();

    /**
     * The access permission for the {@code Year} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessShopYear();

}
