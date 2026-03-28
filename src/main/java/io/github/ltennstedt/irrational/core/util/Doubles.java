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
        return Math.abs(x - y) < EPSILON;
    }
}
