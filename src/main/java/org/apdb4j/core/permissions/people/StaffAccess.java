package org.apdb4j.core.permissions.people;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessSettings;

/**
 * The access related to staff members.
 */
public interface StaffAccess extends PersonAccess {

    /**
     * The access permission for the {@code NationalID} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfStaffNationalID();

    /**
     * The access permission for the {@code DoB} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfStaffDoB();

    /**
     * The access permission for the {@code BirthPlace} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfStaffBirthPlace();

    /**
     * The access permission for the {@code Gender} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfStaffGender();

}
