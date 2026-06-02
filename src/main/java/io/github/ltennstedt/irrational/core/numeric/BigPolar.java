package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.util.AtanCalculator;
import io.github.ltennstedt.irrational.core.util.Constants;
import io.github.ltennstedt.irrational.core.util.PiCalculator;
import io.github.ltennstedt.irrational.core.util.SinAndCosCalculator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * Immutable implementation of polar coordinates based on {@link BigDecimal}
 *
 * @param radius radius
 * @param angle angle
 */
public record BigPolar(BigDecimal radius, BigDecimal angle)
        implements MathContextNumeric<BigPolar>, Polar<BigPolar, BigComplex> {
    /** 0 */
    public static final BigPolar ZERO = new BigPolar(BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * Canonical constructor
     *
     * @param radius radiol
     * @param angle angle
     * @throws NullPointerException when radius is null
     * @throws NullPointerException when angle is null
     */
    public BigPolar {
        Objects.requireNonNull(radius, "radius");
        Objects.requireNonNull(angle, "angle");
        if (radius.signum() < 0) {
            radius = radius.abs();
            angle = angle.add(Constants.BIG_PI);
        }
        angle = normalizeAngle(angle);
    }

    /**
     * Static factory method
     *
     * @param complex {@link DoubleComplex}
     * @return {@link DoubleComplex}
     * @throws NullPointerException when complex is null
     */
    public static BigPolar ofComplex(final BigComplex complex) {
        Objects.requireNonNull(complex, "complex");
        return new BigPolar(complex.abs(), AtanCalculator.atan2(complex.imaginary(), complex.real()));
    }

    /**
     * Static factory method
     *
     * @param complex {@link DoubleComplex}
     * @param mathContext {@link MathContext}
     * @return {@link DoubleComplex}
     * @throws NullPointerException when complex is null
     * @throws NullPointerException when mathContext is null
     */
    public static BigPolar ofComplex(final BigComplex complex, final MathContext mathContext) {
        Objects.requireNonNull(complex, "complex");
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigPolar(
                complex.abs(mathContext), AtanCalculator.atan2(complex.imaginary(), complex.real(), mathContext));
    }

    @Override
    public boolean isZero() {
        return radius.signum() == 0;
    }

    @Override
    public BigPolar add(final BigPolar summand, final MathContext mathContext) {
        Objects.requireNonNull(summand, "summand");
        Objects.requireNonNull(mathContext, "mathContext");
        return toComplex(mathContext)
                .add(summand.toComplex(mathContext), mathContext)
                .toPolar(mathContext);
    }

    @Override
    public BigPolar subtract(final BigPolar subtrahend, final MathContext mathContext) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        Objects.requireNonNull(mathContext, "mathContext");
        return toComplex(mathContext)
                .subtract(subtrahend.toComplex(mathContext), mathContext)
                .toPolar(mathContext);
    }

    @Override
    public BigPolar multiply(final BigPolar multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new BigPolar(radius.multiply(multiplier.radius), angle.add(multiplier.angle));
    }

    @Override
    public BigPolar multiply(final BigPolar multiplier, final MathContext mathContext) {
        Objects.requireNonNull(multiplier, "multiplier");
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigPolar(radius.multiply(multiplier.radius, mathContext), angle.add(multiplier.angle, mathContext));
    }

    @Override
    public BigPolar divide(final BigPolar divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return new BigPolar(
                radius.divide(divisor.radius, Constants.DEFAULT_MATH_CONTEXT), angle.subtract(divisor.angle));
    }

    @Override
    public BigPolar divide(final BigPolar divisor, final MathContext mathContext) {
        Objects.requireNonNull(divisor, "divisor");
        Objects.requireNonNull(mathContext, "mathContext");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return new BigPolar(radius.divide(divisor.radius, mathContext), angle.subtract(divisor.angle, mathContext));
    }

    @Override
    public BigPolar pow(final int exponent) {
        return new BigPolar(radius.pow(exponent), BigDecimal.valueOf(exponent).multiply(angle));
    }

    @Override
    public BigPolar pow(final int exponent, final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigPolar(
                radius.pow(exponent, mathContext), BigDecimal.valueOf(exponent).multiply(angle, mathContext));
    }

    @Override
    public BigPolar reciprocal() {
        return new BigPolar(BigDecimal.ONE.divide(radius, Constants.DEFAULT_MATH_CONTEXT), angle.negate());
    }

    @Override
    public BigPolar reciprocal(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigPolar(BigDecimal.ONE.divide(radius, mathContext), angle.negate(mathContext));
    }

    @Override
    public BigPolar negate() {
        return new BigPolar(radius, normalizeAngle(angle.add(Constants.BIG_PI)));
    }

    @Override
    public BigPolar negate(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigPolar(radius, normalizeAngle(angle.add(PiCalculator.pi(mathContext), mathContext), mathContext));
    }

    @Override
    public BigComplex toComplex() {
        return new BigComplex(
                radius.multiply(SinAndCosCalculator.cos(angle)), radius.multiply(SinAndCosCalculator.sin(angle)));
    }

    /**
     * Returns this as complex number
     *
     * @param mathContext {@link MathContext}
     * @return {@link Complex}
     * @throws NullPointerException when mathContext is null
     */
    public BigComplex toComplex(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigComplex(
                radius.multiply(SinAndCosCalculator.cos(angle, mathContext), mathContext),
                radius.multiply(SinAndCosCalculator.sin(angle, mathContext), mathContext));
    }

    private BigDecimal normalizeAngle(final BigDecimal _angle) {
        final var mod = BigDecimal.valueOf(2L).multiply(Constants.BIG_PI);
        return _angle.remainder(mod).add(mod).remainder(mod);
    }

    private BigDecimal normalizeAngle(final BigDecimal _angle, final MathContext mathContext) {
        final var mod = BigDecimal.valueOf(2L).multiply(PiCalculator.pi(mathContext), mathContext);
        return _angle.remainder(mod).add(mod).remainder(mod);
    }
}
