package irrational.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.junitpioneer.jupiter.cartesian.CartesianTest.Values;
import org.junitpioneer.jupiter.params.LongRangeSource;

final class RationalTest {
    @ParameterizedTest
    @LongRangeSource(from = 1L, to = 9L)
    void isNotUnit_should_be_false_when_isUnit_is_true(final long denominator) {
        assertThat(SmallRational.of(1L, denominator).isNotUnit()).isFalse();
    }

    @ParameterizedTest
    @LongRangeSource(from = 2L, to = 9L)
    void isNotUnit_should_be_true_when_isUnit_is_false(final long numerator) {
        assertThat(SmallRational.of(numerator).isNotUnit()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 4L, 8L})
    void isNotDyadic_should_be_false_when_isDyadic_is_true(final long denominator) {
        assertThat(SmallRational.of(1L, denominator).isNotDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 5L, 6L, 7L, 9L})
    void isNotDyadic_should_be_true_when_isDyadic_is_false(final long denominator) {
        assertThat(SmallRational.of(1L, denominator).isNotDyadic()).isTrue();
    }

    @CartesianTest
    void isImproper_should_be_false_when_isProper_is_true(
            @Values(longs = {-1L, 1L}) final long numerator,
            @LongRangeSource(from = 2L, to = 9L) final long denominator) {
        assertThat(SmallRational.of(numerator, denominator).isImproper()).isFalse();
    }

    @CartesianTest
    void isImproper_should_be_true_when_isProper_is_false(
            @Values(longs = {-9L, 9L}) final long numerator,
            @LongRangeSource(from = 1L, to = 9L) final long denominator) {
        assertThat(SmallRational.of(numerator, denominator).isImproper()).isTrue();
    }

    @Test
    void isNegative_shoud_be_false_when_isPositive_is_true() {
        assertThat(SmallRational.ONE.isNegative()).isFalse();
    }

    @Test
    void isNegative_shoud_be_false_when_isZero_is_true() {
        assertThat(SmallRational.ZERO.isNegative()).isFalse();
    }

    @Test
    void isNegative_shoud_be_true_when_isPositive_and_isZero_are_false() {
        assertThat(SmallRational.of(-1L, 1L).isNegative()).isTrue();
    }

    @Test
    void isLessThan_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> SmallRational.ZERO.isLessThan(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void isLessThan_should_return_false_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(SmallRational.ONE.isLessThan(SmallRational.of(numerator, 1L)))
                .isFalse();
    }

    @Test
    void isLessThan_should_return_true_when_compareTo_returns_negative() {
        assertThat(SmallRational.ZERO.isLessThan(SmallRational.ONE)).isTrue();
    }

    @Test
    void isLessThanOrEqualTo_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> SmallRational.ZERO.isLessThanOrEqualTo(null))
                .withMessage("other")
                .withNoCause();
    }

    @Test
    void isLessThanOrEqualTo_should_return_false_when_compareTo_returns_positive() {
        assertThat(SmallRational.ONE.isLessThanOrEqualTo(SmallRational.ZERO)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void isLessThanOrEqualTo_should_return_true_when_compareTo_returns_negative_or_0(final long numerator) {
        assertThat(SmallRational.ZERO.isLessThanOrEqualTo(SmallRational.of(numerator, 1L)))
                .isTrue();
    }

    @Test
    void isGreaterThan_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> SmallRational.ZERO.isGreaterThan(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void isGreaterThan_should_return_false_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(SmallRational.ZERO.isGreaterThan(SmallRational.of(numerator, 1L)))
                .isFalse();
    }

    @Test
    void isGreaterThan_should_return_true_when_compareTo_returns_negative() {
        assertThat(SmallRational.ONE.isGreaterThan(SmallRational.ZERO)).isTrue();
    }

    @Test
    void isGreaterThanOrEqualTo_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> SmallRational.ZERO.isGreaterThanOrEqualTo(null))
                .withMessage("other")
                .withNoCause();
    }

    @Test
    void isGreaterThanOrEqualTo_should_return_false_when_compareTo_returns_negative() {
        assertThat(SmallRational.ZERO.isGreaterThanOrEqualTo(SmallRational.ONE)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void isGreaterThanOrEqualTo_should_return_true_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(SmallRational.ONE.isGreaterThanOrEqualTo(SmallRational.of(numerator, 1L)))
                .isTrue();
    }
}
