package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.numeric.DoubleComplex;
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion;
import io.github.ltennstedt.irrational.core.numeric.LongGaussian;
import io.github.ltennstedt.irrational.core.numeric.Rational;

/**
 * Interface for types for which exponentiation is defined
 *
 * @param <E> type of the {@link Exponentiable}
 */
public sealed interface Exponentiable<E extends Exponentiable<E, Q>, Q extends Exponentiable<Q, Q>>
        permits DoubleComplex, DoubleQuaternion, LongGaussian, Rational {
    /**
     * Returns this raised by the power of exponent
     *
     * @param exponent exponent
     * @return power
     */
    Q pow(int exponent);
}
