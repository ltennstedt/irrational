package io.github.ltennstedt.irrational.core.numeric;

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
     * @throws ArithmeticException when denominator is 0
     */
    public BigRational {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("denominator must not be 0 but was " + denominator);
        }
        final var gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
        if (denominator.signum() < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
    }

    @Override
    public boolean isInvertible() {
        return !numerator.equals(BigInteger.ZERO);
    }

    @Override
    public boolean isZero() {
        return numerator.equals(BigInteger.ZERO);
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
        return new BigRational(numerator.negate(), denominator);
    }

    @Override
    public BigRational add(final BigRational summand) {
        requireNonNull(summand, "summand");
        return new BigRational(
                summand.denominator.multiply(numerator).add(denominator.multiply(summand.numerator)),
                denominator.multiply(summand.denominator));
    }

    @Override
    public BigRational subtract(final BigRational subtrahend) {
        requireNonNull(subtrahend, "subtrahend");
        return new BigRational(
                subtrahend.denominator.multiply(numerator).subtract(denominator.multiply(subtrahend.numerator)),
                denominator.multiply(subtrahend.denominator));
    }

    @Override
    public BigRational multiply(final BigRational multiplier) {
        requireNonNull(multiplier, "multiplier");
        return new BigRational(numerator.multiply(multiplier.numerator), denominator.multiply(multiplier.denominator));
    }

    @Override
    public BigRational divide(final BigRational divisor) {
        requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return new BigRational(numerator.multiply(divisor.denominator), denominator.multiply(divisor.numerator));
    }

    @Override
    public BigRational pow(final int exponent) {
        if (exponent < 0) {
            if (!isInvertible()) {
                throw new ArithmeticException("this must be invertible but was " + this);
            }
            return reciprocal().pow(Math.negateExact(exponent));
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

    @Override
    public BigRational reciprocal() {
        if (!isInvertible()) {
            throw new ArithmeticException("this must be invertible but was " + this);
        }
        return new BigRational(denominator, numerator);
    }

    @Override
    public int signum() {
        return numerator.signum();
    }

    @Override
    public BigRational min(final BigRational other) {
        requireNonNull(other, "other");
        return compareTo(other) <= 0 ? this : other;
    }

    @Override
    public BigRational max(final BigRational other) {
        requireNonNull(other, "other");
        return compareTo(other) >= 0 ? this : other;
    }

    /**
     * Returns the absolute value
     *
     * @return absolute value
     */
    public BigRational abs() {
        return new BigRational(numerator.abs(), denominator);
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
