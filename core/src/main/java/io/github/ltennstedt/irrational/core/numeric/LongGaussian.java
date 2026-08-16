package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.Divisible;
import io.github.ltennstedt.irrational.core.Exponentiable;
import java.util.Objects;
import java.util.Set;

/**
 * Immutable implementation of a Gaussian number based on long
 *
 * @param real real part
 * @param imaginary imaginary part
 */
public record LongGaussian(long real, long imaginary)
        implements Complex<LongGaussian, DoubleComplex>,
                Divisible<LongGaussian, DoubleComplex>,
                Exponentiable<LongGaussian, DoubleComplex> {
    /** 0 */
    public static final LongGaussian ZERO = new LongGaussian(0L, 0L);

    /** 1 */
    public static final LongGaussian ONE = new LongGaussian(1L, 0L);

    /** i */
    public static final LongGaussian I = new LongGaussian(0L, 1L);

    /** -1 */
    public static final LongGaussian MINUS_ONE = new LongGaussian(-1L, 0L);

    /** -i */
    public static final LongGaussian MINUS_I = new LongGaussian(0L, -1L);

    /** Units */
    public static final Set<LongGaussian> UNITS = Set.of(ONE, I, MINUS_ONE, MINUS_I);

    @Override
    public boolean isInvertible() {
        return !isZero();
    }

    @Override
    public boolean isZero() {
        return real == 0L && imaginary == 0L;
    }

    /**
     * Returns if this is a unit
     *
     * @return boolean
     */
    public boolean isUnit() {
        return UNITS.contains(this);
    }

    @Override
    public LongGaussian add(final LongGaussian summand) {
        Objects.requireNonNull(summand, "summand");
        return new LongGaussian(Math.addExact(real, summand.real), Math.addExact(imaginary, summand.imaginary));
    }

    @Override
    public LongGaussian subtract(final LongGaussian subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new LongGaussian(
                Math.subtractExact(real, subtrahend.real), Math.subtractExact(imaginary, subtrahend.imaginary));
    }

    @Override
    public LongGaussian multiply(final LongGaussian multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new LongGaussian(
                Math.subtractExact(
                        Math.multiplyExact(real, multiplier.real), Math.multiplyExact(imaginary, multiplier.imaginary)),
                Math.addExact(
                        Math.multiplyExact(real, multiplier.imaginary),
                        Math.multiplyExact(imaginary, multiplier.real)));
    }

    @Override
    public DoubleComplex divide(final LongGaussian divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        if (Double.compare(StrictMath.abs(divisor.real), StrictMath.abs(divisor.imaginary)) >= 0) {
            final var r = (double) divisor.imaginary / divisor.real;
            final var d = divisor.real + divisor.imaginary * r;
            return new DoubleComplex((real + imaginary * r) / d, (imaginary - real * r) / d);
        } else {
            final var r = (double) divisor.real / divisor.imaginary;
            final var d = divisor.imaginary + divisor.real * r;
            return new DoubleComplex((real * r + imaginary) / d, (imaginary * r - real) / d);
        }
    }

    @Override
    public LongGaussian negate() {
        return new LongGaussian(Math.negateExact(real), Math.negateExact(imaginary));
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
            return DoubleComplex.ONE;
        }
        if (exponent == 1) {
            return toComplex();
        }
        if (isZero()) {
            return DoubleComplex.ZERO;
        }
        final var half = pow(exponent / 2);
        final var squared = half.multiply(half);
        return (exponent & 1) == 0 ? squared : toComplex().multiply(squared);
    }

    @Override
    public DoubleComplex reciprocal() {
        if (!isInvertible()) {
            throw new ArithmeticException("this must be invertible but was " + this);
        }
        final var denominator = norm();
        return new DoubleComplex(real / denominator, Math.negateExact(imaginary) / denominator);
    }

    @Override
    public LongGaussian conjugate() {
        return new LongGaussian(real, Math.negateExact(imaginary));
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
     * Returns this as {@link DoubleComplex}
     *
     * @return {@link DoubleComplex}
     */
    public DoubleComplex toComplex() {
        return new DoubleComplex(real, imaginary);
    }
}
