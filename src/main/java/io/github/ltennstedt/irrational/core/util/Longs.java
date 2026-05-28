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
        return b == 0L ? (a == Long.MIN_VALUE ? 1L << 63 : StrictMath.absExact(a)) : gcd(b, a % b);
    }
}
