package org.apdb4j.core.permissions.people;

import org.apdb4j.core.permissions.Permission;

/**
 * The permissions related to people.
 */
public interface PersonAccess extends Permission {

    /**
     * The access permission for the {@code PersonID} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessPersonID();

    /**
     * The access permission for the {@code Name} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessName();

    /**
     * The access permission for the {@code Surname} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessSurname();

}
