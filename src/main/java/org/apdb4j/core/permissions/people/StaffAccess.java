package org.apdb4j.core.permissions.people;

/**
 * The access related to staff members.
 */
public interface StaffAccess extends PersonAccess {

    /**
     * The access permission for the {@code NationalID} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessStaffNationalID();

    /**
     * The access permission for the {@code DoB} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessStaffDoB();

    /**
     * The access permission for the {@code BirthPlace} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessStaffBirthPlace();

}
