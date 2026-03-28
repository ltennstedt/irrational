package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.util.Doubles;
import java.util.Objects;

/**
 * Immutable implementation of polar coordinates based on double
 *
 * @param radial radial
 * @param angular angular
 */
public record DoublePolar(double radial, double angular) implements Polar<DoublePolar, DoubleComplex> {
    public static final DoublePolar ZERO = new DoublePolar(0D, 0D);

    /**
     * Canonical constructor
     *
     * @param radial radiol
     * @param angular angular
     */
    public DoublePolar {
        if (radial < 0D) {
            radial = Math.abs(radial);
            angular += Math.PI;
        }
        angular = normalizeAngle(angular);
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
        return new DoublePolar(radial, Math.atan2(complex.imaginary(), complex.real()));
    }

    @Override
    public boolean isZero() {
        return Doubles.isNear(radial, 0D);
    }

    @Override
    public DoublePolar multiply(final DoublePolar multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new DoublePolar(radial * multiplier.radial, angular + multiplier.angular);
    }

    @Override
    public DoublePolar divide(final DoublePolar divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return new DoublePolar(radial / divisor.radial, angular - divisor.angular);
    }

    @Override
    public DoublePolar pow(final int exponent) {
        return new DoublePolar(Math.pow(radial, exponent), exponent * angular);
    }

    @Override
    public DoublePolar reciprocal() {
        return new DoublePolar(1 / radial, -angular);
    }

    @Override
    public DoublePolar negate() {
        return new DoublePolar(radial, normalizeAngle(angular + Math.PI));
    }

    @Override
    public DoubleComplex toComplex() {
        return new DoubleComplex(radial * Math.cos(angular), radial * Math.sin(angular));
    }

    private double normalizeAngle(final double angle) {
        final var mod = 2 * Math.PI;
        return (angle % mod + mod) % mod;
    }
}
