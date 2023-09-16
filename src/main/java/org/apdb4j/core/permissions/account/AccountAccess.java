package org.apdb4j.core.permissions.account;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to accounts.
 */
public interface AccountAccess extends Access {

    /**
     * The access permission for the {@code Email} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessEmail();

    /**
     * The access permission for the {@code Username} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessUsername();

    /**
     * The access permission for the {@code Password} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessPassword();

}
