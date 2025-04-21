package io.github.ltennstedt.irrational.util;

import java.util.stream.IntStream;

/** Utilities for longs */
public final class Longs {
    /** No arguments constructor */
    private Longs() {}

    /**
     * Calculates base to the power of exponent
     *
     * @param base base
     * @param exponent exponent
     * @return power
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public static double power(final long base, final int exponent) {
        if (base == 0L) {
            return 0D;
        }
        if (base == 1L || exponent == 0) {
            return 1D;
        }
        if (exponent == 1) {
            return base;
        }
        final var power = IntStream.rangeClosed(1, Math.absExact(exponent))
                .mapToLong(i -> base)
                .reduce(Math::multiplyExact)
                .orElseThrow();
        return exponent < 0 ? 1D / power : power;
    }

    /**
     * Returns the positive greatest common divisor
     *
     * @param a a
     * @param b b
     * @return greatest common divisor
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public static long gcd(final long a, final long b) {
        return b == 0L ? (a == Long.MIN_VALUE ? 1L << 63 : Math.absExact(a)) : gcd(b, a % b);
    }
}
