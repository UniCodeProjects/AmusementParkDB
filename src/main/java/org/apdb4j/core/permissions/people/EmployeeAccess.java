package org.apdb4j.core.permissions.people;

import lombok.NonNull;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to employees.
 */
public interface EmployeeAccess extends StaffAccess {

    /**
     * The access permission for the {@code Role} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfEmployeeRole();

}
