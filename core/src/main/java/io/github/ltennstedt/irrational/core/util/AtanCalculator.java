package io.github.ltennstedt.irrational.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Calculator for arctangent */
public final class AtanCalculator {
    private AtanCalculator() {}

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
     * @throws ArithmeticException when x and y are 0
     * @throws ArithmeticException when precision is unlimited
     */
    public static BigDecimal atan2(final BigDecimal y, final BigDecimal x, final MathContext mathContext) {
        Objects.requireNonNull(y, "y");
        Objects.requireNonNull(x, "x");
        Objects.requireNonNull(mathContext, "mathContext");
        if (x.signum() == 0 && y.signum() == 0) {
            throw new ArithmeticException("x and y must not both be 0");
        }
        if (mathContext.getPrecision() == 0) {
            throw new ArithmeticException("Unlimited precision is disallowed");
        }
        final var guard =
                new MathContext(StrictMath.addExact(mathContext.getPrecision(), 128), mathContext.getRoundingMode());
        if (x.signum() > 0) {
            return atan(y.divide(x, guard), guard).round(mathContext);
        }
        final var pi = PiCalculator.pi(guard);
        if (x.signum() < 0) {
            final var yOverX = y.divide(x, guard);
            return (y.signum() >= 0
                            ? atan(yOverX, guard).add(pi, guard)
                            : atan(yOverX, guard).subtract(pi, guard))
                    .round(mathContext);
        }
        final var halfPi = pi.divide(BigDecimal.valueOf(2L), guard);
        return (y.signum() > 0 ? halfPi : halfPi.negate(guard)).round(mathContext);
    }

    private static BigDecimal atan(final BigDecimal x, final MathContext mathContext) {
        if (x.signum() == 0) {
            return BigDecimal.ZERO;
        }
        if (x.signum() < 0) {
            return atan(x.negate(mathContext), mathContext).negate(mathContext);
        }
        final var pi = PiCalculator.pi(mathContext);
        if (x.compareTo(BigDecimal.ONE) > 0) {
            return pi.divide(BigDecimal.valueOf(2L), mathContext)
                    .subtract(atan(BigDecimal.ONE.divide(x, mathContext), mathContext), mathContext);
        } else if (x.compareTo(new BigDecimal("0.5")) > -1) {
            return pi.divide(BigDecimal.valueOf(4L), mathContext)
                    .add(
                            atan(
                                    x.subtract(BigDecimal.ONE, mathContext)
                                            .divide(x.add(BigDecimal.ONE, mathContext), mathContext),
                                    mathContext),
                            mathContext);
        } else {
            final var x2 = x.multiply(x, mathContext);
            final var coefficients = coefficients(x, x2, mathContext);
            var p = coefficients.get(coefficients.size() - 1);
            for (int i = coefficients.size() - 2; i >= 0; i--) {
                p = p.multiply(x2, mathContext).add(coefficients.get(i), mathContext);
            }
            return p.multiply(x, mathContext);
        }
    }

    private static List<BigDecimal> coefficients(
            final BigDecimal x, final BigDecimal x2, final MathContext mathContext) {
        var xPower = x;
        var divisor = BigDecimal.ONE;
        final var coefficients = new ArrayList<BigDecimal>();
        coefficients.add(BigDecimal.ONE);
        final var tolerance = BigDecimal.ONE.scaleByPowerOfTen(-mathContext.getPrecision() - 16);
        for (int k = 0; ; k++) {
            xPower = xPower.multiply(x2, mathContext);
            divisor = divisor.add(BigDecimal.valueOf(2L));
            final var candidate = BigDecimal.ONE.divide(divisor, mathContext);
            coefficients.add((k & 1) == 1 ? candidate.negate() : candidate);
            if (xPower.divide(divisor, mathContext).compareTo(tolerance) < 0) {
                break;
            }
        }
        return List.copyOf(coefficients);
    }
}
