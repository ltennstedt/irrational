package io.github.ltennstedt.irrational.core;

import java.math.MathContext;

/**
 * Interface for types for which division with {@link MathContext} is defined
 *
 * @param <D> type of the {@link MathContextDivisible}
 * @param <Q> type of the quotient
 */
public interface MathContextDivisible<D extends MathContextDivisible<D, Q>, Q extends MathContextDivisible<Q, Q>> {
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
    Q divide(D divisor, MathContext mathContext);

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
