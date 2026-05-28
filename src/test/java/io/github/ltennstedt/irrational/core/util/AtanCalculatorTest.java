package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;
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
    private static final BigDecimal PI = PiCalculator.pi(MathContext.DECIMAL128);

    private static Stream<Arguments> atanSource() {
        return Stream.of(
                arguments(BigDecimal.ZERO, BigDecimal.ZERO),
                arguments(BigDecimal.ONE, BigDecimal.ONE.divide(BigDecimal.valueOf(4L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.valueOf(-1L),
                        BigDecimal.valueOf(-1L).divide(BigDecimal.valueOf(4L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.valueOf(3L).sqrt(MathContext.DECIMAL128),
                        BigDecimal.ONE.divide(BigDecimal.valueOf(3L), MathContext.DECIMAL128)),
                arguments(
                        BigDecimal.ONE.divide(
                                BigDecimal.valueOf(3L).sqrt(MathContext.DECIMAL128), MathContext.DECIMAL128),
                        BigDecimal.ONE.divide(BigDecimal.valueOf(6L), MathContext.DECIMAL128)));
    }

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
    void atan_should_throw_Exception_when_x_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> AtanCalculator.atan(null, MathContext.DECIMAL128))
                .withMessage("x")
                .withNoCause();
    }

    @Test
    void atan_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> AtanCalculator.atan(BigDecimal.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @ParameterizedTest
    @MethodSource("atanSource")
    void atan(final BigDecimal x, final BigDecimal expected) {
        assertThat(AtanCalculator.atan(x, MathContext.DECIMAL128))
                .isCloseTo(expected.multiply(PI), within(BigDecimal.ONE.scaleByPowerOfTen(-2)));
    }

    @Test
    void atan2_should_throw_Exception_when_y_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> AtanCalculator.atan2(null, BigDecimal.ZERO, MathContext.DECIMAL128))
                .withMessage("y")
                .withNoCause();
    }

    @Test
    void atan2_should_throw_Exception_when_x_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> AtanCalculator.atan2(BigDecimal.ZERO, null, MathContext.DECIMAL128))
                .withMessage("x")
                .withNoCause();
    }

    @Test
    void atan2_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> AtanCalculator.atan2(BigDecimal.ONE, BigDecimal.ONE, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void atan2_should_throw_Exception_when_x_and_y_are_0() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> AtanCalculator.atan2(BigDecimal.ZERO, BigDecimal.ZERO, MathContext.DECIMAL128))
                .withMessage("x and y must not both be 0")
                .withNoCause();
    }

    @ParameterizedTest
    @MethodSource("atan2Source")
    void atan2_should_succeed(final BigDecimal x, final BigDecimal y, final BigDecimal expected) {
        assertThat(AtanCalculator.atan2(x, y, MathContext.DECIMAL128))
                .isCloseTo(expected.multiply(PI), within(BigDecimal.ONE.scaleByPowerOfTen(-2)));
    }
}
