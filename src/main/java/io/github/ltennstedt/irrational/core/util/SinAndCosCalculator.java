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
    private static final int MAX_COEFFICIENTS = 45;

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
        Objects.requireNonNull(x, "x");
        Objects.requireNonNull(mathContext, "mathContext");
        final var pi = PiCalculator.pi(mathContext);
        final var guard =
                new MathContext(Math.addExact(mathContext.getPrecision(), GUARD_DIGITS), mathContext.getRoundingMode());
        final var reducedAngle = x.remainder(BigDecimal.valueOf(2L).multiply(pi, guard), guard);
        final var halfPi = pi.divide(BigDecimal.valueOf(2L), guard);
        final var quadrantIndexRaw = reducedAngle.divide(halfPi, 0, RoundingMode.HALF_EVEN);
        final var quadrant = quadrantIndexRaw.intValueExact() & 3;
        final var localAngle = reducedAngle.subtract(halfPi.multiply(quadrantIndexRaw, guard), guard);
        return (switch (quadrant) {
                    case 0 -> sinReduced(localAngle, guard);
                    case 1 -> cosReduced(localAngle, guard);
                    case 2 -> sinReduced(localAngle, guard).negate();
                    case 3 -> cosReduced(localAngle, guard).negate();
                    default -> throw new IllegalStateException("k must be 0, 1, 2 or 3 but was " + quadrant);
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
        Objects.requireNonNull(x, "x");
        Objects.requireNonNull(mathContext, "mathContext");
        final var pi = PiCalculator.pi(mathContext);
        final var guard =
                new MathContext(Math.addExact(mathContext.getPrecision(), GUARD_DIGITS), mathContext.getRoundingMode());
        final var reducedAngle = x.remainder(BigDecimal.valueOf(2L).multiply(pi, guard), guard);
        final var halfPi = pi.divide(BigDecimal.valueOf(2L), guard);
        final var quadrantIndexRaw = reducedAngle.divide(halfPi, 0, RoundingMode.HALF_EVEN);
        final var quadrant = quadrantIndexRaw.intValueExact() & 3;
        final var localAngle = reducedAngle.subtract(halfPi.multiply(quadrantIndexRaw, guard), guard);
        return (switch (quadrant) {
                    case 0 -> cosReduced(localAngle, guard);
                    case 1 -> sinReduced(localAngle, guard).negate();
                    case 2 -> cosReduced(localAngle, guard).negate();
                    case 3 -> sinReduced(localAngle, guard);
                    default -> throw new IllegalStateException("k must be 0, 1, 2 or 3 but was " + quadrant);
                })
                .round(mathContext);
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

    private static BigDecimal sinReduced(final BigDecimal x, final MathContext mathContext) {
        final var z = x.multiply(x, mathContext);
        final var coefficients = sinCoefficients(mathContext);
        var p = coefficients.get(coefficients.size() - 1);
        for (var i = coefficients.size() - 2; i > -1; i--) {
            p = p.multiply(z, mathContext).add(coefficients.get(i), mathContext);
        }
        return p.multiply(x, mathContext);
    }

    private static List<BigDecimal> sinCoefficients(final MathContext mathContext) {
        final var coefficients = new ArrayList<BigDecimal>(MAX_COEFFICIENTS);
        var product = BigInteger.ONE;
        for (var k = 1; k < MAX_COEFFICIENTS; k++) {
            product = product.multiply(BigInteger.valueOf(2L * k)).multiply(BigInteger.valueOf(2L * k + 1));
            final var candidate = BigDecimal.ONE.divide(new BigDecimal(product), mathContext);
            coefficients.add((k & 1) == 1 ? candidate.negate() : candidate);
        }
        return List.copyOf(coefficients);
    }

    private static List<BigDecimal> cosCoefficients(final MathContext mc) {
        final var coefficients = new ArrayList<BigDecimal>(MAX_COEFFICIENTS);
        var product = BigInteger.ONE;
        for (var k = 1; k < MAX_COEFFICIENTS; k++) {
            product = product.multiply(BigInteger.valueOf(2L * k - 1)).multiply(BigInteger.valueOf(2L * k));
            final var candidate = BigDecimal.ONE.divide(new BigDecimal(product), mc);
            coefficients.add((k & 1) == 1 ? candidate.negate() : candidate);
        }
        return List.copyOf(coefficients);
    }
}
