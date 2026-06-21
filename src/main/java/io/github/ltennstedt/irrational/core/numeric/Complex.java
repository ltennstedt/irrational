package io.github.ltennstedt.irrational.core.numeric;

/**
 * Base interface for complex numbers
 *
 * @param <C> type of the complex number
 */
public sealed interface Complex<C extends Complex<C, Q, P>, Q extends Complex<Q, Q, P>, P extends Polar<P, Q>>
        extends Numeric<C, Q> permits BigComplex, BigGaussian, DoubleComplex, LongGaussian {
    /**
     * Returns the conjugated complex number
     *
     * @return conjugated complex number
     */
    C conjugate();

    /**
     * Returns this as polar form
     *
     * @return {@link Polar}
     * @throws ArithmeticException when radius is 0
     */
    P toPolar();
}
