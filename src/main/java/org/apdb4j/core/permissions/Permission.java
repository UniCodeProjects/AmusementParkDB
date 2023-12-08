package org.apdb4j.core.permissions;

import java.util.List;

/**
 * Represents an account's permission.
 * @param forUserType a list that contains the user types to which the read and write values are applied to
 * @param read the {@code read} value
 * @param write the {@code write} value
 */
public record Permission(List<String> forUserType, boolean read, boolean write) {

    /**
     * Permission constructor.
     * @param forUserType a list that contains the user types to which the read and write values are applied to
     * @param read the {@code read} value
     * @param write the {@code write} value
     */
    public Permission(final List<String> forUserType, final boolean read, final boolean write) {
        this.forUserType = List.copyOf(forUserType);
        this.read = read;
        this.write = write;
    }

}
