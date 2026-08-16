package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.linear.Vector;

/**
 * Interface for types for which addition is defined
 *
 * @param <A> type of the {@link Additive}
 */
public sealed interface Additive<A>
        permits Vector,
                io.github.ltennstedt.irrational.core.numeric.Complex,
                io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion,
                io.github.ltennstedt.irrational.core.numeric.Rational {
    /**
     * Indicates if this is 0
     *
     * @return boolean
     */
    boolean isZero();

    /**
     * Returns the sum of this and the summand
     *
     * @param summand summand
     * @return sum
     * @throws NullPointerException when summand is null
     */
    A add(A summand);
}
