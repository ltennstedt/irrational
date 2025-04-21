package sliderule.core.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class AbstractNumberTest {
    @Test
    void isNotInvertible_should_return_false_isInvertible_is_true() {
        assertThat(LongRational.ONE.isNotInvertible()).isFalse();
    }

    @Test
    void isNotInvertible_should_return_true_isInvertible_is_true() {
        assertThat(LongRational.ZERO.isNotInvertible()).isTrue();
    }

    @Test
    void isLessThan_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isLessThan(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1})
    void isLessThan_should_return_false_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(LongRational.ONE.isLessThan(new LongRational(numerator, 1))).isFalse();
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
    @ValueSource(longs = {0, 1})
    void isLessThanOrEqualTo_should_return_true_when_compareTo_returns_negative_or_0(final long numerator) {
        assertThat(LongRational.ZERO.isLessThanOrEqualTo(new LongRational(numerator, 1)))
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
    @ValueSource(longs = {0, 1})
    void isGreaterThan_should_return_false_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(LongRational.ZERO.isGreaterThan(new LongRational(numerator, 1)))
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
    @ValueSource(longs = {0, 1})
    void isGreaterThanOrEqualTo_should_return_true_when_compareTo_returns_positive_or_0(final long numerator) {
        assertThat(LongRational.ONE.isGreaterThanOrEqualTo(new LongRational(numerator, 1)))
                .isTrue();
    }

    @Test
    void intValue_should_succeed() {
        assertThat(LongRational.ZERO.intValue()).isZero();
    }

    @Test
    void intValueExact_should_succeed() {
        assertThat(LongRational.ZERO.intValueExact()).isZero();
    }

    @Test
    void longValue_should_succeed() {
        assertThat(LongRational.ZERO.longValue()).isZero();
    }

    @Test
    void longValueExact_should_succeed() {
        assertThat(LongRational.ZERO.longValueExact()).isZero();
    }

    @Test
    void floatValue_should_succeed() {
        assertThat(LongRational.ZERO.floatValue()).isZero();
    }

    @Test
    void doubleValue_should_succeed() {
        assertThat(LongRational.ZERO.doubleValue()).isZero();
    }
}
