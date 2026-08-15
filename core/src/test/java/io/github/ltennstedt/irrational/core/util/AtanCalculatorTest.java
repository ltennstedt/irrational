package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.Stream;
import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

final class AtanCalculatorTest {
    private static final Offset<BigDecimal> OFFSET = within(new BigDecimal("1E-33"));

    private static Stream<Arguments> atan2Source() {
        final var pi = PiCalculator.pi(MathContext.DECIMAL128);
        return Stream.of(
                arguments(BigDecimal.ONE, BigDecimal.ONE, pi.divide(BigDecimal.valueOf(4L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.valueOf(-1L),
                        BigDecimal.ONE,
                        pi.divide(BigDecimal.valueOf(-4L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.valueOf(2L), BigDecimal.ONE, new BigDecimal("1.107148717794090503017065460178537")),
                arguments(
                        BigDecimal.ONE, BigDecimal.valueOf(4L), new BigDecimal("0.2449786631268641541720824812112758")),
                arguments(
                        BigDecimal.ONE, BigDecimal.valueOf(2L), new BigDecimal("0.4636476090008061162142562314612144")),
                arguments(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO),
                arguments(
                        BigDecimal.ONE,
                        BigDecimal.valueOf(-1L),
                        pi.multiply(BigDecimal.valueOf(3L)).divide(BigDecimal.valueOf(4L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.valueOf(-1L),
                        BigDecimal.valueOf(-1L),
                        pi.multiply(BigDecimal.valueOf(-3L)).divide(BigDecimal.valueOf(4L), MathContext.DECIMAL128)),
                arguments(BigDecimal.ZERO, BigDecimal.valueOf(-1L), pi),
                arguments(BigDecimal.ONE, BigDecimal.ZERO, pi.divide(BigDecimal.valueOf(2L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.valueOf(-1L),
                        BigDecimal.ZERO,
                        pi.divide(BigDecimal.valueOf(-2L), MathContext.DECIMAL128)));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        java.lang.NullPointerException,  , 0, precision=7 roundingMode=HALF_EVEN, y
        java.lang.NullPointerException, 0,  , precision=7 roundingMode=HALF_EVEN, x
        java.lang.NullPointerException, 0, 1,                                   , mathContext
        java.lang.ArithmeticException , 0, 0, precision=7 roundingMode=HALF_EVEN, x and y must not both be 0
        java.lang.ArithmeticException , 1, 0, precision=0 roundingMode=HALF_EVEN, Unlimited precision is disallowed
        """)
    <T extends RuntimeException> void atan2_should_throw(
            final Class<T> exceptionType,
            final BigDecimal y,
            final BigDecimal x,
            final MathContext mathContext,
            final String message) {
        assertThatExceptionOfType(exceptionType)
                .isThrownBy(() -> AtanCalculator.atan2(y, x, mathContext))
                .withMessage(message)
                .withNoCause();
    }

    @ParameterizedTest
    @MethodSource("atan2Source")
    void atan2_should_succeed(final BigDecimal y, final BigDecimal x, final BigDecimal expected) {
        assertThat(AtanCalculator.atan2(y, x, MathContext.DECIMAL128)).isCloseTo(expected, OFFSET);
    }
}
