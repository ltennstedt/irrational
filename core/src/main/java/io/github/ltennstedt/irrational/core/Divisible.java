package io.github.ltennstedt.irrational.core;

/**
 * Interface for types for which division is defined
 *
 * @param <D> type of the {@link Divisible}
 * @param <Q> type of the quotient
 */
public interface Divisible<D extends Divisible<D, Q>, Q extends Divisible<Q, Q>> {
    /**
     * Returns the quotient of this and the divisor
     *
     * @param divisor divisor
     * @return quotient
     * @throws NullPointerException when divisor is null
     * @throws ArithmeticException when divisor is not invertible
     */
    Q divide(D divisor);

    /**
     * Returns the reciprocal
     *
     * @return reciprocal
     * @throws ArithmeticException when this is not invertible
     */
    Q reciprocal();
}
