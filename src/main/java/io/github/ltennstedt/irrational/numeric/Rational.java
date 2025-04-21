package io.github.ltennstedt.irrational.numeric;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Base class for rational numbers
 *
 * @param <R> type of the rational number
 */
public sealed interface Rational<R extends Rational<R>> extends Numeric<R>, Comparable<R>
        permits LongRational, BigRational {
    /**
     * Indicates if this is a unit
     *
     * @return boolean
     */
    boolean isUnitFraction();

    /**
     * Indicates if this is not a unit
     *
     * @return boolean
     */
    default boolean isNotUnitFraction() {
        return !isUnitFraction();
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
     * @return minimum
     */
    R min(R other);

    /**
     * Returns the maximum
     *
     * @param other other
     * @return maximum
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
        requireNonNull(other, "other");
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
        requireNonNull(other, "other");
        return compareTo(other) <= 0;
    }

    /**
     * Returns the increment
     *
     * @return increment
     */
    R increment();

    /**
     * Returns the decrement
     *
     * @return decrement
     */
    R decrement();

    /**
     * Returns if this is greater than the other
     *
     * @param other other
     * @return boolean
     * @throws NullPointerException if other is null
     */
    default boolean isGreaterThan(final R other) {
        requireNonNull(other, "other");
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
        requireNonNull(other, "other");
        return compareTo(other) >= 0;
    }

    /**
     * Returns this as {@link BigDecimal}
     *
     * @param scale scale
     * @param roundingMode {@link RoundingMode}
     * @return {@link BigDecimal}
     * @throws NullPointerException when roundingMode is null
     */
    BigDecimal toBigDecimal(int scale, RoundingMode roundingMode);

    /**
     * Returns this as {@link BigDecimal}
     *
     * @param roundingMode {@link RoundingMode}
     * @return {@link BigDecimal}
     * @throws NullPointerException when roundingMode is null
     */
    BigDecimal toBigDecimal(RoundingMode roundingMode);

    /**
     * Returns this as {@link BigDecimal}
     *
     * @param mathContext {@link MathContext}
     * @return {@link BigDecimal}
     * @throws NullPointerException when mathContext is null
     */
    BigDecimal toBigDecimal(MathContext mathContext);
}
