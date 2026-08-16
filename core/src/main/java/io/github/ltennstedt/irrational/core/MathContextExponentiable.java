package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.numeric.BigComplex;
import io.github.ltennstedt.irrational.core.numeric.BigGaussian;
import java.math.MathContext;

/**
 * Interface for types for which exponentiation with {@link MathContext} is defined
 *
 * @param <E> type of the {@link MathContextExponentiable}
 */
public sealed interface MathContextExponentiable<
                E extends MathContextExponentiable<E, Q>, Q extends MathContextExponentiable<Q, Q>>
        permits BigComplex, BigGaussian {
    /**
     * Returns this raised by the power of exponent
     *
     * @param exponent exponent
     * @param mathContext {@link MathContext}
     * @return power
     * @throws NullPointerException when mathContext is null
     */
    Q pow(int exponent, MathContext mathContext);
}
