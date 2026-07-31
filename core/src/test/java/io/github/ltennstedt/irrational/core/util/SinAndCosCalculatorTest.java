package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;

import java.math.BigDecimal;
import java.math.MathContext;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class SinAndCosCalculatorTest {
    private final Offset<BigDecimal> offset = within(new BigDecimal("1E-33"));

    @Test
    void sin_should_throw_when_x_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> SinAndCosCalculator.sin(null, MathContext.DECIMAL128))
                .withMessage("x")
                .withNoCause();
    }

    @Test
    void sin_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> SinAndCosCalculator.sin(BigDecimal.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void sin_should_throw_when_precision_is_0() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> SinAndCosCalculator.sin(BigDecimal.ZERO, MathContext.UNLIMITED))
                .withMessage("Unlimited precision is disallowed")
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

    @Test
    void cos_should_throw_when_x_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> SinAndCosCalculator.cos(null, MathContext.DECIMAL128))
                .withMessage("x")
                .withNoCause();
    }

    @Test
    void cos_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> SinAndCosCalculator.cos(BigDecimal.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void cos_should_throw_when_precision_is_0() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> SinAndCosCalculator.cos(BigDecimal.ZERO, MathContext.UNLIMITED))
                .withMessage("Unlimited precision is disallowed")
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
