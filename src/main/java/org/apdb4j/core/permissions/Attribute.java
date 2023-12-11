package org.apdb4j.core.permissions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

/**
 * Represents a permission's attribute.
 * @param attribute the database attribute
 * @param userTypePermissions a list of userTypePermissions related to the attribute
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "The constructor creates a defensive immutable copy of the list")
public record Attribute(String attribute, List<UserTypePermission> userTypePermissions) {

    /**
     * Attribute constructor.
     * @param attribute the attribute
     * @param userTypePermissions the list of userTypePermissions
     */
    public Attribute(final String attribute, final List<UserTypePermission> userTypePermissions) {
        this.attribute = attribute;
        this.userTypePermissions = List.copyOf(userTypePermissions);
    }

}
