package io.github.ltennstedt.irrational.core.linear;

import io.github.ltennstedt.irrational.core.Additive;
import io.github.ltennstedt.irrational.core.Subtractable;

/**
 * Base interface for vectors
 *
 * @param <V> type of the vector
 * @param <E> type of the entry
 */
public sealed interface Vector<V extends Vector<V, E>, E> extends Additive<V>, Subtractable<V>
        permits BigIntegerVector, LongVector {
    /**
     * Returns the size
     *
     * @return size
     */
    int size();

    /**
     * Returns if this is a standard basis vector
     *
     * @return boolean
     */
    boolean isStandardBasisVector();

    /**
     * Returns if this is a unit vector
     *
     * @return boolean
     */
    boolean isUnitVector();

    /**
     * Returns the indices
     *
     * @return indices
     */
    int[] indices();

    /**
     * Returns if this contains index
     *
     * @param index index
     * @return boolean
     */
    boolean containsIndex(int index);

    /**
     * Returns if this contains entry
     *
     * @param entry entry
     * @return boolean
     * @throws NullPointerException when entry is null
     */
    boolean containsEntry(E entry);
}
