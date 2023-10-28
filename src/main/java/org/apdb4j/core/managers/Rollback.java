package org.apdb4j.core.managers;

/**
 * A functional interface for implementing a rollback method
 * used when executing queries in the database.
 */
@FunctionalInterface
public interface Rollback {

    /**
     * The rollback action.
     * @return {@code true} on success, {@code false} otherwise
     */
    boolean action();

}
