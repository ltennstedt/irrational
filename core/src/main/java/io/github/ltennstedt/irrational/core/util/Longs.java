package io.github.ltennstedt.irrational.core.util;

/** Utilities for longs */
public final class Longs {
    /** No arguments constructor */
    private Longs() {}

    /**
     * Returns the positive greatest common divisor
     *
     * @param a a
     * @param b b
     * @return greatest common divisor
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public static long gcd(final long a, final long b) {
        var absA = StrictMath.absExact(a);
        var absB = StrictMath.absExact(b);
        while (absB != 0) {
            final var t = absA % absB;
            absA = absB;
            absB = t;
        }
        return absA;
    }
}
