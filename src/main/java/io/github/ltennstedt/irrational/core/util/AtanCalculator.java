package io.github.ltennstedt.irrational.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Calculator for arcutangens */
public final class AtanCalculator {
    private AtanCalculator() {}

    /**
     * Returns arctangent of x based on given mathContext
     *
     * @param x x
     * @param mathContext {@link MathContext}
     * @return {@link BigDecimal}
     * @throws NullPointerException when x is null
     * @throws NullPointerException when mathContext is null
     */
    public static BigDecimal atan(final BigDecimal x, final MathContext mathContext) {
        Objects.requireNonNull(x, "x");
        Objects.requireNonNull(mathContext, "mathContext");
        if (x.signum() == 0) {
            return BigDecimal.ZERO;
        }
        final var guard =
                new MathContext(StrictMath.addExact(mathContext.getPrecision(), 90), mathContext.getRoundingMode());
        if (x.signum() < 0) {
            return atan(x.negate(guard), mathContext).negate(mathContext);
        }
        final var pi = PiCalculator.pi(guard);
        final var halfPi = pi.divide(BigDecimal.valueOf(2L), guard);
        final BigDecimal result;
        if (x.compareTo(BigDecimal.ONE) > 0) {
            result = halfPi.subtract(atan(BigDecimal.ONE.divide(x, guard), guard), guard);
        } else {
            final var x2 = x.multiply(x, guard);
            final var coefficients = coefficients(guard);
            var p = coefficients.get(coefficients.size() - 1);
            for (int i = coefficients.size() - 2; i >= 0; i--) {
                p = p.multiply(x2, guard).add(coefficients.get(i), guard);
            }
            result = p.multiply(x, guard);
        }
        return result.round(mathContext);
    }

    /**
     * Returns two-argument arctangent of x and y
     *
     * @param x x
     * @param y y
     * @return {@link BigDecimal}
     * @throws NullPointerException when x is null
     * @throws NullPointerException when y is null
     * @throws IllegalArgumentException when x and y are 0
     */
    public static BigDecimal atan2(final BigDecimal y, final BigDecimal x) {
        return atan2(y, x, Constants.DEFAULT_MATH_CONTEXT);
    }

    /**
     * Returns two-argument arctangent of x and y based on given mathContext
     *
     * @param x x
     * @param y y
     * @param mathContext {@link MathContext}
     * @return {@link BigDecimal}
     * @throws NullPointerException when x is null
     * @throws NullPointerException when y is null
     * @throws NullPointerException when mathContext is null
     * @throws IllegalArgumentException when x and y are 0
     */
    public static BigDecimal atan2(final BigDecimal y, final BigDecimal x, final MathContext mathContext) {
        Objects.requireNonNull(y, "y");
        Objects.requireNonNull(x, "x");
        Objects.requireNonNull(mathContext, "mathContext");
        if (x.signum() == 0 && y.signum() == 0) {
            throw new IllegalArgumentException("x and y must not both be 0");
        }
        if (x.signum() > 0) {
            return atan(y.divide(x, mathContext), mathContext);
        }
        final var pi = PiCalculator.pi(mathContext);
        if (x.signum() < 0) {
            final var xOverY = y.divide(x, mathContext);
            return y.signum() >= 0
                    ? atan(xOverY, mathContext).add(pi, mathContext)
                    : atan(xOverY, mathContext).subtract(pi, mathContext);
        }
        final var halfPi = pi.divide(BigDecimal.valueOf(2L), mathContext);
        return y.signum() > 0 ? halfPi : halfPi.negate(mathContext);
    }

    private static List<BigDecimal> coefficients(final MathContext mathContext) {
        final var coefficients = new ArrayList<BigDecimal>(45);
        var divisor = BigInteger.ONE;
        for (int k = 0; k < 45; k++) {
            if (k > 0) {
                divisor = divisor.add(BigInteger.TWO);
            }
            final var candidate = BigDecimal.ONE.divide(new BigDecimal(divisor), mathContext);
            coefficients.add((k & 1) == 1 ? candidate.negate() : candidate);
        }
        return List.copyOf(coefficients);
    }
}
