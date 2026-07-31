package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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

    @Test
    void atan2_should_throw_when_y_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> AtanCalculator.atan2(null, BigDecimal.ZERO, MathContext.DECIMAL128))
                .withMessage("y")
                .withNoCause();
    }

    @Test
    void atan2_should_throw_when_x_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> AtanCalculator.atan2(BigDecimal.ZERO, null, MathContext.DECIMAL128))
                .withMessage("x")
                .withNoCause();
    }

    @Test
    void atan2_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> AtanCalculator.atan2(BigDecimal.ONE, BigDecimal.ONE, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void atan2_should_throw_when_x_and_y_are_0() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> AtanCalculator.atan2(BigDecimal.ZERO, BigDecimal.ZERO, MathContext.DECIMAL128))
                .withMessage("x and y must not both be 0")
                .withNoCause();
    }

    @Test
    void atan2_should_throw_when_precision_is_unlimited() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> AtanCalculator.atan2(BigDecimal.ONE, BigDecimal.ONE, MathContext.UNLIMITED))
                .withMessage("Unlimited precision is disallowed")
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
