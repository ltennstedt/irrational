package io.github.ltennstedt.irrational.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/** Calculator for pi */
public final class PiCalculator {
    private PiCalculator() {}

    /**
     * Returns pi based on given mathContext
     *
     * <p>The Chudnovsky series with iterative term recurrence is used.
     *
     * @param mathContext {@link MathContext}
     * @return pi
     * @throws NullPointerException when mathContext is null
     */
    public static BigDecimal pi(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        final var guard =
                new MathContext(StrictMath.addExact(mathContext.getPrecision(), 10), mathContext.getRoundingMode());
        var a_k = BigDecimal.ONE;
        BigDecimal b_k;
        var a_sum = BigDecimal.ONE;
        var b_sum = BigDecimal.ZERO;
        for (var k = 1; a_k.abs(guard).compareTo(BigDecimal.ONE.scaleByPowerOfTen(-guard.getPrecision())) > 0; k++) {
            a_k = BigDecimal.valueOf(6L)
                    .multiply(BigDecimal.valueOf(k), guard)
                    .subtract(BigDecimal.valueOf(5L), guard)
                    .multiply(
                            BigDecimal.valueOf(2L)
                                    .multiply(BigDecimal.valueOf(k), guard)
                                    .subtract(BigDecimal.ONE, guard),
                            guard)
                    .multiply(
                            BigDecimal.valueOf(6L)
                                    .multiply(BigDecimal.valueOf(k), guard)
                                    .subtract(BigDecimal.ONE, guard),
                            guard)
                    .negate(guard)
                    .multiply(a_k, guard)
                    .divide(
                            BigDecimal.valueOf(k)
                                    .pow(3, guard)
                                    .multiply(
                                            BigDecimal.valueOf(640_320L)
                                                    .pow(3, guard)
                                                    .divide(BigDecimal.valueOf(24L), guard),
                                            guard),
                            guard);
            b_k = BigDecimal.valueOf(k).multiply(a_k, guard);
            a_sum = a_sum.add(a_k, guard);
            b_sum = b_sum.add(b_k, guard);
        }
        final var pi = BigDecimal.valueOf(426_880L)
                .multiply(BigDecimal.valueOf(10_005L).sqrt(guard), guard)
                .divide(
                        BigDecimal.valueOf(13_591_409L)
                                .multiply(a_sum, guard)
                                .add(BigDecimal.valueOf(545_140_134L).multiply(b_sum, guard), guard),
                        guard);
        return pi.round(mathContext);
    }
}
