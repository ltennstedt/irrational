package irrational.number;

import java.util.Objects;

/**
 * Base class for rational numbers
 *
 * @param <R> type of the rational number
 */
interface Rational<R extends Rational<R>> extends Numeric<R>, Comparable<R> {
    /**
     * Indicates if this is a unit
     *
     * @return boolean
     */
    boolean isUnit();

    /**
     * Indicates if this is not a unit
     *
     * @return boolean
     */
    default boolean isNotUnit() {
        return !isUnit();
    }

    /**
     * Indicates if this is dyadic
     *
     * @return boolean
     */
    boolean isDyadic();

    /**
     * Indicates if this is not dyadic
     *
     * @return boolean
     */
    default boolean isNotDyadic() {
        return !isDyadic();
    }

    /**
     * Indicates if this is proper
     *
     * @return boolean
     */
    boolean isProper();

    /**
     * Indicates if this is improper
     *
     * @return boolean
     */
    default boolean isImproper() {
        return !isProper();
    }

    /**
     * Indicates if this is positive
     *
     * @return boolean
     */
    boolean isPositive();

    /**
     * Indicates if this is negative
     *
     * @return boolean
     */
    default boolean isNegative() {
        return !isPositive() && !isZero();
    }

    /**
     * Returns the signum
     *
     * @return signum
     */
    int signum();

    /**
     * Returns the minimum
     *
     * @param other other
     * @return boolean
     */
    R min(R other);

    /**
     * Returns the maximum
     *
     * @param other other
     * @return boolean
     */
    R max(R other);

    /**
     * Returns if this is less than the other
     *
     * @param other other
     * @return boolean
     * @throws NullPointerException if other is null
     */
    default boolean isLessThan(final R other) {
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
    default boolean isLessThanOrEqualTo(final R other) {
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
    default boolean isGreaterThan(final R other) {
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
    default boolean isGreaterThanOrEqualTo(final R other) {
        Objects.requireNonNull(other, "other");
        return compareTo(other) >= 0;
    }
}
