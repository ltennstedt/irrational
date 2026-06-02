package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.util.Doubles;
import java.util.Objects;

/**
 * Immutable implementation of a complex number based on double
 *
 * @param real real part
 * @param imaginary imaginary part
 */
public record DoubleComplex(double real, double imaginary) implements Complex<DoubleComplex, DoublePolar> {
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
        real = normalizeZero(check(real, "real"));
        imaginary = normalizeZero(check(imaginary, "imaginary"));
    }

    /**
     * Static factory method
     *
     * @param radius radius
     * @param argument argument
     * @return {@link DoubleComplex}
     * @throws ArithmeticException when radius is NaN or infinite
     * @throws ArithmeticException when argument is NaN or infinite
     */
    public static DoubleComplex ofPolar(final double radius, final double argument) {
        check(radius, "radius");
        check(argument, "argument");
        if (Doubles.isNear(radius, 0D)) {
            return ZERO;
        }
        if (radius < 0D) {
            final var normalizedRadius = -radius;
            final var normalizedArgument = argument + StrictMath.PI;
            return new DoubleComplex(
                    normalizedRadius * StrictMath.cos(normalizedArgument),
                    normalizedRadius * StrictMath.sin(normalizedArgument));
        }
        return new DoubleComplex(radius * StrictMath.cos(argument), radius * StrictMath.sin(argument));
    }

    /**
     * Static factory method
     *
     * @param polar {@link DoublePolar}
     * @return {@link DoubleComplex}
     * @throws ArithmeticException when radius is NaN or infinite
     * @throws ArithmeticException when argument is NaN or infinite
     */
    public static DoubleComplex ofPolar(final DoublePolar polar) {
        Objects.requireNonNull(polar, "polar");
        return ofPolar(polar.radial(), polar.angular());
    }

    private static double normalizeZero(final double d) {
        return d == 0D ? 0D : d;
    }

    private static double check(final double d, final String name) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new ArithmeticException("%s must not be NaN and must be finite but was %s".formatted(name, d));
        }
        return d;
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
    public DoubleComplex negate() {
        return new DoubleComplex(-real, -imaginary);
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
        if (Double.compare(Math.abs(divisor.real), StrictMath.abs(divisor.imaginary)) >= 0) {
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

    /**
     * Returns this as polar form
     *
     * @return {@link DoublePolar}
     * @throws ArithmeticException when radial is 0
     */
    @Override
    public DoublePolar toPolar() {
        return DoublePolar.ofComplex(this);
    }
}
