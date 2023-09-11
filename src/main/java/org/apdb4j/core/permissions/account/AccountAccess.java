package org.apdb4j.core.permissions.account;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to accounts.
 */
public interface AccountAccess extends Access {

    /**
     * The access permission for the {@code Email} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessEmail();

    /**
     * The access permission for the {@code Username} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessUsername();

    /**
     * The access permission for the {@code Password} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessPassword();

}
