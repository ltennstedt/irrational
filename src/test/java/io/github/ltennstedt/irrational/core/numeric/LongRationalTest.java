package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

final class LongRationalTest {
    private final LongRational rational1 = new LongRational(2L, 3L);
    private final LongRational rational2 = new LongRational(4L, 5L);

    @Test
    void new_should_throw_exception_when_denominator_is_0() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new LongRational(1L, 0L))
                .withMessage("denominator must not be 0 but was 0")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, -4, -1, 2
        2,  4,  1, 2
        """)
    void new_should_succeed(
            final long numerator,
            final long denominator,
            final long expectedNumerator,
            final long expectedDenominator) {
        assertThat(new LongRational(numerator, denominator))
                .isEqualTo(new LongRational(expectedNumerator, expectedDenominator));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isInvertible_should_succeed(final long numerator, final boolean expected) {
        assertThat(new LongRational(numerator, 1L).isInvertible()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, false
            0, true
        """)
    void isZero_should_succeed(final long numerator, final boolean expected) {
        assertThat(new LongRational(numerator, 1L).isZero()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isUnitFraction_should_succeed(final long numerator, final boolean expected) {
        assertThat(new LongRational(numerator, 1L).isUnitFraction()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, true
            2, true
            3, false
            4, true
            5, false
            6, false
            7, false
            8, true
            9, false
        """)
    void isDyadic_should_succeed(final long denominator, final boolean expected) {
        assertThat(new LongRational(1L, denominator).isDyadic()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            -2, 1, false
            -1, 1, false
             1, 1, false
             2, 1, false
            -1, 2, true
             1, 2, true
        """)
    void isProper_should_succeed(final long numerator, final long denominator, final boolean expected) {
        assertThat(new LongRational(numerator, denominator).isProper()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            -1, false
             0, false
             1, true
        """)
    void isPositive_should_succeed(final long numerator, final boolean expected) {
        assertThat(new LongRational(numerator, 1L).isPositive()).isEqualTo(expected);
    }

    @Test
    void negate_should_succeed() {
        assertThat(LongRational.ONE.negate()).isEqualTo(new LongRational(-1L, 1L));
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(rational1.add(rational2)).isEqualTo(new LongRational(22L, 15L));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(rational1.subtract(rational2)).isEqualTo(new LongRational(-2L, 15L));
    }

    @Test
    void multiply_should_throw_exception_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        assertThat(rational1.multiply(rational2)).isEqualTo(new LongRational(8L, 15L));
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> LongRational.ONE.divide(LongRational.ZERO))
                .withMessage("divisor must be invertible but was LongRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        assertThat(rational1.divide(rational2)).isEqualTo(new LongRational(5L, 6L));
    }

    @Test
    void pow_should_throw_exception_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> LongRational.ZERO.pow(-1))
                .withMessage("this must be invertible but was LongRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 3, -1, 3,  2
        0, 1,  0, 1,  1
        2, 3,  1, 2,  3
        0, 1,  2, 0,  1
        2, 3,  2, 4,  9
        2, 3,  3, 8, 27
        """)
    void pow_should_succeed(
            final long numerator,
            final long denominator,
            final int exponent,
            final long expectedNumerator,
            final long expectedDenominator) {
        assertThat(new LongRational(numerator, denominator).pow(exponent))
                .isEqualTo(new LongRational(expectedNumerator, expectedDenominator));
    }

    @Test
    void reciprocal_should_throw_exception_when_this_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(LongRational.ZERO::reciprocal)
                .withMessage("this must be invertible but was LongRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void reciprocal_should_succeed() {
        assertThat(rational1.reciprocal()).isEqualTo(new LongRational(3L, 2L));
    }

    @ParameterizedTest
    @LongRangeSource(from = -1L, to = 1L, closed = true)
    void signum_should_succeed(final long numerator) {
        assertThat(new LongRational(numerator, 1L).signum()).isEqualTo(Long.signum(numerator));
    }

    @Test
    void min_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.min(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, 1, 0
            0, 0, 0
            1, 0, 0
        """)
    void min_should_succeed(final long numerator, final long otherNumerator, final long expected) {
        assertThat(new LongRational(numerator, 1L).min(new LongRational(otherNumerator, 1L)))
                .isEqualTo(new LongRational(expected, 1L));
    }

    @Test
    void max_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.max(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, 0, 1
            0, 0, 0
            0, 1, 1
        """)
    void max_should_succeed(final long numerator, final long otherNumerator, final long expected) {
        assertThat(new LongRational(numerator, 1L).max(new LongRational(otherNumerator, 1L)))
                .isEqualTo(new LongRational(expected, 1L));
    }

    @ParameterizedTest
    @ValueSource(longs = {-2L, 2L})
    void abs_should_succeed(final long numerator) {
        assertThat(new LongRational(numerator, 1L).abs()).isEqualTo(new LongRational(Math.absExact(numerator), 1L));
    }

    @Test
    void toBigDecimal_with_scale_and_roundingMode_should_succeed() {
        assertThat(new LongRational(1L, 3L).toBigDecimal(2, RoundingMode.UP)).isEqualTo(new BigDecimal("0.34"));
    }

    @Test
    void toBigDecimal_with_roundingMode_should_succeed() {
        assertThat(new LongRational(1L, 3L).toBigDecimal(RoundingMode.UP)).isOne();
    }

    @Test
    void toBigDecimal_with_mathContext_should_succeed() {
        assertThat(new LongRational(1L, 3L).toBigDecimal(MathContext.DECIMAL32)).isEqualTo(new BigDecimal("0.3333333"));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 1, -1
        1, 1,  0
        1, 2,  1
        """)
    void compareTo_should_succeed(final long numerator, final long denominator, final int expected) {
        assertThat(LongRational.ONE.compareTo(new LongRational(numerator, denominator)))
                .isEqualTo(expected);
    }

    @Nested
    class ComparatorTest {
        @ParameterizedTest
        @CsvSource(textBlock = """
            2, 1, -1
            1, 1,  0
            1, 2,  1
            """)
        void compareTo_should_succeed(final long numerator, final long denominator, final int expected) {
            assertThat(LongRational.COMPARATOR.compare(LongRational.ONE, new LongRational(numerator, denominator)))
                    .isEqualTo(expected);
        }
    }
}
