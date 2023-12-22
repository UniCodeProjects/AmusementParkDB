package org.apdb4j.core.permissions;

/**
 * A special type of access that grants an AccessType of
 * type {@code ALL} for every known access.
 * @see Access
 * @see AccessType.Read#GLOBAL
 * @see AccessType.Write#GLOBAL
 */
public interface AllAccess extends Access {
}