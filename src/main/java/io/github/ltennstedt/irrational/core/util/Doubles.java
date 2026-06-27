package io.github.ltennstedt.irrational.core.util;

/** Utilities for doubles */
public final class Doubles {
    /** Epsilon */
    public static final double EPSILON = 1e-12;

    private Doubles() {}

    /**
     * Returns if x is near y
     *
     * @param x x
     * @param y y
     * @return boolean
     */
    public static boolean isNear(final double x, final double y) {
        return StrictMath.abs(x - y) < EPSILON;
    }

    /**
     * Returns a normalized 0 value or d
     *
     * @param d double
     * @return 0 or d
     */
    public static double normalizeZero(final double d) {
        return d == 0D ? 0D : d;
    }

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
            throw new ArithmeticException("%s must not be NaN and must be finite but was %s".formatted(name, d));
        }
        return d;
    }
}
