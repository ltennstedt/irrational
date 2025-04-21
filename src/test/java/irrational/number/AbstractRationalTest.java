package irrational.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

final class AbstractRationalTest {
    @ParameterizedTest
    @LongRangeSource(from = 1L, to = 9L)
    void isNotUnit_should_return_false_when_isUnit_returns_true(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isNotUnit()).isFalse();
    }

    @ParameterizedTest
    @LongRangeSource(from = 2L, to = 9L)
    void isNotUnit_should_return_true_when_isUnit_returns_false(final long numerator) {
        assertThat(LongRational.of(numerator).isNotUnit()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 4L, 8L})
    void isNotDyadic_should_return_false_when_isDyadic_returns_true(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isNotDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 5L, 6L, 7L, 9L})
    void isNotDyadic_should_return_true_when_isDyadic_returns_false(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isNotDyadic()).isTrue();
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
