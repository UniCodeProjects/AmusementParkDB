package org.apdb4j.core.permissions.people;

import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to staff members.
 */
public interface StaffAccess extends PersonAccess {

    /**
     * The access permission for the {@code NationalID} attribute.
     * @return the type of access
     */
    AccessType canAccessStaffNationalID();

    /**
     * The access permission for the {@code DoB} attribute.
     * @return the type of access
     */
    AccessType canAccessStaffDoB();

    /**
     * The access permission for the {@code BirthPlace} attribute.
     * @return the type of access
     */
    AccessType canAccessStaffBirthPlace();

}
