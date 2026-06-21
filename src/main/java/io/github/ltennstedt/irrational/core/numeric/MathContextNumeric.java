package io.github.ltennstedt.irrational.core.numeric;

import java.math.MathContext;

/**
 * Base interface for numbers
 *
 * @param <N> type of the number
 */
public sealed interface MathContextNumeric<N extends MathContextNumeric<N>> permits BigComplex, BigPolar {
    /**
     * Returns the negated number
     *
     * @param mathContext {@link MathContext}
     * @return negated number
     * @throws NullPointerException when mathContext is null
     */
    N negate(MathContext mathContext);

    /**
     * Returns the sum of this and the summand
     *
     * @param summand summand
     * @param mathContext {@link MathContext}
     * @return sum
     * @throws NullPointerException when summand is null
     * @throws NullPointerException when mathContext is null
     */
    N add(N summand, MathContext mathContext);

    /**
     * Returns the difference of this and the subtrahend
     *
     * @param subtrahend subtrahend
     * @param mathContext {@link MathContext}
     * @return difference
     * @throws NullPointerException when subtrahend is null
     * @throws NullPointerException when mathContext is null
     */
    N subtract(N subtrahend, MathContext mathContext);

    /**
     * Returns the product of this and the multiplier
     *
     * @param multiplier multiplier
     * @param mathContext {@link MathContext}
     * @return product
     * @throws NullPointerException when multiplier is null
     * @throws NullPointerException when mathContext is null
     */
    N multiply(N multiplier, MathContext mathContext);

    /**
     * Returns the quotient of this and the divisor
     *
     * @param divisor divisor
     * @param mathContext {@link MathContext}
     * @return quotient
     * @throws NullPointerException when divisor is null
     * @throws NullPointerException when mathContext is null
     * @throws ArithmeticException when divisor is not invertible
     */
    N divide(N divisor, MathContext mathContext);

    /**
     * Returns this raised by the power of exponent
     *
     * @param exponent exponent
     * @param mathContext {@link MathContext}
     * @return power
     * @throws NullPointerException when mathContext is null
     */
    N pow(int exponent, MathContext mathContext);

    /**
     * Returns the reciprocal
     *
     * @param mathContext {@link MathContext}
     * @return reciprocal
     * @throws NullPointerException when mathContext is null
     * @throws ArithmeticException when this is not invertible
     */
    N reciprocal(MathContext mathContext);
}
