package io.github.ltennstedt.irrational.core.numeric;

import static java.util.Objects.requireNonNull;

import io.github.ltennstedt.irrational.core.util.Longs;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;

/**
 * Immutable implementation of a rational number based on long
 *
 * @param numerator numerator
 * @param denominator denominator
 */
public record LongRational(long numerator, long denominator) implements Rational<LongRational> {
    /** Comparator */
    public static final Comparator<LongRational> COMPARATOR = Comparable::compareTo;

    /** 0 */
    public static final LongRational ZERO = new LongRational(0L, 1L);

    /** 1 */
    public static final LongRational ONE = new LongRational(1L, 1L);

    /**
     * All arguments constructor
     *
     * @param numerator numerator
     * @param denominator denominator
     * @throws ArithmeticException when denominator is 0 or an arithmetic overflow occurs
     */
    public LongRational {
        if (denominator == 0) {
            throw new ArithmeticException("denominator must not be 0 but was " + denominator);
        }
        final var gcd = Longs.gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (denominator < 0L) {
            numerator = StrictMath.negateExact(numerator);
            denominator = StrictMath.negateExact(denominator);
        }
    }

    @Override
    public boolean isInvertible() {
        return numerator != 0L;
    }

    @Override
    public boolean isZero() {
        return numerator == 0L;
    }

    @Override
    public boolean isUnitFraction() {
        return numerator == 1L;
    }

    @Override
    public boolean isDyadic() {
        return (denominator & StrictMath.decrementExact(denominator)) == 0L;
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public boolean isProper() {
        return StrictMath.absExact(numerator) < denominator;
    }

    @Override
    public boolean isPositive() {
        return numerator > 0L;
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational negate() {
        return new LongRational(StrictMath.negateExact(numerator), denominator);
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational add(final LongRational summand) {
        requireNonNull(summand, "summand");
        return new LongRational(
                StrictMath.addExact(
                        StrictMath.multiplyExact(summand.denominator, numerator),
                        StrictMath.multiplyExact(denominator, summand.numerator)),
                StrictMath.multiplyExact(denominator, summand.denominator));
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational subtract(final LongRational subtrahend) {
        requireNonNull(subtrahend, "subtrahend");
        return new LongRational(
                StrictMath.subtractExact(
                        StrictMath.multiplyExact(subtrahend.denominator, numerator),
                        StrictMath.multiplyExact(denominator, subtrahend.numerator)),
                StrictMath.multiplyExact(denominator, subtrahend.denominator));
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational multiply(final LongRational multiplier) {
        requireNonNull(multiplier, "multiplier");
        return new LongRational(
                StrictMath.multiplyExact(numerator, multiplier.numerator),
                StrictMath.multiplyExact(denominator, multiplier.denominator));
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational divide(final LongRational divisor) {
        requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return new LongRational(
                StrictMath.multiplyExact(numerator, divisor.denominator),
                StrictMath.multiplyExact(denominator, divisor.numerator));
    }

    @Override
    public LongRational pow(final int exponent) {
        if (exponent < 0) {
            if (!isInvertible()) {
                throw new ArithmeticException("this must be invertible but was " + this);
            }
            return reciprocal().pow(StrictMath.negateExact(exponent));
        }
        if (exponent == 0) {
            return ONE;
        }
        if (exponent == 1) {
            return this;
        }
        if (isZero()) {
            return ZERO;
        }
        final var half = pow(exponent / 2);
        final var squared = half.multiply(half);
        return (exponent & 1) == 0 ? squared : multiply(squared);
    }

    /** @throws ArithmeticException when this is not invertible */
    @Override
    public LongRational reciprocal() {
        if (!isInvertible()) {
            throw new ArithmeticException("this must be invertible but was " + this);
        }
        return new LongRational(denominator, numerator);
    }

    @Override
    public int signum() {
        return Long.signum(numerator);
    }

    @Override
    public LongRational min(final LongRational other) {
        requireNonNull(other, "other");
        return compareTo(other) <= 0 ? this : other;
    }

    @Override
    public LongRational max(final LongRational other) {
        requireNonNull(other, "other");
        return compareTo(other) >= 0 ? this : other;
    }

    /**
     * Returns the absolute value
     *
     * @return absolute value
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public LongRational abs() {
        return new LongRational(StrictMath.absExact(numerator), denominator);
    }

    @Override
    public BigDecimal toBigDecimal(final int scale, final RoundingMode roundingMode) {
        requireNonNull(roundingMode, "roundingMode");
        return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), scale, roundingMode);
    }

    @Override
    public BigDecimal toBigDecimal(final RoundingMode roundingMode) {
        requireNonNull(roundingMode, "roundingMode");
        return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), roundingMode);
    }

    @Override
    public BigDecimal toBigDecimal(final MathContext mathContext) {
        requireNonNull(mathContext, "mathContext");
        return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), mathContext);
    }

    /**
     * Compares this to other
     *
     * @return int
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public int compareTo(final LongRational other) {
        requireNonNull(other, "other");
        return Long.compare(
                StrictMath.multiplyExact(numerator, other.denominator),
                StrictMath.multiplyExact(other.numerator, denominator));
    }
}
