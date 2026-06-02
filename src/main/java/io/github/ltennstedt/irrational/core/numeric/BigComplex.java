package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.util.AtanCalculator;
import io.github.ltennstedt.irrational.core.util.Constants;
import io.github.ltennstedt.irrational.core.util.SinAndCosCalculator;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Immutable implementation of a complex number based on {@link BigDecimal}
 *
 * @param real real part
 * @param imaginary imaginary part
 */
public record BigComplex(BigDecimal real, BigDecimal imaginary) implements Complex<BigComplex, BigPolar> {
    /** 0 */
    public static final BigComplex ZERO = new BigComplex(BigDecimal.ZERO, BigDecimal.ZERO);

    /** 1 */
    public static final BigComplex ONE = new BigComplex(BigDecimal.ONE, BigDecimal.ZERO);

    /** i */
    public static final BigComplex I = new BigComplex(BigDecimal.ZERO, BigDecimal.ONE);

    /**
     * Canonical constructor
     *
     * @param real real part
     * @param imaginary imaginary part
     * @throws NullPointerException when real is null
     * @throws NullPointerException when imaginary is null
     */
    public BigComplex {
        Objects.requireNonNull(real, "real");
        Objects.requireNonNull(imaginary, "imaginary");
    }

    /**
     * Static factory method
     *
     * @param radius radius
     * @param argument argument
     * @return {@link BigComplex}
     * @throws NullPointerException when radius is null
     * @throws NullPointerException when argument is null
     */
    public static BigComplex ofPolar(final BigDecimal radius, final BigDecimal argument) {
        Objects.requireNonNull(radius, "radius");
        Objects.requireNonNull(argument, "argument");
        if (radius.signum() == 0) {
            return ZERO;
        }
        if (radius.signum() < 0) {
            final var normalizedRadius = radius.negate();
            final var normalizedArgument = argument.add(Constants.BIG_PI);
            return new BigComplex(
                    normalizedRadius.multiply(SinAndCosCalculator.cos(normalizedArgument)),
                    normalizedRadius.multiply(SinAndCosCalculator.sin(normalizedArgument)));
        }
        return new BigComplex(
                radius.multiply(SinAndCosCalculator.cos(argument)), radius.multiply(SinAndCosCalculator.sin(argument)));
    }

    /**
     * Static factory method
     *
     * @param polar {@link BigPolar}
     * @return {@link BigComplex}
     * @throws NullPointerException when polar is null
     */
    public static BigComplex ofPolar(final BigPolar polar) {
        Objects.requireNonNull(polar, "polar");
        return ofPolar(polar.radial(), polar.angular());
    }

    @Override
    public boolean isInvertible() {
        return !isZero();
    }

    @Override
    public boolean isZero() {
        return norm().signum() == 0;
    }

    @Override
    public BigComplex negate() {
        return new BigComplex(real.negate(), imaginary.negate());
    }

    @Override
    public BigComplex add(final BigComplex summand) {
        Objects.requireNonNull(summand, "summand");
        return new BigComplex(real.add(summand.real), imaginary.add(summand.imaginary));
    }

    @Override
    public BigComplex subtract(final BigComplex subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new BigComplex(real.subtract(subtrahend.real), imaginary.subtract(subtrahend.imaginary));
    }

    @Override
    public BigComplex multiply(final BigComplex multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new BigComplex(
                real.multiply(multiplier.real).subtract(imaginary.multiply(multiplier.imaginary)),
                real.multiply(multiplier.imaginary).add(imaginary.multiply(multiplier.real)));
    }

    @Override
    public BigComplex divide(final BigComplex divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        if (divisor.real.abs().compareTo(divisor.imaginary.abs()) >= 0) {
            final var r = divisor.imaginary.divide(divisor.real, Constants.DEFAULT_MATH_CONTEXT);
            final var d = divisor.real.add(divisor.imaginary.multiply(r), Constants.DEFAULT_MATH_CONTEXT);
            return new BigComplex(
                    real.add(imaginary.multiply(r)).divide(d, Constants.DEFAULT_MATH_CONTEXT),
                    imaginary.subtract(real.multiply(r)).divide(d, Constants.DEFAULT_MATH_CONTEXT));
        } else {
            final var r = divisor.real.divide(divisor.imaginary, Constants.DEFAULT_MATH_CONTEXT);
            final var d = divisor.imaginary.add(divisor.real.multiply(r), Constants.DEFAULT_MATH_CONTEXT);
            return new BigComplex(
                    real.multiply(r).add(imaginary).divide(d, Constants.DEFAULT_MATH_CONTEXT),
                    imaginary.multiply(r).subtract(real).divide(d, Constants.DEFAULT_MATH_CONTEXT));
        }
    }

    @Override
    public BigComplex pow(final int exponent) {
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

    /**
     * Returns the reciprocal
     *
     * @return reciprocal
     * @throws ArithmeticException when this is not invertible
     */
    @Override
    public BigComplex reciprocal() {
        if (!isInvertible()) {
            throw new ArithmeticException("this must be invertible but was " + this);
        }
        final var norm = norm();
        return new BigComplex(
                real.divide(norm, Constants.DEFAULT_MATH_CONTEXT),
                imaginary.negate().divide(norm, Constants.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public BigComplex conjugate() {
        return new BigComplex(real, imaginary.negate());
    }

    /**
     * Returns the norm
     *
     * @return norm
     */
    public BigDecimal norm() {
        return real.multiply(real).add(imaginary.multiply(imaginary));
    }

    /**
     * Returns the absolute value
     *
     * @return absolute value
     */
    public BigDecimal abs() {
        return norm().sqrt(Constants.DEFAULT_MATH_CONTEXT);
    }

    /**
     * Returns the argument
     *
     * @return argument
     */
    public BigDecimal arg() {
        return AtanCalculator.atan2(imaginary, real);
    }

    /**
     * Returns this as polar form
     *
     * @return {@link DoublePolar}
     * @throws ArithmeticException when radial is 0
     */
    @Override
    public BigPolar toPolar() {
        return BigPolar.ofComplex(this);
    }
}
