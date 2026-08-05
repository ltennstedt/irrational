package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

final class AtanCalculatorTest {
    private static Stream<Arguments> atan2Source() {
        return Stream.of(
                arguments(
                        BigDecimal.ONE,
                        BigDecimal.ONE,
                        BigDecimal.ONE.divide(BigDecimal.valueOf(4L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.ONE,
                        BigDecimal.valueOf(-1L),
                        BigDecimal.valueOf(3L).divide(BigDecimal.valueOf(4L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.valueOf(-1L),
                        BigDecimal.valueOf(-1L),
                        BigDecimal.valueOf(-3L).divide(BigDecimal.valueOf(4L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.ONE,
                        BigDecimal.ZERO,
                        BigDecimal.ONE.divide(BigDecimal.valueOf(2L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.valueOf(-1L),
                        BigDecimal.ZERO,
                        BigDecimal.valueOf(-1L).divide(BigDecimal.valueOf(2L), MathContext.DECIMAL128)));
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
        assertThat(AtanCalculator.atan2(y, x, MathContext.DECIMAL128))
                .isCloseTo(
                        expected.multiply(PiCalculator.pi(MathContext.DECIMAL128)),
                        within(BigDecimal.ONE.scaleByPowerOfTen(-2)));
    }
}
