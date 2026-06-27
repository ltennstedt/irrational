package io.github.ltennstedt.irrational.core.numeric;

/**
 * Base interface for numbers based on primitive types
 *
 * @param <N> type of the number
 * @param <Q> type of the quotient
 */
public interface PrimitiveNumeric<N extends PrimitiveNumeric<N, Q>, Q extends PrimitiveNumeric<Q, Q>> {
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
