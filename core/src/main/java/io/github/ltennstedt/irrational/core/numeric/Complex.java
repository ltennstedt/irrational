package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.Additive;
import io.github.ltennstedt.irrational.core.Multipliable;
import io.github.ltennstedt.irrational.core.Subtractable;

/**
 * Base interface for complex numbers
 *
 * @param <C> type of the complex number
 */
public sealed interface Complex<C extends Complex<C, Q>, Q extends Complex<Q, Q>>
        extends Additive<C>, Subtractable<C>, Multipliable<C>
        permits BigComplex, BigGaussian, BigQuaternion, DoubleComplex, DoubleQuaternion, LongGaussian {
    /**
     * Returns the conjugated complex number
     *
     * @return conjugated complex number
     */
    C conjugate();
}
