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
    private final BigRational rational1 = new BigRational(BigInteger.TWO, BigInteger.valueOf(3L));
    private final BigRational rational2 = new BigRational(BigInteger.valueOf(4L), BigInteger.valueOf(5L));

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
        assertThat(new BigRational(numerator, denominator))
                .isEqualTo(new BigRational(expectedNumerator, expectedDenominator));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isInvertible_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(new BigRational(numerator, BigInteger.ONE).isInvertible()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, false
            0, true
        """)
    void isZero_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(new BigRational(numerator, BigInteger.ONE).isZero()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isUnitFraction_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(new BigRational(numerator, BigInteger.ONE).isUnitFraction()).isEqualTo(expected);
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
        assertThat(new BigRational(BigInteger.ONE, denominator).isDyadic()).isEqualTo(expected);
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
        assertThat(new BigRational(numerator, denominator).isProper()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            -1, false
             0, false
             1, true
        """)
    void isPositive_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(new BigRational(numerator, BigInteger.ONE).isPositive()).isEqualTo(expected);
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
        assertThat(rational1.add(rational2))
                .isEqualTo(new BigRational(BigInteger.valueOf(22L), BigInteger.valueOf(15L)));
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
        assertThat(rational1.subtract(rational2))
                .isEqualTo(new BigRational(BigInteger.valueOf(-2L), BigInteger.valueOf(15L)));
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
        assertThat(rational1.multiply(rational2))
                .isEqualTo(new BigRational(BigInteger.valueOf(8L), BigInteger.valueOf(15L)));
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
        assertThat(rational1.divide(rational2))
                .isEqualTo(new BigRational(BigInteger.valueOf(5L), BigInteger.valueOf(6L)));
    }

    @Test
    void negate_should_succeed() {
        assertThat(BigRational.ONE.negate()).isEqualTo(new BigRational(BigInteger.ONE.negate(), BigInteger.ONE));
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
        assertThat(new BigRational(numerator, denominator).pow(exponent))
                .isEqualTo(new BigRational(expectedNumerator, expectedDenominator));
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
        assertThat(rational1.reciprocal()).isEqualTo(new BigRational(BigInteger.valueOf(3L), BigInteger.valueOf(2L)));
    }

    @ParameterizedTest
    @LongRangeSource(from = -1L, to = 1L, closed = true)
    void signum_should_succeed(final long numerator) {
        assertThat(new BigRational(BigInteger.valueOf(numerator), BigInteger.ONE).signum())
                .isEqualTo(Long.signum(numerator));
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
    void min_should_succeed(final BigInteger numerator, final BigInteger otherNumerator, final BigInteger expected) {
        assertThat(new BigRational(numerator, BigInteger.ONE).min(new BigRational(otherNumerator, BigInteger.ONE)))
                .isEqualTo(new BigRational(expected, BigInteger.ONE));
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
    void max_should_succeed(final BigInteger numerator, final BigInteger otherNumerator, final BigInteger expected) {
        assertThat(new BigRational(numerator, BigInteger.ONE).max(new BigRational(otherNumerator, BigInteger.ONE)))
                .isEqualTo(new BigRational(expected, BigInteger.ONE));
    }

    @ParameterizedTest
    @ValueSource(longs = {-2L, 2L})
    void abs_should_succeed(final long numerator) {
        assertThat(new BigRational(BigInteger.valueOf(numerator), BigInteger.ONE).abs())
                .isEqualTo(new BigRational(BigInteger.valueOf(numerator).abs(), BigInteger.ONE));
    }

    @Test
    void toBigDecimal_with_scale_and_roundingMode_should_succeed() {
        assertThat(new BigRational(BigInteger.ONE, BigInteger.valueOf(3L)).toBigDecimal(2, RoundingMode.UP))
                .isEqualTo(new BigDecimal("0.34"));
    }

    @Test
    void toBigDecimal_with_roundingMode_should_succeed() {
        assertThat(new BigRational(BigInteger.ONE, BigInteger.valueOf(3L)).toBigDecimal(RoundingMode.UP))
                .isOne();
    }

    @Test
    void toBigDecimal_with_mathContext_should_succeed() {
        assertThat(new BigRational(BigInteger.ONE, BigInteger.valueOf(3L)).toBigDecimal(MathContext.DECIMAL32))
                .isEqualTo(new BigDecimal("0.3333333"));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 1, -1
        1, 1,  0
        1, 2,  1
        """)
    void compareTo_should_succeed(final BigInteger numerator, final BigInteger denominator, final int expected) {
        assertThat(BigRational.ONE.compareTo(new BigRational(numerator, denominator)))
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
        void compareTo_should_succeed(final BigInteger numerator, final BigInteger denominator, final int expected) {
            assertThat(BigRational.COMPARATOR.compare(BigRational.ONE, new BigRational(numerator, denominator)))
                    .isEqualTo(expected);
        }
    }
}
