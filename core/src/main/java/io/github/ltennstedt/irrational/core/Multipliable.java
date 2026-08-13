package io.github.ltennstedt.irrational.core;

import io.github.ltennstedt.irrational.core.numeric.Numeric;

/**
 * Interface for types for which multiplication is defined
 *
 * @param <M> type
 */
public sealed interface Multipliable<M> permits Numeric {
    /**
     * Indicates if this is invertible
     *
     * @return boolean
     */
    boolean isInvertible();

    /**
     * Returns the product of this and the multiplier
     *
     * @param multiplier multiplier
     * @return product
     * @throws NullPointerException when multiplier is null
     */
    M multiply(M multiplier);
}
