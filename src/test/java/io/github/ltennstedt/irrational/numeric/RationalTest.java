package io.github.ltennstedt.irrational.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Execution(ExecutionMode.CONCURRENT)
final class RationalTest {
    @Test
    void isNotUnit_should_be_false_when_isUnit_is_trueFraction() {
        assertThat(LongRational.of(1L, 2L).isNotUnit()).isFalse();
    }

    @Test
    void isNotUnit_should_be_true_when_isUnit_is_falseFraction() {
        assertThat(LongRational.of(2L).isNotUnit()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 4L, 8L})
    void isNotDyadic_should_be_false_when_isDyadic_is_true(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isNotDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 5L, 6L, 7L, 9L})
    void isNotDyadic_should_be_true_when_isDyadic_is_false(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isNotDyadic()).isTrue();
    }

    @Test
    void isImproper_should_be_false_when_isProper_is_true() {
        assertThat(LongRational.ZERO.isImproper()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 1L})
    void isImproper_should_be_true_when_isProper_is_false(final long numerator) {
        assertThat(LongRational.of(numerator).isImproper()).isTrue();
    }

    @Test
    void isNegative_should_be_false_when_isPositive_is_true() {
        assertThat(LongRational.ONE.isNegative()).isFalse();
    }

    @Test
    void isNegative_should_be_false_when_isZero_is_true() {
        assertThat(LongRational.ZERO.isNegative()).isFalse();
    }

    @Test
    void isNegative_should_be_true_when_isPositive_and_isZero_are_false() {
        assertThat(LongRational.of(-1L, 1L).isNegative()).isTrue();
    }

    @Test
    void isLessThan_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isLessThan(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void isLessThan_should_return_false_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(LongRational.ONE.isLessThan(LongRational.of(numerator, 1L))).isFalse();
    }

    @Test
    void isLessThan_should_return_true_when_compareTo_returns_negative() {
        assertThat(LongRational.ZERO.isLessThan(LongRational.ONE)).isTrue();
    }

    @Test
    void isLessThanOrEqualTo_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isLessThanOrEqualTo(null))
                .withMessage("other")
                .withNoCause();
    }

    @Test
    void isLessThanOrEqualTo_should_return_false_when_compareTo_returns_positive() {
        assertThat(LongRational.ONE.isLessThanOrEqualTo(LongRational.ZERO)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void isLessThanOrEqualTo_should_return_true_when_compareTo_returns_negative_or_0(final long numerator) {
        assertThat(LongRational.ZERO.isLessThanOrEqualTo(LongRational.of(numerator, 1L)))
                .isTrue();
    }

    @Test
    void isGreaterThan_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isGreaterThan(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void isGreaterThan_should_return_false_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(LongRational.ZERO.isGreaterThan(LongRational.of(numerator, 1L)))
                .isFalse();
    }

    @Test
    void isGreaterThan_should_return_true_when_compareTo_returns_negative() {
        assertThat(LongRational.ONE.isGreaterThan(LongRational.ZERO)).isTrue();
    }

    @Test
    void isGreaterThanOrEqualTo_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isGreaterThanOrEqualTo(null))
                .withMessage("other")
                .withNoCause();
    }

    @Test
    void isGreaterThanOrEqualTo_should_return_false_when_compareTo_returns_negative() {
        assertThat(LongRational.ZERO.isGreaterThanOrEqualTo(LongRational.ONE)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void isGreaterThanOrEqualTo_should_return_true_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(LongRational.ONE.isGreaterThanOrEqualTo(LongRational.of(numerator, 1L)))
                .isTrue();
    }
}
