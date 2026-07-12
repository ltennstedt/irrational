package io.github.ltennstedt.irrational.core.numeric;

import java.math.MathContext;

/**
 * Base interface for numbers based on {@link java.math.BigInteger} or {@link java.math.BigDecimal}
 *
 * @param <N> type of the number
 * @param <Q> type of the quotient
 */
public interface BigNumeric<N extends BigNumeric<N, Q>, Q extends BigNumeric<Q, Q>> {
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
    Q divide(N divisor, MathContext mathContext);

    /**
     * Returns this raised by the power of exponent
     *
     * @param exponent exponent
     * @param mathContext {@link MathContext}
     * @return power
     * @throws NullPointerException when mathContext is null
     */
    Q pow(int exponent, MathContext mathContext);

    /**
     * Returns the reciprocal
     *
     * @param mathContext {@link MathContext}
     * @return reciprocal
     * @throws NullPointerException when mathContext is null
     * @throws ArithmeticException when this is not invertible
     */
    Q reciprocal(MathContext mathContext);
}
