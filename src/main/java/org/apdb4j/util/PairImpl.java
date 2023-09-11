package org.apdb4j.util;


/**
 * A standard implementation of interface {@link Pair}.
 * @param <X> the type of the first element
 * @param <Y> the type of the second element
 */
public class PairImpl<X, Y> implements Pair<X, Y> {

    private final X x;
    private final Y y;

    /**
     * Basic constructor.
     * @param x the first element of this pair
     * @param y the second element of this pair
     */
    public PairImpl(final X x, final Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public X getLeft() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Y getRight() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PairImpl other = (PairImpl) obj;
        if (x == null) {
            if (other.x != null) {
                return false;
            }
        } else if (!x.equals(other.x)) {
            return false;
        }
        if (y == null) {
            if (other.y != null) {
                return false;
            }
        } else if (!y.equals(other.y)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Pair [x=" + x + ", y=" + y + "]";
    }
}