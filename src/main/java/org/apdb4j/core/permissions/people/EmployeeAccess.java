package org.apdb4j.core.permissions.people;

/**
 * The permissions related to employees.
 */
public interface EmployeeAccess extends StaffAccess {

    /**
     * The access permission for the {@code Role} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessEmployeeRole();

}
