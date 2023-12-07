package org.apdb4j.core.permissions;

import java.util.List;

/**
 * Represents an account's permission.
 * @param type the type of permission
 * @param attributes a list of attributes related to the permission
 */
public record Permission(String type, List<Attribute> attributes) {

    /**
     * Permission constructor.
     * @param type the type of permission
     * @param attributes the list of attributes
     */
    public Permission(final String type, final List<Attribute> attributes) {
        this.type = type;
        this.attributes = List.copyOf(attributes);
    }

}
