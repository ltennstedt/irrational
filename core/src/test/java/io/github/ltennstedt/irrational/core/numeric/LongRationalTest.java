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
    @Test
    void new_should_throw_when_denominator_is_0() {
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
        final var actual = new LongRational(numerator, denominator);

        final var expected = new LongRational(expectedNumerator, expectedDenominator);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isInvertible_should_succeed(final long numerator, final boolean expected) {
        final var rational = new LongRational(numerator, 1L);

        final var actual = rational.isInvertible();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, false
            0, true
        """)
    void isZero_should_succeed(final long numerator, final boolean expected) {
        final var rational = new LongRational(numerator, 1L);

        final var actual = rational.isZero();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isUnitFraction_should_succeed(final long numerator, final boolean expected) {
        final var rational = new LongRational(numerator, 1L);

        final var actual = rational.isUnitFraction();

        assertThat(actual).isEqualTo(expected);
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
        final var rational = new LongRational(1L, denominator);

        final var actual = rational.isDyadic();

        assertThat(actual).isEqualTo(expected);
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
        final var rational = new LongRational(numerator, denominator);

        final var actual = rational.isProper();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            -1, false
             0, false
             1, true
        """)
    void isPositive_should_succeed(final long numerator, final boolean expected) {
        final var rational = new LongRational(numerator, 1L);

        final var actual = rational.isPositive();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        final var rational = new LongRational(2L, 3L);
        final var summand = new LongRational(4L, 5L);

        final var actual = rational.add(summand);

        final var expected = new LongRational(22L, 15L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtract_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        final var rational = new LongRational(2L, 3L);
        final var subtrahend = new LongRational(4L, 5L);

        final var actual = rational.subtract(subtrahend);

        final var expected = new LongRational(-2L, 15L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void multiply_should_throw_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        final var rational = new LongRational(2L, 3L);
        final var multiplier = new LongRational(4L, 5L);

        final var actual = rational.multiply(multiplier);

        final var expected = new LongRational(8L, 15L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void divide_should_throw_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> LongRational.ONE.divide(LongRational.ZERO))
                .withMessage("divisor must be invertible but was LongRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        final var rational = new LongRational(2L, 3L);
        final var divisor = new LongRational(4L, 5L);

        final var actual = rational.divide(divisor);

        final var expected = new LongRational(5L, 6L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void negate_should_succeed() {
        final var actual = LongRational.ONE.negate();

        final var expected = new LongRational(-1L, 1L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void pow_should_throw_when_exponent_is_negative_and_is_not_invertible() {
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
        final var rational = new LongRational(numerator, denominator);

        final var actual = rational.pow(exponent);

        final var expected = new LongRational(expectedNumerator, expectedDenominator);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void reciprocal_should_throw_when_this_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(LongRational.ZERO::reciprocal)
                .withMessage("this must be invertible but was LongRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void reciprocal_should_succeed() {
        final var rational = new LongRational(2L, 3L);

        final var actual = rational.reciprocal();

        final var expected = new LongRational(3L, 2L);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @LongRangeSource(from = -1L, to = 1L, closed = true)
    void signum_should_succeed(final long numerator) {
        final var rational = new LongRational(numerator, 1L);

        final var actual = rational.signum();

        final var expected = Long.signum(numerator);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void min_should_throw_when_other_is_null() {
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
    void min_should_succeed(final long numerator, final long otherNumerator, final long expectedNumerator) {
        final var rational = new LongRational(numerator, 1L);
        final var other = new LongRational(otherNumerator, 1L);

        final var actual = rational.min(other);

        final var expectedRational = new LongRational(expectedNumerator, 1L);
        assertThat(actual).isEqualTo(expectedRational);
    }

    @Test
    void max_should_throw_when_other_is_null() {
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
    void max_should_succeed(final long numerator, final long otherNumerator, final long expectedNumerator) {
        final var rational = new LongRational(numerator, 1L);

        final var other = new LongRational(otherNumerator, 1L);

        final var actual = rational.max(other);
        final var expectedRational = new LongRational(expectedNumerator, 1L);
        assertThat(actual).isEqualTo(expectedRational);
    }

    @ParameterizedTest
    @ValueSource(longs = {-2L, 2L})
    void abs_should_succeed(final long numerator) {
        final var rational = new LongRational(numerator, 1L);

        final var actual = rational.abs();

        final var expected = new LongRational(Math.absExact(numerator), 1L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toBigDecimal_with_scale_and_roundingMode_should_succeed() {
        final var rational = new LongRational(1L, 3L);

        final var actual = rational.toBigDecimal(2, RoundingMode.UP);

        assertThat(actual).isEqualTo(new BigDecimal("0.34"));
    }

    @Test
    void toBigDecimal_with_roundingMode_should_succeed() {
        final var rational = new LongRational(1L, 3L);

        final var actual = rational.toBigDecimal(RoundingMode.UP);

        assertThat(actual).isOne();
    }

    @Test
    void toBigDecimal_with_mathContext_should_succeed() {
        final var rational = new LongRational(1L, 3L);

        final var actual = rational.toBigDecimal(MathContext.DECIMAL32);

        assertThat(actual).isEqualTo(new BigDecimal("0.3333333"));
    }

    @Test
    void compareTo_should_throw_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.compareTo(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 1, -1
        1, 1,  0
        1, 2,  1
        """)
    void compareTo_should_succeed(final long numerator, final long denominator, final int expected) {
        final var other = new LongRational(numerator, denominator);

        final var actual = LongRational.ONE.compareTo(other);

        assertThat(actual).isEqualTo(expected);
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
            final var other = new LongRational(numerator, denominator);

            final var actual = LongRational.COMPARATOR.compare(LongRational.ONE, other);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
