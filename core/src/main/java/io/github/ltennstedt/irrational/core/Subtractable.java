package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.linear.Vector;
import io.github.ltennstedt.irrational.core.numeric.Numeric;

/**
 * Interface for types for which subtraction is defined
 *
 * @param <S> type
 */
public sealed interface Subtractable<S extends Subtractable<S>> permits Numeric, Vector {
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
