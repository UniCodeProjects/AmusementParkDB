package org.apdb4j.core.permissions;

/**
 * The access that is related to a specific database attribute.
 * @param target the access' target account
 * @param type the type of access - local or global
 * @param read enables read for this access
 * @param write enables write for this access
 */
public record Access(String target, String type, boolean read, boolean write) {
}
