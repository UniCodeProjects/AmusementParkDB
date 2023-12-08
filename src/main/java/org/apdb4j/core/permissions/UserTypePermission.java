package org.apdb4j.core.permissions;

import java.util.List;

/**
 * Represents the permissions related to a specific user type.
 * @param userType the user type
 * @param permissions the permission list
 */
public record UserTypePermission(String userType, List<Permission> permissions) {

    /**
     * UserTypePermission constructor.
     * @param userType the user type
     * @param permissions the permission list
     */
    public UserTypePermission(final String userType, final List<Permission> permissions) {
        this.userType = userType;
        this.permissions = List.copyOf(permissions);
    }

}
