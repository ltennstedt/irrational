package io.github.ltennstedt.irrational.core.numeric;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * Immutable implementation of a quaternion based on BigDecimal
 *
 * @param w w
 * @param x x
 * @param y y
 * @param z z
 */
public record BigQuaternion(BigDecimal w, BigDecimal x, BigDecimal y, BigDecimal z)
        implements BigNumeric<BigQuaternion, BigQuaternion>,
                MathContextNumeric<BigQuaternion>,
                Complex<BigQuaternion, BigQuaternion> {
    /** 0 */
    public static final BigQuaternion ZERO =
            new BigQuaternion(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    /** 1 */
    public static final BigQuaternion ONE =
            new BigQuaternion(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    /** i */
    public static final BigQuaternion I =
            new BigQuaternion(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);

    /** j */
    public static final BigQuaternion J =
            new BigQuaternion(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO);

    /** k */
    public static final BigQuaternion K =
            new BigQuaternion(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE);

    /**
     * Canonical constructor
     *
     * @param w w
     * @param x x
     * @param y y
     * @param z z
     * @throws NullPointerException when w is null
     * @throws NullPointerException when x is null
     * @throws NullPointerException when y is null
     * @throws NullPointerException when z is null
     */
    public BigQuaternion {
        Objects.requireNonNull(w, "w");
        Objects.requireNonNull(x, "x");
        Objects.requireNonNull(y, "y");
        Objects.requireNonNull(z, "z");
    }

    @Override
    public boolean isInvertible() {
        return !isZero();
    }

    @Override
    public boolean isZero() {
        return w.compareTo(BigDecimal.ZERO) == 0
                && x.compareTo(BigDecimal.ZERO) == 0
                && y.compareTo(BigDecimal.ZERO) == 0
                && z.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public BigQuaternion add(final BigQuaternion summand) {
        Objects.requireNonNull(summand, "summand");
        return new BigQuaternion(w.add(summand.w), x.add(summand.x), y.add(summand.y), z.add(summand.z));
    }

    @Override
    public BigQuaternion add(final BigQuaternion summand, final MathContext mathContext) {
        Objects.requireNonNull(summand, "summand");
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigQuaternion(
                w.add(summand.w, mathContext),
                x.add(summand.x, mathContext),
                y.add(summand.y, mathContext),
                z.add(summand.z, mathContext));
    }

    @Override
    public BigQuaternion subtract(final BigQuaternion subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new BigQuaternion(
                w.subtract(subtrahend.w), x.subtract(subtrahend.x), y.subtract(subtrahend.y), z.subtract(subtrahend.z));
    }

    @Override
    public BigQuaternion subtract(final BigQuaternion subtrahend, final MathContext mathContext) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigQuaternion(
                w.subtract(subtrahend.w, mathContext),
                x.subtract(subtrahend.x, mathContext),
                y.subtract(subtrahend.y, mathContext),
                z.subtract(subtrahend.z, mathContext));
    }

    @Override
    public BigQuaternion multiply(final BigQuaternion multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new BigQuaternion(
                w.multiply(multiplier.w)
                        .subtract(x.multiply(multiplier.x))
                        .subtract(y.multiply(multiplier.y))
                        .subtract(z.multiply(multiplier.z)),
                w.multiply(multiplier.x)
                        .add(x.multiply(multiplier.w))
                        .add(y.multiply(multiplier.z))
                        .subtract(z.multiply(multiplier.y)),
                w.multiply(multiplier.y)
                        .subtract(x.multiply(multiplier.z))
                        .add(y.multiply(multiplier.w))
                        .add(z.multiply(multiplier.x)),
                w.multiply(multiplier.z)
                        .add(x.multiply(multiplier.y))
                        .subtract(y.multiply(multiplier.x))
                        .add(z.multiply(multiplier.w)));
    }

    @Override
    public BigQuaternion multiply(final BigQuaternion multiplier, final MathContext mathContext) {
        Objects.requireNonNull(multiplier, "multiplier");
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigQuaternion(
                w.multiply(multiplier.w, mathContext)
                        .subtract(x.multiply(multiplier.x, mathContext), mathContext)
                        .subtract(y.multiply(multiplier.y, mathContext), mathContext)
                        .subtract(z.multiply(multiplier.z, mathContext), mathContext),
                w.multiply(multiplier.x, mathContext)
                        .add(x.multiply(multiplier.w, mathContext), mathContext)
                        .add(y.multiply(multiplier.z, mathContext), mathContext)
                        .subtract(z.multiply(multiplier.y, mathContext), mathContext),
                w.multiply(multiplier.y, mathContext)
                        .subtract(x.multiply(multiplier.z, mathContext), mathContext)
                        .add(y.multiply(multiplier.w, mathContext), mathContext)
                        .add(z.multiply(multiplier.x, mathContext), mathContext),
                w.multiply(multiplier.z, mathContext)
                        .add(x.multiply(multiplier.y, mathContext), mathContext)
                        .subtract(y.multiply(multiplier.x, mathContext), mathContext)
                        .add(z.multiply(multiplier.w, mathContext), mathContext));
    }

    @Override
    public BigQuaternion divide(final BigQuaternion divisor, final MathContext mathContext) {
        Objects.requireNonNull(divisor, "divisor");
        Objects.requireNonNull(mathContext, "mathContext");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        return multiply(divisor.reciprocal(mathContext), mathContext);
    }

    @Override
    public BigQuaternion negate() {
        return new BigQuaternion(w.negate(), x.negate(), y.negate(), z.negate());
    }

    @Override
    public BigQuaternion negate(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigQuaternion(
                w.negate(mathContext), x.negate(mathContext), y.negate(mathContext), z.negate(mathContext));
    }

    @Override
    public BigQuaternion pow(final int exponent, final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        if (exponent < 0) {
            if (!isInvertible()) {
                throw new ArithmeticException("this must be invertible but was " + this);
            }
            return reciprocal(mathContext).pow(StrictMath.negateExact(exponent), mathContext);
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
        final var half = pow(exponent / 2, mathContext);
        final var squared = half.multiply(half, mathContext);
        return (exponent & 1) == 0 ? squared : multiply(squared, mathContext);
    }

    @Override
    public BigQuaternion reciprocal(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        if (!isInvertible()) {
            throw new ArithmeticException("this must be invertible but was " + this);
        }
        final var norm = norm(mathContext);
        return new BigQuaternion(
                w.divide(norm, mathContext),
                x.negate(mathContext).divide(norm, mathContext),
                y.negate(mathContext).divide(norm, mathContext),
                z.negate(mathContext).divide(norm, mathContext));
    }

    @Override
    public BigQuaternion conjugate() {
        return new BigQuaternion(w, x.negate(), y.negate(), z.negate());
    }

    /**
     * Returns the conjugated complex number
     *
     * @param mathContext {@link MathContext}
     * @return conjugated complex number
     * @throws NullPointerException when mathContext is null
     */
    public BigQuaternion conjugate(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigQuaternion(w, x.negate(mathContext), y.negate(mathContext), z.negate(mathContext));
    }

    /**
     * Returns the norm
     *
     * @return norm
     */
    public BigDecimal norm() {
        return w.multiply(w).add(x.multiply(x)).add(y.multiply(y)).add(z.multiply(z));
    }

    /**
     * Returns the norm
     *
     * @param mathContext {@link MathContext}
     * @return norm
     * @throws NullPointerException when mathContext is null
     */
    public BigDecimal norm(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return w.multiply(w, mathContext)
                .add(x.multiply(x, mathContext), mathContext)
                .add(y.multiply(y, mathContext), mathContext)
                .add(z.multiply(z, mathContext), mathContext);
    }

    /**
     * Returns the absolute value
     *
     * @param mathContext {@link MathContext}
     * @return absolute value
     * @throws NullPointerException when mathContext is null
     */
    public BigDecimal abs(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return norm(mathContext).sqrt(mathContext);
    }
}
