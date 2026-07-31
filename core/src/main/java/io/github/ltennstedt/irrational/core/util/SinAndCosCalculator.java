package io.github.ltennstedt.irrational.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Calculator for sine and cosine */
public final class SinAndCosCalculator {
    private static final int GUARD_DIGITS = 12;

    private SinAndCosCalculator() {}

    /**
     * Returns sine of x based on given mathContext
     *
     * @param x x
     * @param mathContext {@link MathContext}
     * @return {@link BigDecimal}
     * @throws NullPointerException when x is null
     * @throws NullPointerException when mathContext is null
     */
    public static BigDecimal sin(final BigDecimal x, final MathContext mathContext) {
        validateArguments(x, mathContext);
        final var reduction = computeReduction(x, mathContext);
        return (switch (reduction.quadrant) {
                    case 0 -> sinReduced(reduction.localAngle, reduction.guard);
                    case 1 -> cosReduced(reduction.localAngle, reduction.guard);
                    case 2 -> sinReduced(reduction.localAngle, reduction.guard).negate();
                    case 3 -> cosReduced(reduction.localAngle, reduction.guard).negate();
                    default -> throw new IllegalStateException("k must be 0, 1, 2 or 3 but was " + reduction.quadrant);
                })
                .round(mathContext);
    }

    /**
     * Returns cosine of x based on given mathContext
     *
     * @param x x
     * @param mathContext {@link MathContext}
     * @return {@link BigDecimal}
     * @throws NullPointerException when x is null
     * @throws NullPointerException when mathContext is null
     */
    public static BigDecimal cos(final BigDecimal x, final MathContext mathContext) {
        validateArguments(x, mathContext);
        final var reduction = computeReduction(x, mathContext);
        return (switch (reduction.quadrant) {
                    case 0 -> cosReduced(reduction.localAngle, reduction.guard);
                    case 1 -> sinReduced(reduction.localAngle, reduction.guard).negate();
                    case 2 -> cosReduced(reduction.localAngle, reduction.guard).negate();
                    case 3 -> sinReduced(reduction.localAngle, reduction.guard);
                    default -> throw new IllegalStateException("k must be 0, 1, 2 or 3 but was " + reduction.quadrant);
                })
                .round(mathContext);
    }

    private static void validateArguments(final BigDecimal x, final MathContext mathContext) {
        Objects.requireNonNull(x, "x");
        Objects.requireNonNull(mathContext, "mathContext");
        if (mathContext.getPrecision() == 0) {
            throw new ArithmeticException("Unlimited precision is disallowed");
        }
    }

    private static Reduction computeReduction(final BigDecimal x, final MathContext mathContext) {
        final var guard = new MathContext(
                StrictMath.addExact(mathContext.getPrecision(), GUARD_DIGITS), mathContext.getRoundingMode());
        final var pi = PiCalculator.pi(guard);
        final var reducedAngle = x.remainder(BigDecimal.valueOf(2L).multiply(pi, guard), guard);
        final var halfPi = pi.divide(BigDecimal.valueOf(2L), guard);
        final var quadrantIndexRaw = reducedAngle.divide(halfPi, 0, RoundingMode.HALF_EVEN);
        final var quadrant = quadrantIndexRaw.intValueExact() & 3;
        final var localAngle = reducedAngle.subtract(halfPi.multiply(quadrantIndexRaw, guard), guard);
        return new Reduction(quadrant, localAngle, guard);
    }

    private static BigDecimal sinReduced(final BigDecimal x, final MathContext mathContext) {
        final var z = x.multiply(x, mathContext);
        final var coefficients = sinCoefficients(mathContext);
        var p = coefficients.get(coefficients.size() - 1);
        for (var i = coefficients.size() - 2; i >= 0; i--) {
            p = p.multiply(z, mathContext).add(coefficients.get(i), mathContext);
        }
        return x.multiply(BigDecimal.ONE.add(z.multiply(p, mathContext), mathContext), mathContext);
    }

    private static List<BigDecimal> sinCoefficients(final MathContext mathContext) {
        final var coefficients = new ArrayList<BigDecimal>();
        var p = BigInteger.ONE;
        final var tolerance = BigDecimal.ONE.scaleByPowerOfTen(-mathContext.getPrecision());
        for (var k = 1; ; k++) {
            p = p.multiply(BigInteger.valueOf(2L * k)).multiply(BigInteger.valueOf(2L * k + 1));
            final var candidate = BigDecimal.ONE.divide(new BigDecimal(p), mathContext);
            coefficients.add((k & 1) == 1 ? candidate.negate() : candidate);
            if (candidate.compareTo(tolerance) < 0) {
                break;
            }
        }
        return List.copyOf(coefficients);
    }

    private static BigDecimal cosReduced(final BigDecimal x, final MathContext mathContext) {
        final var z = x.multiply(x, mathContext);
        final var coefficients = cosCoefficients(mathContext);
        var p = coefficients.get(coefficients.size() - 1);
        for (var i = coefficients.size() - 2; i >= 0; i--) {
            p = p.multiply(z, mathContext).add(coefficients.get(i), mathContext);
        }
        return BigDecimal.ONE.add(z.multiply(p, mathContext), mathContext);
    }

    private static List<BigDecimal> cosCoefficients(final MathContext mathContext) {
        var p = BigInteger.ONE;
        final var coefficients = new ArrayList<BigDecimal>();
        final var tolerance = BigDecimal.ONE.scaleByPowerOfTen(-mathContext.getPrecision());
        for (var k = 1; ; k++) {
            p = p.multiply(BigInteger.valueOf(2L * k - 1)).multiply(BigInteger.valueOf(2L * k));
            final var candidate = BigDecimal.ONE.divide(new BigDecimal(p), mathContext);
            coefficients.add((k & 1) == 1 ? candidate.negate() : candidate);
            if (candidate.compareTo(tolerance) < 0) {
                break;
            }
        }
        return List.copyOf(coefficients);
    }

    private record Reduction(int quadrant, BigDecimal localAngle, MathContext guard) {}
}
