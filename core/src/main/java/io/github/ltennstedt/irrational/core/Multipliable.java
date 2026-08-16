package io.github.ltennstedt.irrational.core;

/**
 * Interface for types for which multiplication is defined
 *
 * @param <M> type of the {@link Multipliable}
 */
public sealed interface Multipliable<M extends Multipliable<M>>
        permits io.github.ltennstedt.irrational.core.numeric.Complex,
                io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion,
                io.github.ltennstedt.irrational.core.numeric.Rational {
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
