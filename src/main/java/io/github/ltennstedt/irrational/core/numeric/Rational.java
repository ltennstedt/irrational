package io.github.ltennstedt.irrational.core.numeric;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Base interface for rational numbers
 *
 * @param <R> type of the rational number
 */
public sealed interface Rational<R extends Rational<R>> extends Numeric<R, R>, Comparable<R>
        permits LongRational, BigRational {
    /**
     * Indicates if this is a unit
     *
     * @return boolean
     */
    boolean isUnitFraction();

    /**
     * Indicates if this is dyadic
     *
     * @return boolean
     */
    boolean isDyadic();

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
