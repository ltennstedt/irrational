package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.numeric.BigComplex;
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion;
import java.math.MathContext;

/**
 * Interface for types for which subtraction with {@link MathContext} is defined
 *
 * @param <S> type of the {@link MathContextSubtractable}
 */
public sealed interface MathContextSubtractable<S extends MathContextSubtractable<S>>
        permits BigComplex, BigQuaternion {
    /**
     * Returns the negated number
     *
     * @param mathContext {@link MathContext}
     * @return negated number
     * @throws NullPointerException when mathContext is null
     */
    S negate(MathContext mathContext);

    /**
     * Returns the difference of this and the subtrahend
     *
     * @param subtrahend subtrahend
     * @param mathContext {@link MathContext}
     * @return difference
     * @throws NullPointerException when subtrahend is null
     * @throws NullPointerException when mathContext is null
     */
    S subtract(S subtrahend, MathContext mathContext);
}
