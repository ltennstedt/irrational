package io.github.ltennstedt.irrational.core.numeric;

import java.math.MathContext;

/**
 * Base interface for numbers based on {@link java.math.BigDecimal}
 *
 * @param <N> type of the number
 */
public sealed interface MathContextNumeric<N extends MathContextNumeric<N>> permits BigComplex, BigQuaternion {
    /**
     * Returns the negated number
     *
     * @param mathContext {@link MathContext}
     * @return negated number
     * @throws NullPointerException when mathContext is null
     */
    N negate(MathContext mathContext);

    /**
     * Returns the sum of this and the summand
     *
     * @param summand summand
     * @param mathContext {@link MathContext}
     * @return sum
     * @throws NullPointerException when summand is null
     * @throws NullPointerException when mathContext is null
     */
    N add(N summand, MathContext mathContext);

    /**
     * Returns the difference of this and the subtrahend
     *
     * @param subtrahend subtrahend
     * @param mathContext {@link MathContext}
     * @return difference
     * @throws NullPointerException when subtrahend is null
     * @throws NullPointerException when mathContext is null
     */
    N subtract(N subtrahend, MathContext mathContext);

    /**
     * Returns the product of this and the multiplier
     *
     * @param multiplier multiplier
     * @param mathContext {@link MathContext}
     * @return product
     * @throws NullPointerException when multiplier is null
     * @throws NullPointerException when mathContext is null
     */
    N multiply(N multiplier, MathContext mathContext);
}
