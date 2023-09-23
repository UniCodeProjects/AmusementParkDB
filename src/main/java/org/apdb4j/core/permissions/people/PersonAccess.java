package org.apdb4j.core.permissions.people;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to people.
 */
public interface PersonAccess extends Access {

    /**
     * The access permission for the {@code PersonID} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfPersonID();

    /**
     * The access permission for the {@code Name} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfPersonName();

    /**
     * The access permission for the {@code Surname} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfPersonSurname();

}
