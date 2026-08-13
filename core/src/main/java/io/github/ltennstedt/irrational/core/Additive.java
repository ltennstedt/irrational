package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.linear.Vector;
import io.github.ltennstedt.irrational.core.numeric.Numeric;

/**
 * Interface for types for which addition is defined
 *
 * @param <A> type
 */
public sealed interface Additive<A> permits Numeric, Vector {
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
