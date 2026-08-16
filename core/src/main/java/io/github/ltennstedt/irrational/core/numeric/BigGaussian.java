package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.MathContextDivisible;
import io.github.ltennstedt.irrational.core.MathContextExponentiable;
import io.github.ltennstedt.irrational.core.util.AtanCalculator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;
import java.util.Set;

/**
 * Immutable implementation of a Gaussian number based on {@link BigInteger}
 *
 * @param real real part
 * @param imaginary imaginary part
 */
public record BigGaussian(BigInteger real, BigInteger imaginary)
        implements Complex<BigGaussian, BigComplex>,
                MathContextDivisible<BigGaussian, BigComplex>,
                MathContextExponentiable<BigGaussian, BigComplex> {
    /** 0 */
    public static final BigGaussian ZERO = new BigGaussian(BigInteger.ZERO, BigInteger.ZERO);

    /** 1 */
    public static final BigGaussian ONE = new BigGaussian(BigInteger.ONE, BigInteger.ZERO);

    /** i */
    public static final BigGaussian I = new BigGaussian(BigInteger.ZERO, BigInteger.ONE);

    /** -1 */
    public static final BigGaussian MINUS_ONE = new BigGaussian(BigInteger.valueOf(-1L), BigInteger.ZERO);

    /** -i */
    public static final BigGaussian MINUS_I = new BigGaussian(BigInteger.ZERO, BigInteger.valueOf(-1L));

    /** Units */
    public static final Set<BigGaussian> UNITS = Set.of(ONE, I, MINUS_ONE, MINUS_I);

    /**
     * Canonical constructor
     *
     * @param real real part
     * @param imaginary imaginary part
     * @throws NullPointerException when real is null
     * @throws NullPointerException when imaginary is null
     */
    public BigGaussian {
        Objects.requireNonNull(real, "real");
        Objects.requireNonNull(imaginary, "imaginary");
    }

    @Override
    public boolean isInvertible() {
        return !isZero();
    }

    @Override
    public boolean isZero() {
        return real.signum() == 0 && imaginary.signum() == 0;
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
    public BigGaussian add(final BigGaussian summand) {
        Objects.requireNonNull(summand, "summand");
        return new BigGaussian(real.add(summand.real), imaginary.add(summand.imaginary));
    }

    @Override
    public BigGaussian subtract(final BigGaussian subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new BigGaussian(real.subtract(subtrahend.real), imaginary.subtract(subtrahend.imaginary));
    }

    @Override
    public BigGaussian multiply(final BigGaussian multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new BigGaussian(
                real.multiply(multiplier.real).subtract(imaginary.multiply(multiplier.imaginary)),
                real.multiply(multiplier.imaginary).add(imaginary.multiply(multiplier.real)));
    }

    @Override
    public BigComplex divide(final BigGaussian divisor, final MathContext mathContext) {
        Objects.requireNonNull(divisor, "divisor");
        return toComplex().divide(divisor.toComplex(), mathContext);
    }

    @Override
    public BigGaussian negate() {
        return new BigGaussian(real.negate(), imaginary.negate());
    }

    @Override
    public BigComplex pow(final int exponent, final MathContext mathContext) {
        return toComplex().pow(exponent, mathContext);
    }

    @Override
    public BigComplex reciprocal(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return toComplex().reciprocal(mathContext);
    }

    @Override
    public BigGaussian conjugate() {
        return new BigGaussian(real, imaginary.negate());
    }

    /**
     * Returns the norm
     *
     * @return norm
     */
    public BigInteger norm() {
        return real.multiply(real).add(imaginary.multiply(imaginary));
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
        return new BigDecimal(norm()).sqrt(mathContext);
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
        return AtanCalculator.atan2(new BigDecimal(imaginary), new BigDecimal(real), mathContext);
    }

    /**
     * Returns this as complex number
     *
     * @return {@link Complex}
     */
    public BigComplex toComplex() {
        return new BigComplex(new BigDecimal(real), new BigDecimal(imaginary));
    }
}
