package io.github.ltennstedt.irrational.numeric;

import static java.util.Objects.requireNonNull;

import io.github.ltennstedt.irrational.util.Longs;
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
     * @throws IllegalArgumentException when denominator is 0
     * @throws ArithmeticException when an arithmetic overflow occurs
     * @see #of(long)
     * @see #of(long, long)
     */
    public LongRational {
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator must not be 0 but was " + denominator);
        }
        final var gcd = Longs.gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (denominator < 0L) {
            numerator = Math.negateExact(numerator);
            denominator = Math.negateExact(denominator);
        }
    }

    /**
     * Static factory method
     *
     * @param numerator numerator
     * @return LongRational
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public static LongRational of(final long numerator) {
        return of(numerator, 1L);
    }

    /**
     * Static factory method
     *
     * @param numerator numerator
     * @param denominator denominator
     * @return LongRational
     * @throws IllegalArgumentException when denominator is 0
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public static LongRational of(final long numerator, final long denominator) {
        if (numerator == 0L) {
            return ZERO;
        }
        if (numerator == denominator) {
            return ONE;
        }
        return new LongRational(numerator, denominator);
    }

    @Override
    public boolean isInvertible() {
        return numerator != 0L;
    }

    @Override
    public boolean isInteger() {
        return denominator == 1L;
    }

    @Override
    public boolean isZero() {
        return numerator == 0L;
    }

    @Override
    public boolean isOne() {
        return numerator == denominator;
    }

    @Override
    public boolean isUnitFraction() {
        return numerator == 1L;
    }

    @Override
    public boolean isDyadic() {
        return (denominator & Math.decrementExact(denominator)) == 0L;
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public boolean isProper() {
        return Math.absExact(numerator) < denominator;
    }

    @Override
    public boolean isPositive() {
        return numerator > 0L;
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational negate() {
        return of(Math.negateExact(numerator), denominator);
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational abs() {
        return of(Math.absExact(numerator), denominator);
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational add(final LongRational summand) {
        requireNonNull(summand, "summand");
        return of(
                Math.addExact(
                        Math.multiplyExact(summand.denominator, numerator),
                        Math.multiplyExact(denominator, summand.numerator)),
                Math.multiplyExact(denominator, summand.denominator));
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational subtract(final LongRational subtrahend) {
        requireNonNull(subtrahend, "subtrahend");
        return of(
                Math.subtractExact(
                        Math.multiplyExact(subtrahend.denominator, numerator),
                        Math.multiplyExact(denominator, subtrahend.numerator)),
                Math.multiplyExact(denominator, subtrahend.denominator));
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational multiply(final LongRational multiplier) {
        requireNonNull(multiplier, "multiplier");
        return of(
                Math.multiplyExact(numerator, multiplier.numerator),
                Math.multiplyExact(denominator, multiplier.denominator));
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational divide(final LongRational divisor) {
        requireNonNull(divisor, "divisor");
        if (divisor.isNotInvertible()) {
            throw new IllegalArgumentException("divisor must be invertible but was " + divisor);
        }
        return of(
                Math.multiplyExact(numerator, divisor.denominator), Math.multiplyExact(denominator, divisor.numerator));
    }

    @Override
    public LongRational invert() {
        if (isNotInvertible()) {
            throw new IllegalStateException("this must be invertible but was " + this);
        }
        return of(denominator, numerator);
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational power(final int exponent) {
        return of((long) Longs.power(numerator, exponent), (long) Longs.power(denominator, exponent));
    }

    @Override
    public int signum() {
        return Long.signum(numerator);
    }

    @Override
    public LongRational min(final LongRational other) {
        requireNonNull(other, "other");
        return isLessThanOrEqualTo(other) ? this : other;
    }

    @Override
    public LongRational max(final LongRational other) {
        requireNonNull(other, "other");
        return isGreaterThanOrEqualTo(other) ? this : other;
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational increment() {
        return add(ONE);
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public LongRational decrement() {
        return subtract(ONE);
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
                Math.multiplyExact(numerator, other.denominator), Math.multiplyExact(other.numerator, denominator));
    }
}
