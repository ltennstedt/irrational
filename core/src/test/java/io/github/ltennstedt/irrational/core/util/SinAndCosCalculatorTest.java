package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.within;

import java.math.BigDecimal;
import java.math.MathContext;
import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class SinAndCosCalculatorTest {
    private final Offset<BigDecimal> offset = within(new BigDecimal("1E-33"));

    @ParameterizedTest
    @CsvSource(textBlock = """
            java.lang.NullPointerException,  , precision=7 roundingMode=HALF_EVEN, x
            java.lang.NullPointerException, 0,                                   , mathContext
            java.lang.ArithmeticException , 0, precision=0 roundingMode=HALF_EVEN, Unlimited precision is disallowed
        """)
    <T extends RuntimeException> void sin_should_throw(
            final Class<T> exceptionType, final BigDecimal x, final MathContext mathContext, final String message) {
        assertThatExceptionOfType(exceptionType)
                .isThrownBy(() -> SinAndCosCalculator.sin(x, mathContext))
                .withMessage(message)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0
        0.5, 1
        1, 0
        1.5, -1
        2, 0
        """)
    void sin_should_succeed(final BigDecimal factor, final BigDecimal expected) {
        assertThat(SinAndCosCalculator.sin(
                        factor.multiply(PiCalculator.pi(MathContext.DECIMAL128)), MathContext.DECIMAL128))
                .isCloseTo(expected, offset);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            java.lang.NullPointerException,  , precision=7 roundingMode=HALF_EVEN, x
            java.lang.NullPointerException, 0,                                   , mathContext
            java.lang.ArithmeticException , 0, precision=0 roundingMode=HALF_EVEN, Unlimited precision is disallowed
        """)
    <T extends RuntimeException> void cos_should_throw(
            final Class<T> exceptionType, final BigDecimal x, final MathContext mathContext, final String message) {
        assertThatExceptionOfType(exceptionType)
                .isThrownBy(() -> SinAndCosCalculator.cos(x, mathContext))
                .withMessage(message)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 1
        0.5, 0
        1, -1
        1.5, 0
        2, 1
        """)
    void cos_should_succeed(final BigDecimal factor, final BigDecimal expected) {
        assertThat(SinAndCosCalculator.cos(
                        factor.multiply(PiCalculator.pi(MathContext.DECIMAL128)), MathContext.DECIMAL128))
                .isCloseTo(expected, offset);
    }
}
