package org.apdb4j.core.permissions;

import java.util.List;

/**
 * Represents a permission's attribute.
 * @param attribute the database attribute
 * @param accesses a list of accesses related to the attribute
 */
public record Attribute(String attribute, List<Access> accesses) {

    /**
     * Attribute constructor.
     * @param attribute the attribute
     * @param accesses the list of accesses
     */
    public Attribute(final String attribute, final List<Access> accesses) {
        this.attribute = attribute;
        this.accesses = List.copyOf(accesses);
    }
}
