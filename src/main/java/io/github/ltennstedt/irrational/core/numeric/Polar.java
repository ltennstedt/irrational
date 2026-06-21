package io.github.ltennstedt.irrational.core.numeric;

import java.util.Objects;

/**
 * Base interface for complex numbers
 *
 * @param <C> type of the complex number
 */
public sealed interface Polar<P extends Polar<P, C>, C extends Complex<C, ?, P>> extends Numeric<P, P>
        permits DoublePolar, BigPolar {
    @Override
    default boolean isInvertible() {
        return !isZero();
    }

    @Override
    boolean isZero();

    @Override
    default P add(final P summand) {
        Objects.requireNonNull(summand, "summand");
        return toComplex().add(summand.toComplex()).toPolar();
    }

    @Override
    default P subtract(final P subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return toComplex().subtract(subtrahend.toComplex()).toPolar();
    }

    /**
     * Returns this as complex number
     *
     * @return {@link Complex}
     */
    C toComplex();
}
