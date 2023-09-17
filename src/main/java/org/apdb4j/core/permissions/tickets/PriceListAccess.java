package org.apdb4j.core.permissions.tickets;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to price lists.
 */
public interface PriceListAccess extends Access {

    /**
     * The access permission for the {@code Year} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessPriceListYear();

}
