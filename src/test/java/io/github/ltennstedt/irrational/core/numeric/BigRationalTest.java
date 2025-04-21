package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

@Execution(ExecutionMode.CONCURRENT)
final class BigRationalTest {
    private final BigRational rational1 = BigRational.of(BigInteger.TWO, BigInteger.valueOf(3L));
    private final BigRational rational2 = BigRational.of(BigInteger.valueOf(4L), BigInteger.valueOf(5L));

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 2})
    void of_numerator_should_succeed(final long numerator) {
        assertThat(BigRational.of(BigInteger.valueOf(numerator)))
                .isEqualTo(BigRational.of(BigInteger.valueOf(numerator)));
    }

    @Test
    void of_numerator_and_denominator_should_throw_exception_when_denominator_is_zero() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BigRational.of(BigInteger.ONE, BigInteger.ZERO))
                .withMessage("denominator must not be 0 but was 0")
                .withNoCause();
    }

    @Test
    void of_numerator_and_denominator_should_return_ZERO_when_numerator_is_0() {
        assertThat(BigRational.of(BigInteger.ZERO, BigInteger.ONE)).isSameAs(BigRational.ZERO);
    }

    @Test
    void of_numerator_and_denominator_should_return_ONE_when_numerator_equals_denominator() {
        assertThat(BigRational.of(BigInteger.TWO, BigInteger.TWO)).isSameAs(BigRational.ONE);
    }

    @Test
    void of_numerator_and_denominator_should_normalize() {
        assertThat(BigRational.of(BigInteger.TWO, BigInteger.valueOf(-4L)))
                .isEqualTo(BigRational.of(BigInteger.ONE.negate(), BigInteger.TWO));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isInvertible_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(BigRational.of(numerator).isInvertible()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, false
            0, true
        """)
    void isZero_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(BigRational.of(numerator).isZero()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isOne_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(BigRational.of(numerator).isOne()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, 2, false
            1, 1, true
        """)
    void isInteger_should_succeed(final BigInteger numerator, final BigInteger denominator, final boolean expected) {
        assertThat(BigRational.of(numerator, denominator).isInteger()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, false
            1, true
        """)
    void isUnit_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(BigRational.of(numerator).isUnitFraction()).isSameAs(expected);
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
        assertThat(BigRational.of(BigInteger.ONE, denominator).isDyadic()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, false
            2, false
            3, true
            4, false
            5, true
            6, true
            7, true
            8, false
            9, true
        """)
    void isNotDyadic_should_succeed(final BigInteger denominator, final boolean expected) {
        assertThat(BigRational.of(BigInteger.ONE, denominator).isNotDyadic()).isSameAs(expected);
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
        assertThat(BigRational.of(numerator, denominator).isProper()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            -1, false
             0, false
             1, true
        """)
    void isPositive_should_succeed(final BigInteger numerator, final boolean expected) {
        assertThat(BigRational.of(numerator).isPositive()).isSameAs(expected);
    }

    @Test
    void negate_should_succeed() {
        assertThat(BigRational.ONE.negate()).isEqualTo(BigRational.of(BigInteger.ONE.negate()));
    }

    @ParameterizedTest
    @ValueSource(longs = {-2L, 2L})
    void abs_should_succeed(final long numerator) {
        assertThat(BigRational.of(BigInteger.valueOf(numerator)).abs())
                .isEqualTo(BigRational.of(BigInteger.valueOf(numerator).abs()));
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(rational1.add(rational2))
                .isEqualTo(BigRational.of(BigInteger.valueOf(22L), BigInteger.valueOf(15L)));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(rational1.subtract(rational2))
                .isEqualTo(BigRational.of(BigInteger.valueOf(-2L), BigInteger.valueOf(15L)));
    }

    @Test
    void multiply_should_throw_exception_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        assertThat(rational1.multiply(rational2))
                .isEqualTo(BigRational.of(BigInteger.valueOf(8L), BigInteger.valueOf(15L)));
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigRational.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BigRational.ONE.divide(BigRational.ZERO))
                .withMessage("divisor must be invertible but was BigRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        assertThat(rational1.divide(rational2))
                .isEqualTo(BigRational.of(BigInteger.valueOf(5L), BigInteger.valueOf(6L)));
    }

    @Test
    void invert_should_throw_exception_when_this_is_not_invertible() {
        assertThatIllegalStateException()
                .isThrownBy(BigRational.ZERO::invert)
                .withMessage("this must be invertible but was BigRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void invert_should_succeed() {
        assertThat(rational1.invert()).isEqualTo(BigRational.of(BigInteger.valueOf(3L), BigInteger.valueOf(2L)));
    }

    @Test
    void power_should_succeed() {
        assertThat(rational1.power(2)).isEqualTo(BigRational.of(BigInteger.valueOf(4L), BigInteger.valueOf(9L)));
    }

    @ParameterizedTest
    @LongRangeSource(from = -1L, to = 1L, closed = true)
    void signum_should_succeed(final long numerator) {
        assertThat(BigRational.of(BigInteger.valueOf(numerator)).signum()).isEqualTo(Long.signum(numerator));
    }

    @Test
    void min_should_throw_Exception_when_other_is_null() {
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
        assertThat(BigRational.of(numerator).min(BigRational.of(otherNumerator)))
                .isSameAs(BigRational.of(expected));
    }

    @Test
    void max_should_throw_Exception_when_other_is_null() {
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
        assertThat(BigRational.of(numerator).max(BigRational.of(otherNumerator)))
                .isSameAs(BigRational.of(expected));
    }

    @Test
    void increment_should_succeed() {
        assertThat(BigRational.ZERO.increment()).isSameAs(BigRational.ONE);
    }

    @Test
    void decrement_should_succeed() {
        assertThat(BigRational.ONE.decrement()).isSameAs(BigRational.ZERO);
    }

    @Test
    void toBigDecimal_with_scale_and_roundingMode_should_succeed() {
        assertThat(BigRational.of(BigInteger.ONE, BigInteger.valueOf(3L)).toBigDecimal(2, RoundingMode.UP))
                .isEqualTo(new BigDecimal("0.34"));
    }

    @Test
    void toBigDecimal_with_roundingMode_should_succeed() {
        assertThat(BigRational.of(BigInteger.ONE, BigInteger.valueOf(3L)).toBigDecimal(RoundingMode.UP))
                .isOne();
    }

    @Test
    void toBigDecimal_with_mathContext_should_succeed() {
        assertThat(BigRational.of(BigInteger.ONE, BigInteger.valueOf(3L)).toBigDecimal(MathContext.DECIMAL32))
                .isEqualTo(new BigDecimal("0.3333333"));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 1, -1
        1, 1,  0
        1, 2,  1
        """)
    void compareTo_should_succeed(final BigInteger numerator, final BigInteger denominator, final int expected) {
        assertThat(BigRational.ONE.compareTo(BigRational.of(numerator, denominator)))
                .isSameAs(expected);
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
            assertThat(BigRational.COMPARATOR.compare(BigRational.ONE, BigRational.of(numerator, denominator)))
                    .isSameAs(expected);
        }
    }
}
