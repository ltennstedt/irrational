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
 * @param radial radial
 * @param angular angular
 */
public record BigPolar(BigDecimal radial, BigDecimal angular) implements Polar<BigPolar, BigComplex> {
    /** 0 */
    public static final BigPolar ZERO = new BigPolar(BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * Canonical constructor
     *
     * @param radial radiol
     * @param angular angular
     */
    public BigPolar {
        if (radial.signum() < 0) {
            radial = radial.abs();
            angular = angular.add(PiCalculator.pi(MathContext.DECIMAL128));
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
    public static BigPolar ofComplex(final BigComplex complex) {
        Objects.requireNonNull(complex, "complex");
        return new BigPolar(
                complex.abs(),
                AtanCalculator.atan2(complex.imaginary(), complex.real(), Constants.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public boolean isZero() {
        return radial.signum() == 0;
    }

    @Override
    public BigPolar multiply(final BigPolar multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new BigPolar(radial.multiply(multiplier.radial), angular.add(multiplier.angular));
    }

    @Override
    public BigPolar divide(final BigPolar divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return new BigPolar(
                radial.divide(divisor.radial, Constants.DEFAULT_MATH_CONTEXT), angular.subtract(divisor.angular));
    }

    @Override
    public BigPolar pow(final int exponent) {
        return new BigPolar(radial.pow(exponent), BigDecimal.valueOf(exponent).multiply(angular));
    }

    @Override
    public BigPolar reciprocal() {
        return new BigPolar(BigDecimal.ONE.divide(radial, Constants.DEFAULT_MATH_CONTEXT), angular.negate());
    }

    @Override
    public BigPolar negate() {
        return new BigPolar(radial, normalizeAngle(angular.add(Constants.BIG_PI)));
    }

    @Override
    public BigComplex toComplex() {
        return new BigComplex(
                radial.multiply(SinAndCosCalculator.cos(angular)), radial.multiply(SinAndCosCalculator.sin(angular)));
    }

    private BigDecimal normalizeAngle(final BigDecimal angle) {
        final var mod = BigDecimal.valueOf(2L).multiply(Constants.BIG_PI);
        return angle.remainder(mod).add(mod).remainder(mod);
    }
}
