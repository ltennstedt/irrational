package sliderule.core.util;

import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;
import java.util.stream.IntStream;

/** Utilities for longs */
public final class Longs {
    private Longs() {}

    /**
     * Calculates base to the power of exponent
     *
     * @param base base
     * @param exponent exponent
     * @return power
     */
    public static double power(final long base, final int exponent) {
        return powerInernal(base, exponent, Math::abs, (a, b) -> a * b);
    }

    /**
     * Calculates base to the power of exponent
     *
     * @param base base
     * @param exponent exponent
     * @return power
     * @throws ArithmeticException when an operation overflows
     */
    public static double powerExact(final long base, final int exponent) {
        return powerInernal(base, exponent, Math::absExact, Math::multiplyExact);
    }

    /**
     * Returns the positive greatest common divisor
     *
     * @param a a
     * @param b b
     * @return greatest common divisor
     */
    public static long gcd(final long a, final long b) {
        return gcdInternal(a, b, Math::abs, Longs::gcd);
    }

    /**
     * Returns the positive greatest common divisor
     *
     * @param a a
     * @param b b
     * @return greatest common divisor
     * @throws ArithmeticException when an operation overflows
     */
    public static long gcdExact(final long a, final long b) {
        return gcdInternal(a, b, Math::absExact, Longs::gcdExact);
    }

    private static double powerInernal(
            final long base,
            final int exponent,
            final IntFunction<Integer> function,
            final LongBinaryOperator operator) {
        if (base == 0) {
            return 0;
        }
        if (base == 1 || exponent == 0) {
            return 1;
        }
        if (exponent == 1) {
            return base;
        }
        final var power = IntStream.rangeClosed(1, function.apply(exponent))
                .mapToLong(i -> base)
                .reduce(operator)
                .orElseThrow();
        return exponent < 0 ? 1.0D / power : power;
    }

    private static long gcdInternal(
            final long a, final long b, final LongFunction<Long> function, final LongBinaryOperator operator) {
        return b == 0 ? function.apply(a) : operator.applyAsLong(b, a % b);
    }
}
