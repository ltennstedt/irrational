package irrational.number;

import java.util.Objects;

/**
 * Base class for rational numbers
 *
 * @param <R> type of the rational number
 */
abstract class AbstractRational<R extends AbstractRational<R>> extends AbstractNumber<R> implements Comparable<R> {
    /**
     * Indicates if this is a unit
     *
     * @return boolean
     */
    public abstract boolean isUnit();

    /**
     * Indicates if this is not a unit
     *
     * @return boolean
     */
    public final boolean isNotUnit() {
        return !isUnit();
    }

    /**
     * Indicates if this is dyadic
     *
     * @return boolean
     */
    public abstract boolean isDyadic();

    /**
     * Indicates if this is not dyadic
     *
     * @return boolean
     */
    public final boolean isNotDyadic() {
        return !isDyadic();
    }

    /**
     * Indicates if this is proper
     *
     * @return boolean
     */
    public abstract boolean isProper();

    /**
     * Indicates if this is improper
     *
     * @return boolean
     */
    public final boolean isImproper() {
        return !isProper();
    }

    /**
     * Indicates if this is positive
     *
     * @return boolean
     */
    public abstract boolean isPositive();

    /**
     * Indicates if this is negative
     *
     * @return boolean
     */
    public final boolean isNegative() {
        return !isPositive() && !isZero();
    }

    /**
     * Returns the signum
     *
     * @return signum
     */
    public abstract int signum();

    /**
     * Returns the minimum
     *
     * @param other other
     * @return boolean
     */
    public abstract R min(R other);

    /**
     * Returns the maximum
     *
     * @param other other
     * @return boolean
     */
    public abstract R max(R other);

    /**
     * Returns if this is less than the other
     *
     * @param other other
     * @return boolean
     * @throws NullPointerException if other is null
     */
    public final boolean isLessThan(final R other) {
        Objects.requireNonNull(other, "other");
        return compareTo(other) < 0;
    }

    /**
     * Returns if this is less than or equal to the other
     *
     * @param other other
     * @return boolean
     * @throws NullPointerException if other is null
     */
    public final boolean isLessThanOrEqualTo(final R other) {
        Objects.requireNonNull(other, "other");
        return compareTo(other) <= 0;
    }

    /**
     * Returns if this is greater than the other
     *
     * @param other other
     * @return boolean
     * @throws NullPointerException if other is null
     */
    public final boolean isGreaterThan(final R other) {
        Objects.requireNonNull(other, "other");
        return compareTo(other) > 0;
    }

    /**
     * Returns if this is greater than or equal to the other
     *
     * @param other other
     * @return boolean
     * @throws NullPointerException if other is null
     */
    public final boolean isGreaterThanOrEqualTo(final R other) {
        Objects.requireNonNull(other, "other");
        return compareTo(other) >= 0;
    }
}
