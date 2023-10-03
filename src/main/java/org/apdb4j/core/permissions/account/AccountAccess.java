package org.apdb4j.core.permissions.account;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSettings;

/**
 * The access related to accounts.
 */
public interface AccountAccess extends Access {

    /**
     * The access permission for the {@code Email} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfAccountEmail();

    /**
     * The access permission for the {@code Username} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfAccountUsername();

    /**
     * The access permission for the {@code Password} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfAccountPassword();

}
