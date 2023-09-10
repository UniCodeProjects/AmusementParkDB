package org.apdb4j.util;

/**
 * A pair consisting of two elements.
 * @param <X> the type of the first element
 * @param <Y> the type of the second element
 */
public interface Pair<X, Y> {

    /**
     * Retrieves the first element of this pair.
     * @return the first element of this pair
     */
    X getLeft();

    /**
     * Retrieves the second element of this pair.
     * @return the second element of this pair
     */
    Y getRight();

}
