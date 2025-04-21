package io.github.ltennstedt.irrational.numeric;

/**
 * Base class for numbers
 *
 * @param <N> type of the number
 */
public sealed interface Numeric<N extends Numeric<N>> permits Rational {
    /**
     * Indicates if this is invertible
     *
     * @return boolean
     */
    boolean isInvertible();

    /**
     * Indicates if this is not invertible
     *
     * @return boolean
     */
    default boolean isNotInvertible() {
        return !isInvertible();
    }

    /**
     * Indicates if this is an element of the ring of integers
     *
     * @return boolean
     */
    boolean isInteger();

    /**
     * Indicates if this is not an element of the ring of integers
     *
     * @return boolean
     */
    default boolean isNotInteger() {
        return !isInteger();
    }

    /**
     * Indicates if this is 0
     *
     * @return boolean
     */
    boolean isZero();

    /**
     * Indicates if this is 1
     *
     * @return boolean
     */
    boolean isOne();

    /**
     * Returns the negated number
     *
     * @return negated number
     */
    N negate();

    /**
     * Returns the absolute value
     *
     * @return absolute value
     */
    N abs();

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
     * @throws IllegalArgumentException when divisor is not invertible
     */
    N divide(N divisor);

    /**
     * Returns the inverted number
     *
     * @return inverted number
     * @throws IllegalStateException when this is not invertible
     */
    N invert();

    /**
     * Returns this by the power of exponent
     *
     * @param exponent exponent
     * @return power
     */
    N power(int exponent);
}
