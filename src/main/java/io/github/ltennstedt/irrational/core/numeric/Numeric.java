package io.github.ltennstedt.irrational.core.numeric;

/**
 * Base interface for numbers
 *
 * @param <N> type of the number
 */
public sealed interface Numeric<N extends Numeric<N, Q>, Q extends Numeric<Q, Q>> permits Complex, Polar, Rational {
    /**
     * Indicates if this is invertible
     *
     * @return boolean
     */
    boolean isInvertible();

    /**
     * Indicates if this is 0
     *
     * @return boolean
     */
    boolean isZero();

    /**
     * Returns the negated number
     *
     * @return negated number
     */
    N negate();

    /**
     * Returns the sum of this and the summand
     *
     * @param summand summand
     * @return sum
     * @throws NullPointerException when summand is null
     */
    N add(N summand);

    /**
     * Returns the difference of this and the subtrahend
     *
     * @param subtrahend subtrahend
     * @return difference
     * @throws NullPointerException when subtrahend is null
     */
    N subtract(N subtrahend);

    /**
     * Returns the product of this and the multiplier
     *
     * @param multiplier multiplier
     * @return product
     * @throws NullPointerException when multiplier is null
     */
    N multiply(N multiplier);

    /**
     * Returns the quotient of this and the divisor
     *
     * @param divisor divisor
     * @return quotient
     * @throws NullPointerException when divisor is null
     * @throws ArithmeticException when divisor is not invertible
     */
    Q divide(N divisor);

    /**
     * Returns this raised by the power of exponent
     *
     * @param exponent exponent
     * @return power
     */
    Q pow(int exponent);

    /**
     * Returns the reciprocal
     *
     * @return reciprocal
     * @throws ArithmeticException when this is not invertible
     */
    Q reciprocal();
}
