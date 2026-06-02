package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.util.Doubles;
import java.util.Objects;

/**
 * Immutable implementation of polar coordinates based on double
 *
 * @param radius radius
 * @param angle angle
 */
public record DoublePolar(double radius, double angle) implements Polar<DoublePolar, DoubleComplex> {
    /** 0 */
    public static final DoublePolar ZERO = new DoublePolar(0D, 0D);

    /**
     * Canonical constructor
     *
     * @param radius radiol
     * @param angle angle
     */
    public DoublePolar {
        if (radius < 0D) {
            radius = StrictMath.abs(radius);
            angle += StrictMath.PI;
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
    public static DoublePolar ofComplex(final DoubleComplex complex) {
        Objects.requireNonNull(complex, "complex");
        final var radial = complex.abs();
        return new DoublePolar(radial, StrictMath.atan2(complex.imaginary(), complex.real()));
    }

    @Override
    public boolean isZero() {
        return Doubles.isNear(radius, 0D);
    }

    @Override
    public DoublePolar multiply(final DoublePolar multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new DoublePolar(radius * multiplier.radius, angle + multiplier.angle);
    }

    @Override
    public DoublePolar divide(final DoublePolar divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return new DoublePolar(radius / divisor.radius, angle - divisor.angle);
    }

    /** @throws ArithmeticException when an arithmetic overflow occurs */
    @Override
    public DoublePolar pow(final int exponent) {
        return new DoublePolar(StrictMath.pow(radius, exponent), exponent * angle);
    }

    @Override
    public DoublePolar reciprocal() {
        return new DoublePolar(1 / radius, -angle);
    }

    @Override
    public DoublePolar negate() {
        return new DoublePolar(radius, normalizeAngle(angle + StrictMath.PI));
    }

    @Override
    public DoubleComplex toComplex() {
        return new DoubleComplex(radius * StrictMath.cos(angle), radius * StrictMath.sin(angle));
    }

    private double normalizeAngle(final double _angle) {
        final var mod = 2 * StrictMath.PI;
        return (_angle % mod + mod) % mod;
    }
}
