package io.github.ltennstedt.irrational.numeric;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;

/**
 * Immutable implementation of a rational number based on BigInteger
 *
 * @param numerator numerator
 * @param denominator denominator
 */
public record BigRational(BigInteger numerator, BigInteger denominator) implements Rational<BigRational> {
    /** Comparator */
    public static final Comparator<BigRational> COMPARATOR = Comparable::compareTo;

    /** 0 */
    public static final BigRational ZERO = new BigRational(BigInteger.ZERO, BigInteger.ONE);

    /** 1 */
    public static final BigRational ONE = new BigRational(BigInteger.ONE, BigInteger.ONE);

    /**
     * All arguments constructor
     *
     * @param numerator numerator
     * @param denominator denominator
     * @throws IllegalArgumentException when denominator is 0
     * @see #of(BigInteger)
     * @see #of(BigInteger, BigInteger)
     */
    public BigRational {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("denominator must not be 0 but was " + denominator);
        }
        final var gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
        if (denominator.signum() < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
    }

    /**
     * Static factory method
     *
     * @param numerator numerator
     * @return BigIntegerRational
     */
    public static BigRational of(final BigInteger numerator) {
        return of(numerator, BigInteger.ONE);
    }

    /**
     * Static factory method
     *
     * @param numerator numerator
     * @param denominator denominator
     * @return BigIntegerRational
     * @throws IllegalArgumentException when denominator is 0
     */
    public static BigRational of(final BigInteger numerator, final BigInteger denominator) {
        if (numerator.equals(BigInteger.ZERO)) {
            return ZERO;
        }
        if (numerator.equals(denominator)) {
            return ONE;
        }
        return new BigRational(numerator, denominator);
    }

    @Override
    public boolean isInvertible() {
        return !numerator.equals(BigInteger.ZERO);
    }

    @Override
    public boolean isInteger() {
        return denominator.equals(BigInteger.ONE);
    }

    @Override
    public boolean isZero() {
        return numerator.equals(BigInteger.ZERO);
    }

    @Override
    public boolean isOne() {
        return numerator.equals(denominator);
    }

    @Override
    public boolean isUnitFraction() {
        return numerator.equals(BigInteger.ONE);
    }

    @Override
    public boolean isDyadic() {
        return denominator.signum() > 0 && denominator.bitCount() == 1;
    }

    @Override
    public boolean isProper() {
        return numerator.abs().compareTo(denominator) < 0;
    }

    @Override
    public boolean isPositive() {
        return numerator.signum() > 0;
    }

    @Override
    public BigRational negate() {
        return of(numerator.negate(), denominator);
    }

    @Override
    public BigRational abs() {
        return of(numerator.abs(), denominator);
    }

    @Override
    public BigRational add(final BigRational summand) {
        requireNonNull(summand, "summand");
        return of(
                summand.denominator.multiply(numerator).add(denominator.multiply(summand.numerator)),
                denominator.multiply(summand.denominator));
    }

    @Override
    public BigRational subtract(final BigRational subtrahend) {
        requireNonNull(subtrahend, "subtrahend");
        return of(
                subtrahend.denominator.multiply(numerator).subtract(denominator.multiply(subtrahend.numerator)),
                denominator.multiply(subtrahend.denominator));
    }

    @Override
    public BigRational multiply(final BigRational multiplier) {
        requireNonNull(multiplier, "multiplier");
        return of(numerator.multiply(multiplier.numerator), denominator.multiply(multiplier.denominator));
    }

    @Override
    public BigRational divide(final BigRational divisor) {
        requireNonNull(divisor, "divisor");
        if (divisor.isNotInvertible()) {
            throw new IllegalArgumentException("divisor must be invertible but was " + divisor);
        }
        return of(numerator.multiply(divisor.denominator), denominator.multiply(divisor.numerator));
    }

    @Override
    public BigRational invert() {
        if (isNotInvertible()) {
            throw new IllegalStateException("this must be invertible but was " + this);
        }
        return of(denominator, numerator);
    }

    @Override
    public BigRational power(final int exponent) {
        return of(numerator.pow(exponent), denominator.pow(exponent));
    }

    @Override
    public int signum() {
        return numerator.signum();
    }

    @Override
    public BigRational min(final BigRational other) {
        requireNonNull(other, "other");
        return isLessThanOrEqualTo(other) ? this : other;
    }

    @Override
    public BigRational max(final BigRational other) {
        requireNonNull(other, "other");
        return isGreaterThanOrEqualTo(other) ? this : other;
    }

    @Override
    public BigRational increment() {
        return add(ONE);
    }

    @Override
    public BigRational decrement() {
        return subtract(ONE);
    }

    @Override
    public BigDecimal toBigDecimal(final int scale, final RoundingMode roundingMode) {
        requireNonNull(roundingMode, "roundingMode");
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), scale, roundingMode);
    }

    @Override
    public BigDecimal toBigDecimal(final RoundingMode roundingMode) {
        requireNonNull(roundingMode, "roundingMode");
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), roundingMode);
    }

    @Override
    public BigDecimal toBigDecimal(final MathContext mathContext) {
        requireNonNull(mathContext, "mathContext");
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), mathContext);
    }

    /**
     * Compares this to other
     *
     * @return int
     */
    @Override
    public int compareTo(final BigRational other) {
        requireNonNull(other, "other");
        return numerator.multiply(other.denominator).compareTo(denominator.multiply(other.numerator));
    }
}
