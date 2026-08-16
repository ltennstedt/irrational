package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.numeric.BigComplex;
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion;
import java.math.MathContext;

/**
 * Interface for types for which addition with {@link MathContext} is defined
 *
 * @param <A> type of the {@link MathContextAdditive}
 */
public sealed interface MathContextAdditive<A extends MathContextAdditive<A>> permits BigComplex, BigQuaternion {
    /**
     * Returns the sum of this and the summand
     *
     * @param summand summand
     * @param mathContext {@link MathContext}
     * @return sum
     * @throws NullPointerException when summand is null
     * @throws NullPointerException when mathContext is null
     */
    A add(A summand, MathContext mathContext);
}
