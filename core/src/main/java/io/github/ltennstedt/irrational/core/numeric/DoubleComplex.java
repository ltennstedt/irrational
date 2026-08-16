package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.Divisible;
import io.github.ltennstedt.irrational.core.Exponentiable;
import io.github.ltennstedt.irrational.core.util.Doubles;
import java.util.Objects;

/**
 * Immutable implementation of a complex number based on double
 *
 * @param real real part
 * @param imaginary imaginary part
 */
public record DoubleComplex(double real, double imaginary)
        implements Complex<DoubleComplex, DoubleComplex>,
                Divisible<DoubleComplex, DoubleComplex>,
                Exponentiable<DoubleComplex, DoubleComplex> {
    /** 0 */
    public static final DoubleComplex ZERO = new DoubleComplex(0D, 0D);

    /** 1 */
    public static final DoubleComplex ONE = new DoubleComplex(1D, 0D);

    /** i */
    public static final DoubleComplex I = new DoubleComplex(0D, 1D);

    /**
     * Canonical constructor
     *
     * @param real real part
     * @param imaginary imaginary part
     * @throws ArithmeticException when real is NaN or infinite
     * @throws ArithmeticException when imaginary is NaN or infinite
     */
    public DoubleComplex {
        real = Doubles.normalizeZero(Doubles.check(real, "real"));
        imaginary = Doubles.normalizeZero(Doubles.check(imaginary, "imaginary"));
    }

    /**
     * Static factory method
     *
     * @param radius radius
     * @param angle angle
     * @return {@link BigComplex}
     * @throws NullPointerException when radius is null
     * @throws NullPointerException when angle is null
     */
    public static DoubleComplex ofPolar(final double radius, final double angle) {
        final var normalizedRadius = Doubles.normalizeZero(Doubles.check(radius, "radius"));
        final var normalizedAngle = Doubles.normalizeZero(Doubles.check(angle, "angle"));
        return new DoubleComplex(
                normalizedRadius * StrictMath.cos(normalizedAngle), normalizedRadius * StrictMath.sin(normalizedAngle));
    }

    @Override
    public boolean isInvertible() {
        return !isZero();
    }

    @Override
    public boolean isZero() {
        return Doubles.isNear(norm(), 0D);
    }

    @Override
    public DoubleComplex add(final DoubleComplex summand) {
        Objects.requireNonNull(summand, "summand");
        return new DoubleComplex(real + summand.real, imaginary + summand.imaginary);
    }

    @Override
    public DoubleComplex subtract(final DoubleComplex subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new DoubleComplex(real - subtrahend.real, imaginary - subtrahend.imaginary);
    }

    @Override
    public DoubleComplex multiply(final DoubleComplex multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new DoubleComplex(
                real * multiplier.real - imaginary * multiplier.imaginary,
                real * multiplier.imaginary + imaginary * multiplier.real);
    }

    @Override
    public DoubleComplex divide(final DoubleComplex divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        if (Double.compare(StrictMath.abs(divisor.real), StrictMath.abs(divisor.imaginary)) >= 0) {
            final var r = divisor.imaginary / divisor.real;
            final var d = divisor.real + divisor.imaginary * r;
            return new DoubleComplex((real + imaginary * r) / d, (imaginary - real * r) / d);
        } else {
            final var r = divisor.real / divisor.imaginary;
            final var d = divisor.imaginary + divisor.real * r;
            return new DoubleComplex((real * r + imaginary) / d, (imaginary * r - real) / d);
        }
    }

    @Override
    public DoubleComplex negate() {
        return new DoubleComplex(-real, -imaginary);
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public DoubleComplex pow(final int exponent) {
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

    @Override
    public DoubleComplex reciprocal() {
        if (!isInvertible()) {
            throw new ArithmeticException("this must be invertible but was " + this);
        }
        final var denominator = norm();
        return new DoubleComplex(real / denominator, -imaginary / denominator);
    }

    @Override
    public DoubleComplex conjugate() {
        return new DoubleComplex(real, -imaginary);
    }

    /**
     * Returns the norm
     *
     * @return norm
     */
    public double norm() {
        final var abs = abs();
        return abs * abs;
    }

    /**
     * Returns the absolute value
     *
     * @return absolute value
     */
    public double abs() {
        return StrictMath.hypot(real, imaginary);
    }

    /**
     * Returns the argument
     *
     * @return argument
     */
    public double arg() {
        return StrictMath.atan2(imaginary, real);
    }
}
