package io.github.ltennstedt.irrational.core.numeric;

/**
 * Base interface for complex numbers
 *
 * @param <C> type of the complex number
 */
public sealed interface Complex<C extends Complex<C, Q>, Q extends Complex<Q, Q>> extends Numeric<C, Q>
        permits BigComplex, BigGaussian, DoubleComplex, LongGaussian {
    /**
     * Returns the conjugated complex number
     *
     * @return conjugated complex number
     */
    C conjugate();
}
