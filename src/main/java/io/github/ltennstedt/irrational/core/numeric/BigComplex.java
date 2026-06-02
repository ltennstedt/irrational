package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.util.AtanCalculator;
import io.github.ltennstedt.irrational.core.util.Constants;
import io.github.ltennstedt.irrational.core.util.SinAndCosCalculator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * Immutable implementation of a complex number based on {@link BigDecimal}
 *
 * @param real real part
 * @param imaginary imaginary part
 */
public record BigComplex(BigDecimal real, BigDecimal imaginary)
        implements MathContextNumeric<BigComplex>, Complex<BigComplex, BigPolar> {
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
     * @param polar {@link BigPolar}
     * @return {@link BigComplex}
     * @throws NullPointerException when polar is null
     */
    public static BigComplex ofPolar(final BigPolar polar) {
        Objects.requireNonNull(polar, "polar");
        if (polar.radius().signum() == 0) {
            return ZERO;
        }
        return new BigComplex(
                polar.radius().multiply(SinAndCosCalculator.cos(polar.angle())),
                polar.radius().multiply(SinAndCosCalculator.sin(polar.angle())));
    }

    /**
     * Static factory method
     *
     * @param polar {@link BigPolar}
     * @param mathContext {@link MathContext}
     * @return {@link BigComplex}
     * @throws NullPointerException when polar is null
     * @throws NullPointerException when mathContext is null
     */
    public static BigComplex ofPolar(final BigPolar polar, final MathContext mathContext) {
        Objects.requireNonNull(polar, "polar");
        Objects.requireNonNull(mathContext, "mathContext");
        if (polar.radius().signum() == 0) {
            return ZERO;
        }
        return new BigComplex(
                polar.radius().multiply(SinAndCosCalculator.cos(polar.angle(), mathContext), mathContext),
                polar.radius().multiply(SinAndCosCalculator.sin(polar.angle(), mathContext), mathContext));
    }

    @Override
    public boolean isInvertible() {
        return !isZero();
    }

    @Override
    public boolean isZero() {
        return real.signum() == 0 && imaginary.signum() == 0;
    }

    @Override
    public BigComplex negate() {
        return new BigComplex(real.negate(), imaginary.negate());
    }

    @Override
    public BigComplex negate(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigComplex(real.negate(mathContext), imaginary.negate(mathContext));
    }

    @Override
    public BigComplex add(final BigComplex summand) {
        Objects.requireNonNull(summand, "summand");
        return new BigComplex(real.add(summand.real), imaginary.add(summand.imaginary));
    }

    @Override
    public BigComplex add(final BigComplex summand, final MathContext mathContext) {
        Objects.requireNonNull(summand, "summand");
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigComplex(real.add(summand.real, mathContext), imaginary.add(summand.imaginary, mathContext));
    }

    @Override
    public BigComplex subtract(final BigComplex subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new BigComplex(real.subtract(subtrahend.real), imaginary.subtract(subtrahend.imaginary));
    }

    @Override
    public BigComplex subtract(final BigComplex subtrahend, final MathContext mathContext) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigComplex(
                real.subtract(subtrahend.real, mathContext), imaginary.subtract(subtrahend.imaginary, mathContext));
    }

    @Override
    public BigComplex multiply(final BigComplex multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new BigComplex(
                real.multiply(multiplier.real).subtract(imaginary.multiply(multiplier.imaginary)),
                real.multiply(multiplier.imaginary).add(imaginary.multiply(multiplier.real)));
    }

    @Override
    public BigComplex multiply(final BigComplex multiplier, final MathContext mathContext) {
        Objects.requireNonNull(multiplier, "multiplier");
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigComplex(
                real.multiply(multiplier.real, mathContext)
                        .subtract(imaginary.multiply(multiplier.imaginary, mathContext), mathContext),
                real.multiply(multiplier.imaginary, mathContext)
                        .add(imaginary.multiply(multiplier.real, mathContext), mathContext));
    }

    @Override
    public BigComplex divide(final BigComplex divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        if (divisor.real.abs().compareTo(divisor.imaginary.abs()) >= 0) {
            final var r = divisor.imaginary.divide(divisor.real, Constants.DEFAULT_MATH_CONTEXT);
            final var d = divisor.real.add(divisor.imaginary.multiply(r));
            return new BigComplex(
                    real.add(imaginary.multiply(r)).divide(d, Constants.DEFAULT_MATH_CONTEXT),
                    imaginary.subtract(real.multiply(r)).divide(d, Constants.DEFAULT_MATH_CONTEXT));
        } else {
            final var r = divisor.real.divide(divisor.imaginary, Constants.DEFAULT_MATH_CONTEXT);
            final var d = divisor.imaginary.add(divisor.real.multiply(r));
            return new BigComplex(
                    real.multiply(r).add(imaginary).divide(d, Constants.DEFAULT_MATH_CONTEXT),
                    imaginary.multiply(r).subtract(real).divide(d, Constants.DEFAULT_MATH_CONTEXT));
        }
    }

    @Override
    public BigComplex divide(final BigComplex divisor, final MathContext mathContext) {
        Objects.requireNonNull(divisor, "divisor");
        Objects.requireNonNull(mathContext, "mathContext");
        if (!divisor.isInvertible()) {
            throw new ArithmeticException("divisor must be invertible but was " + divisor);
        }
        if (divisor.real.abs(mathContext).compareTo(divisor.imaginary.abs(mathContext)) >= 0) {
            final var r = divisor.imaginary.divide(divisor.real, mathContext);
            final var d = divisor.real.add(divisor.imaginary.multiply(r, mathContext), mathContext);
            return new BigComplex(
                    real.add(imaginary.multiply(r, mathContext), mathContext).divide(d, mathContext),
                    imaginary
                            .subtract(real.multiply(r, mathContext), mathContext)
                            .divide(d, mathContext));
        } else {
            final var r = divisor.real.divide(divisor.imaginary, mathContext);
            final var d = divisor.imaginary.add(divisor.real.multiply(r, mathContext), mathContext);
            return new BigComplex(
                    real.multiply(r, mathContext).add(imaginary, mathContext).divide(d, mathContext),
                    imaginary
                            .multiply(r, mathContext)
                            .subtract(real, mathContext)
                            .divide(d, mathContext));
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

    @Override
    public BigComplex pow(final int exponent, final MathContext mathContext) {
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
    public BigComplex reciprocal(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        if (!isInvertible()) {
            throw new ArithmeticException("this must be invertible but was " + this);
        }
        final var norm = norm();
        return new BigComplex(
                real.divide(norm, mathContext), imaginary.negate(mathContext).divide(norm, mathContext));
    }

    @Override
    public BigComplex conjugate() {
        return new BigComplex(real, imaginary.negate());
    }

    /**
     * Returns the conjugated complex number
     *
     * @param mathContext {@link MathContext}
     * @return conjugated complex number
     * @throws NullPointerException when mathContext is null
     */
    public BigComplex conjugate(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return new BigComplex(real, imaginary.negate(mathContext));
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
     * Returns the norm
     *
     * @param mathContext {@link MathContext}
     * @return norm
     * @throws NullPointerException when mathContext is null
     */
    public BigDecimal norm(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return real.multiply(real, mathContext).add(imaginary.multiply(imaginary, mathContext), mathContext);
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
     * Returns the absolute value
     *
     * @param mathContext {@link MathContext}
     * @return absolute value
     * @throws NullPointerException when mathContext is null
     */
    public BigDecimal abs(final MathContext mathContext) {
        return norm(mathContext).sqrt(mathContext);
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
     * Returns the argument
     *
     * @param mathContext {@link MathContext}
     * @return argument
     * @throws NullPointerException when mathContext is null
     */
    public BigDecimal arg(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return AtanCalculator.atan2(imaginary, real, mathContext);
    }

    @Override
    public BigPolar toPolar() {
        return BigPolar.ofComplex(this);
    }

    /**
     * Returns this as polar form
     *
     * @param mathContext {@link MathContext}
     * @return {@link BigPolar}
     * @throws NullPointerException when mathContext is null
     * @throws ArithmeticException when radius is 0
     */
    public BigPolar toPolar(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return BigPolar.ofComplex(this, mathContext);
    }
}
