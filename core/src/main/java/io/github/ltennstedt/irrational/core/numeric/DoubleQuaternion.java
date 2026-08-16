package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.Divisible;
import io.github.ltennstedt.irrational.core.Exponentiable;
import io.github.ltennstedt.irrational.core.util.Doubles;
import java.util.Objects;

/**
 * Immutable implementation of a quaternion based on double
 *
 * @param w w
 * @param x x
 * @param y y
 * @param z z
 */
public record DoubleQuaternion(double w, double x, double y, double z)
        implements Divisible<DoubleQuaternion, DoubleQuaternion>,
                Exponentiable<DoubleQuaternion, DoubleQuaternion>,
                Complex<DoubleQuaternion, DoubleQuaternion>,
                io.github.ltennstedt.irrational.core.Additive<DoubleQuaternion>,
                io.github.ltennstedt.irrational.core.Subtractable<DoubleQuaternion>,
                io.github.ltennstedt.irrational.core.Multipliable<DoubleQuaternion> {
    /** 0 */
    public static final DoubleQuaternion ZERO = new DoubleQuaternion(0D, 0D, 0D, 0D);

    /** 1 */
    public static final DoubleQuaternion ONE = new DoubleQuaternion(1D, 0D, 0D, 0D);

    /** i */
    public static final DoubleQuaternion I = new DoubleQuaternion(0D, 1D, 0D, 0D);

    /** j */
    public static final DoubleQuaternion J = new DoubleQuaternion(0D, 0D, 1D, 0D);

    /** k */
    public static final DoubleQuaternion K = new DoubleQuaternion(0D, 0D, 0D, 1D);

    /**
     * Canonical constructor
     *
     * @param w w
     * @param x x
     * @param y y
     * @param z z
     * @throws ArithmeticException when w is NaN or infinite
     * @throws ArithmeticException when x is NaN or infinite
     * @throws ArithmeticException when y is NaN or infinite
     * @throws ArithmeticException when z is NaN or infinite
     */
    public DoubleQuaternion {
        w = Doubles.normalizeZero(Doubles.check(w, "w"));
        x = Doubles.normalizeZero(Doubles.check(x, "x"));
        y = Doubles.normalizeZero(Doubles.check(y, "y"));
        z = Doubles.normalizeZero(Doubles.check(z, "z"));
    }

    @Override
    public boolean isInvertible() {
        return !isZero();
    }

    @Override
    public boolean isZero() {
        return Doubles.isNear(w, 0D) && Doubles.isNear(x, 0D) && Doubles.isNear(y, 0D) && Doubles.isNear(z, 0D);
    }

    @Override
    public DoubleQuaternion add(final DoubleQuaternion summand) {
        Objects.requireNonNull(summand, "summand");
        return new DoubleQuaternion(w + summand.w, x + summand.x, y + summand.y, z + summand.z);
    }

    @Override
    public DoubleQuaternion subtract(final DoubleQuaternion subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new DoubleQuaternion(w - subtrahend.w, x - subtrahend.x, y - subtrahend.y, z - subtrahend.z);
    }

    @Override
    public DoubleQuaternion multiply(final DoubleQuaternion multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new DoubleQuaternion(
                w * multiplier.w - x * multiplier.x - y * multiplier.y - z * multiplier.z,
                w * multiplier.x + x * multiplier.w + y * multiplier.z - z * multiplier.y,
                w * multiplier.y - x * multiplier.z + y * multiplier.w + z * multiplier.x,
                w * multiplier.z + x * multiplier.y - y * multiplier.x + z * multiplier.w);
    }

    @Override
    public DoubleQuaternion divide(final DoubleQuaternion divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return multiply(divisor.reciprocal());
    }

    @Override
    public DoubleQuaternion negate() {
        return new DoubleQuaternion(-w, -x, -y, -z);
    }

    @Override
    public DoubleQuaternion pow(final int exponent) {
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
    public DoubleQuaternion reciprocal() {
        if (!isInvertible()) {
            throw new ArithmeticException("this must be invertible but was " + this);
        }
        final var norm = norm();
        return new DoubleQuaternion(w / norm, -x / norm, -y / norm, -z / norm);
    }

    @Override
    public DoubleQuaternion conjugate() {
        return new DoubleQuaternion(w, -x, -y, -z);
    }

    /**
     * Returns the norm
     *
     * @return norm
     */
    public double norm() {
        return w * w + x * x + y * y + z * z;
    }

    /**
     * Returns the absolute value
     *
     * @return absolute value
     */
    public double abs() {
        return StrictMath.sqrt(norm());
    }
}
