package org.apdb4j.core.permissions.tickets;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to price lists.
 */
public interface PriceListAccess extends Access {

    /**
     * The access permission for the {@code Year} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessPriceListYear();

}
