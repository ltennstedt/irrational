package io.github.ltennstedt.irrational.core.util;

/** Utilities for doubles */
public final class Doubles {

    private Doubles() {}

    /**
     * Checks if d is {@link Double#NaN}, {@link Double#NEGATIVE_INFINITY} or {@link Double#POSITIVE_INFINITY}
     *
     * @param d double
     * @param name name
     * @return d
     * @throws ArithmeticException when d is {@link Double#NaN}
     * @throws ArithmeticException when d is {@link Double#NEGATIVE_INFINITY} or {@link Double#POSITIVE_INFINITY}
     */
    public static double check(final double d, final String name) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new ArithmeticException(name + " must be a finite number but was " + d);
        }
        return d;
    }
}
