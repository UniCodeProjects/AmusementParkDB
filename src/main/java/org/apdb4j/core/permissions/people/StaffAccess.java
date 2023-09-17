package org.apdb4j.core.permissions.people;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to staff members.
 */
public interface StaffAccess extends PersonAccess {

    /**
     * The access permission for the {@code NationalID} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessStaffNationalID();

    /**
     * The access permission for the {@code DoB} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessStaffDoB();

    /**
     * The access permission for the {@code BirthPlace} attribute.
     * @return the type of access
     */
    @NonNull AccessType canAccessStaffBirthPlace();

    /**
     * The access permission for the {@code Gender} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessStaffGender();

}
