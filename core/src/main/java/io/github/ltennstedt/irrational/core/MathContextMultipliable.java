package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.numeric.BigComplex;
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion;
import java.math.MathContext;

/**
 * Interface for types for which multiplication with {@link MathContext} is defined
 *
 * @param <M> type of the {@link MathContextMultipliable}
 */
public sealed interface MathContextMultipliable<
                M extends MathContextMultipliable<M, Q>, Q extends MathContextMultipliable<Q, Q>>
        permits BigComplex, BigQuaternion {
    /**
     * Returns the product of this and the multiplier
     *
     * @param multiplier multiplier
     * @param mathContext {@link MathContext}
     * @return product
     * @throws NullPointerException when multiplier is null
     * @throws NullPointerException when mathContext is null
     */
    M multiply(M multiplier, MathContext mathContext);

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
