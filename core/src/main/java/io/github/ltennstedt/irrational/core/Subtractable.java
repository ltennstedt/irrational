package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.linear.Vector;

/**
 * Interface for types for which subtraction is defined
 *
 * @param <S> type of the {@link Subtractable}
 */
public sealed interface Subtractable<S extends Subtractable<S>>
        permits Vector,
                io.github.ltennstedt.irrational.core.numeric.Complex,
                io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion,
                io.github.ltennstedt.irrational.core.numeric.Rational {
    /**
     * Returns the difference of this and the subtrahend
     *
     * @param subtrahend subtrahend
     * @return difference
     * @throws NullPointerException when subtrahend is null
     */
    S subtract(S subtrahend);

    /**
     * Returns the negated of this
     *
     * @return negated
     */
    S negate();
}
