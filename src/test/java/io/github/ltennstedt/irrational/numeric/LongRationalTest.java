package io.github.ltennstedt.irrational.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

@Execution(ExecutionMode.CONCURRENT)
final class LongRationalTest {
    private final LongRational rational1 = LongRational.of(2L, 3L);
    private final LongRational rational2 = LongRational.of(4L, 5L);

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 2})
    void of_numerator_should_succeed(final long numerator) {
        assertThat(LongRational.of(numerator)).isEqualTo(LongRational.of(numerator, 1L));
    }

    @Test
    void of_numerator_and_denominator_should_throw_exception_when_denominator_is_zero() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LongRational.of(1L, 0L))
                .withMessage("denominator must not be 0 but was 0")
                .withNoCause();
    }

    @Test
    void of_numerator_and_denominator_should_return_ZERO_when_numerator_is_0() {
        assertThat(LongRational.of(0L, 1L)).isSameAs(LongRational.ZERO);
    }

    @ParameterizedTest
    @LongRangeSource(from = 1L, to = 10L)
    void of_numerator_and_denominator_should_return_ONE_when_numerator_equals_denominator(final long l) {
        assertThat(LongRational.of(l, l)).isSameAs(LongRational.ONE);
    }

    @Test
    void of_numerator_and_denominator_should_normalize() {
        assertThat(LongRational.of(2L, -4L)).isEqualByComparingTo(LongRational.of(-1L, 2L));
    }

    @Test
    void of_numerator_and_denominator_should_succeed() {
        assertThat(rational1.numerator()).isEqualByComparingTo(2L);
        assertThat(rational1.denominator()).isEqualByComparingTo(3L);
    }

    @Test
    void isInvertible_should_be_false_when_numerator_is_0() {
        assertThat(LongRational.ZERO.isInvertible()).isFalse();
    }

    @Test
    void isInvertible_should_be_true_when_numerator_is_not_0() {
        assertThat(LongRational.ONE.isInvertible()).isTrue();
    }

    @Test
    void isZero_should_be_false_when_rational_is_not_zero() {
        assertThat(LongRational.ONE.isZero()).isFalse();
    }

    @Test
    void isZero_should_be_true_when_rational_is_zero() {
        assertThat(LongRational.ZERO.isZero()).isTrue();
    }

    @Test
    void isOne_should_be_false_when_rational_is_not_one() {
        assertThat(LongRational.ZERO.isOne()).isFalse();
    }

    @Test
    void isOne_should_be_true_when_rational_is_one() {
        assertThat(LongRational.ONE.isOne()).isTrue();
    }

    @Test
    void isInteger_should_be_false_when_denominator_is_greater_than_one() {
        assertThat(rational1.isInteger()).isFalse();
    }

    @Test
    void isInteger_should_be_true_when_denominator_is_one() {
        assertThat(LongRational.ONE.isInteger()).isTrue();
    }

    @Test
    void isUnit_should_be_false_when_numerator_is_not_1Fraction() {
        assertThat(rational1.isUnitFraction()).isFalse();
    }

    @Test
    void isUnit_should_be_true_when_numerator_is_oneFraction() {
        assertThat(LongRational.ONE.isUnitFraction()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 5L, 6L, 7L, 9L})
    void isDyadic_should_be_false_when_denominator_is_not_power_of_two(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 4L, 8L})
    void isDyadic_should_be_true_when_denominator_is_power_of_two(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isDyadic()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 4L, 8L})
    void isNotDyadic_should_be_false_when_denominator_is_power_of_two(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isNotDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 5L, 6L, 7L, 9L})
    void isNotDyadic_should_be_true_when_denominator_is_not_power_of_two(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isNotDyadic()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {-2L, -1L, 1L, 2L})
    void isProper_should_be_false_when_absolute_numerator_is_not_less_than_denominator(final long numerator) {
        assertThat(LongRational.of(numerator).isProper()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 1L})
    void isProper_should_be_true_when_absolute_numerator_is_less_than_denominator(final long numerator) {
        assertThat(LongRational.of(numerator, 2L).isProper()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void isPositive_should_be_false_when_numerator_is_not_greater_than_zero(final long numerator) {
        assertThat(LongRational.of(numerator).isPositive()).isFalse();
    }

    @Test
    void isPositive_should_be_true_when_numerator_is_greater_than_zero() {
        assertThat(LongRational.ONE.isPositive()).isTrue();
    }

    @Test
    void negate_should_succeed() {
        assertThat(LongRational.ONE.negate()).isEqualByComparingTo(LongRational.of(-1L));
    }

    @ParameterizedTest
    @ValueSource(longs = {-2L, 2L})
    void abs_should_succeed(final long numerator) {
        assertThat(LongRational.of(numerator).abs()).isEqualByComparingTo(LongRational.of(Math.absExact(numerator)));
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> rational1.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(rational1.add(rational2)).isEqualByComparingTo(LongRational.of(22L, 15L));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> rational1.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(rational1.subtract(rational2)).isEqualByComparingTo(LongRational.of(-2L, 15L));
    }

    @Test
    void multiply_should_throw_exception_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> rational1.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        assertThat(rational1.multiply(rational2)).isEqualByComparingTo(LongRational.of(8L, 15L));
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> rational1.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> rational1.divide(LongRational.ZERO))
                .withMessage("divisor must be invertible but was LongRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        assertThat(rational1.divide(rational2)).isEqualByComparingTo(LongRational.of(5L, 6L));
    }

    @Test
    void invert_should_throw_exception_when_this_is_not_invertible() {
        assertThatIllegalStateException()
                .isThrownBy(LongRational.ZERO::invert)
                .withMessage("this must be invertible but was LongRational[numerator=0, denominator=1]")
                .withNoCause();
    }

    @Test
    void invert_should_succeed() {
        assertThat(rational1.invert()).isEqualByComparingTo(LongRational.of(3L, 2L));
    }

    @Test
    void power_should_succeed() {
        assertThat(rational1.power(2)).isEqualByComparingTo(LongRational.of(4L, 9L));
    }

    @ParameterizedTest
    @LongRangeSource(from = -1L, to = 1L, closed = true)
    void signum_should_succeed(final long numerator) {
        assertThat(LongRational.of(numerator).signum()).isEqualByComparingTo(Long.signum(numerator));
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
    void min_should_succeed(final long numerator, final long otherNumerator, final long expeced) {
        assertThat(LongRational.of(numerator).min(LongRational.of(otherNumerator)))
                .isSameAs(LongRational.of(expeced));
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
    void max_should_succeed(final long numerator, final long otherNumerator, final long expeced) {
        assertThat(LongRational.of(numerator).max(LongRational.of(otherNumerator)))
                .isSameAs(LongRational.of(expeced));
    }

    @Test
    void increment_should_succeed() {
        assertThat(LongRational.ZERO.increment()).isSameAs(LongRational.ONE);
    }

    @Test
    void decrement_should_succeed() {
        assertThat(LongRational.ONE.decrement()).isSameAs(LongRational.ZERO);
    }

    @Test
    void toBigDecimal_with_scale_and_roundingMode_should_succeed() {
        assertThat(LongRational.of(1L, 3L).toBigDecimal(2, RoundingMode.UP)).isEqualTo(new BigDecimal("0.34"));
    }

    @Test
    void toBigDecimal_with_roundingMode_should_succeed() {
        assertThat(LongRational.of(1L, 3L).toBigDecimal(RoundingMode.UP)).isOne();
    }

    @Test
    void toBigDecimal_with_mathContext_should_succeed() {
        assertThat(LongRational.of(1L, 3L).toBigDecimal(MathContext.DECIMAL32)).isEqualTo(new BigDecimal("0.3333333"));
    }
}
