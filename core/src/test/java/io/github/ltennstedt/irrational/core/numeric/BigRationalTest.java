package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

final class BigRationalTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
         , 1, numerator
        1,  , denominator
        """)
    void new_should_throw_when_any_argument_is_null(
            final BigInteger numerator, final BigInteger denominator, final String message) {
        assertThatNullPointerException()
                .isThrownBy(() -> new BigRational(numerator, denominator))
                .withMessage(message)
                .withNoCause();
    }

    @Test
    void new_should_throw_when_denominator_is_zero() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new BigRational(BigInteger.ONE, BigInteger.ZERO))
                .withMessage("denominator must not be 0 but was 0")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, -4, -1, 2
        2,  4,  1, 2
        """)
    void new_should_succeed(
            final BigInteger numerator,
            final BigInteger denominator,
            final BigInteger expectedNumerator,
            final BigInteger expectedDenominator) {
        final var actual = new BigRational(numerator, denominator);

        final var expected = new BigRational(expectedNumerator, expectedDenominator);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isInvertible_should_succeed(final BigInteger numerator, final boolean expected) {
        final var rational = new BigRational(numerator, BigInteger.ONE);

        final var actual = rational.isInvertible();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, false
            0, true
        """)
    void isZero_should_succeed(final BigInteger numerator, final boolean expected) {
        final var rational = new BigRational(numerator, BigInteger.ONE);

        final var actual = rational.isZero();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isUnitFraction_should_succeed(final BigInteger numerator, final boolean expected) {
        final var rational = new BigRational(numerator, BigInteger.ONE);

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
    void isDyadic_should_succeed(final BigInteger denominator, final boolean expected) {
        final var rational = new BigRational(BigInteger.ONE, denominator);

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
    void isProper_should_succeed(final BigInteger numerator, final BigInteger denominator, final boolean expected) {
        final var rational = new BigRational(numerator, denominator);

        final var actual = rational.isProper();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            -1, false
             0, false
             1, true
        """)
    void isPositive_should_succeed(final BigInteger numerator, final boolean expected) {
        final var rational = new BigRational(numerator, BigInteger.ONE);

        final var actual = rational.isPositive();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        final var rational = new BigRational(BigInteger.TWO, BigInteger.valueOf(3L));
        final var summand = new BigRational(BigInteger.valueOf(4L), BigInteger.valueOf(5L));

        final var actual = rational.add(summand);

        final var expected = new BigRational(BigInteger.valueOf(22L), BigInteger.valueOf(15L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtract_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        final var rational = new BigRational(BigInteger.TWO, BigInteger.valueOf(3L));
        final var subtrahend = new BigRational(BigInteger.valueOf(4L), BigInteger.valueOf(5L));

        final var actual = rational.subtract(subtrahend);

        final var expected = new BigRational(BigInteger.valueOf(-2L), BigInteger.valueOf(15L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void multiply_should_throw_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        final var rational = new BigRational(BigInteger.TWO, BigInteger.valueOf(3L));
        final var multiplier = new BigRational(BigInteger.valueOf(4L), BigInteger.valueOf(5L));

        final var actual = rational.multiply(multiplier);

        final var expected = new BigRational(BigInteger.valueOf(8L), BigInteger.valueOf(15L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void divide_should_throw_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigRational.ONE.divide(BigRational.ZERO))
                .withMessage("divisor must be invertible but was BigRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        final var rational = new BigRational(BigInteger.TWO, BigInteger.valueOf(3L));
        final var divisor = new BigRational(BigInteger.valueOf(4L), BigInteger.valueOf(5L));

        final var actual = rational.divide(divisor);

        final var expected = new BigRational(BigInteger.valueOf(5L), BigInteger.valueOf(6L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void negate_should_succeed() {
        final var actual = BigRational.ONE.negate();

        final var expected = new BigRational(BigInteger.ONE.negate(), BigInteger.ONE);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void pow_should_throw_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigRational.ZERO.pow(-1))
                .withMessage("this must be invertible but was BigRational[numerator=0, denominator=1]")
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
            final BigInteger numerator,
            final BigInteger denominator,
            final int exponent,
            final BigInteger expectedNumerator,
            final BigInteger expectedDenominator) {
        final var rational = new BigRational(numerator, denominator);

        final var actual = rational.pow(exponent);

        final var expected = new BigRational(expectedNumerator, expectedDenominator);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void reciprocal_should_throw_when_this_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(BigRational.ZERO::reciprocal)
                .withMessage("this must be invertible but was BigRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void reciprocal_should_succeed() {
        final var rational = new BigRational(BigInteger.TWO, BigInteger.valueOf(3L));

        final var actual = rational.reciprocal();

        final var expected = new BigRational(BigInteger.valueOf(3L), BigInteger.valueOf(2L));
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @LongRangeSource(from = -1L, to = 1L, closed = true)
    void signum_should_succeed(final long numerator) {
        final var rational = new BigRational(BigInteger.valueOf(numerator), BigInteger.ONE);

        final var actual = rational.signum();

        final var signum = Long.signum(numerator);
        assertThat(actual).isEqualTo(signum);
    }

    @Test
    void min_should_throw_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.min(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, 1, 0
            0, 0, 0
            1, 0, 0
        """)
    void min_should_succeed(
            final BigInteger numerator, final BigInteger otherNumerator, final BigInteger expectedNumerator) {
        final var rational = new BigRational(numerator, BigInteger.ONE);
        final var other = new BigRational(otherNumerator, BigInteger.ONE);

        final var actual = rational.min(other);

        final var expectedRational = new BigRational(expectedNumerator, BigInteger.ONE);
        assertThat(actual).isEqualTo(expectedRational);
    }

    @Test
    void max_should_throw_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.max(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, 0, 1
            0, 0, 0
            0, 1, 1
        """)
    void max_should_succeed(
            final BigInteger numerator, final BigInteger otherNumerator, final BigInteger expectedNumerator) {
        final var rational = new BigRational(numerator, BigInteger.ONE);
        final var other = new BigRational(otherNumerator, BigInteger.ONE);

        final var actual = rational.max(other);

        final var expectedRational = new BigRational(expectedNumerator, BigInteger.ONE);
        assertThat(actual).isEqualTo(expectedRational);
    }

    @ParameterizedTest
    @ValueSource(longs = {-2L, 2L})
    void abs_should_succeed(final long numerator) {
        final var rational = new BigRational(BigInteger.valueOf(numerator), BigInteger.ONE);

        final var actual = rational.abs();

        final var expected = new BigRational(BigInteger.valueOf(numerator).abs(), BigInteger.ONE);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toBigDecimal_with_scale_and_roundingMode_should_succeed() {
        final var rational = new BigRational(BigInteger.ONE, BigInteger.valueOf(3L));

        final var actual = rational.toBigDecimal(2, RoundingMode.UP);

        assertThat(actual).isEqualTo(new BigDecimal("0.34"));
    }

    @Test
    void toBigDecimal_with_roundingMode_should_succeed() {
        final var rational = new BigRational(BigInteger.ONE, BigInteger.valueOf(3L));

        final var actual = rational.toBigDecimal(RoundingMode.UP);

        assertThat(actual).isOne();
    }

    @Test
    void toBigDecimal_with_mathContext_should_succeed() {
        final var rational = new BigRational(BigInteger.ONE, BigInteger.valueOf(3L));

        final var actual = rational.toBigDecimal(MathContext.DECIMAL32);

        assertThat(actual).isEqualTo(new BigDecimal("0.3333333"));
    }

    @Test
    void compareTo_should_throw_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.compareTo(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 1, -1
        1, 1,  0
        1, 2,  1
        """)
    void compareTo_should_succeed(final BigInteger numerator, final BigInteger denominator, final int expected) {
        final var other = new BigRational(numerator, denominator);

        final var actual = BigRational.ONE.compareTo(other);

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
        void compareTo_should_succeed(final BigInteger numerator, final BigInteger denominator, final int expected) {
            final var actual = BigRational.COMPARATOR.compare(BigRational.ONE, new BigRational(numerator, denominator));

            assertThat(actual).isEqualTo(expected);
        }
    }
}
