package io.github.ltennstedt.irrational.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Execution(ExecutionMode.CONCURRENT)
final class RationalTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 2, false
        2, 1, true
    """)
    void isNotUnit_should_succeed(final long numerator, final long denominator, final boolean expected) {
        assertThat(LongRational.of(numerator, denominator).isNotUnitFraction()).isSameAs(expected);
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
    void isNotDyadic_should_succeed(final long denominator, final boolean expected) {
        assertThat(LongRational.of(1L, denominator).isNotDyadic()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        -1, true
         0, false
         1, true
    """)
    void isImproper_should_succeed(final long numerator, final boolean expected) {
        assertThat(LongRational.of(numerator).isImproper()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        -1, true
         0, false
         1, false
    """)
    void isNegative_should_succeed(final long numerator, final boolean expected) {
        assertThat(LongRational.of(numerator).isNegative()).isSameAs(expected);
    }

    @Test
    void isLessThan_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isLessThan(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 0, false
        1, 1, false
        0, 1, true
    """)
    void isLessThan_should_succeed(final long numerator, final long otherNumerator, final boolean expected) {
        assertThat(LongRational.of(numerator).isLessThan(LongRational.of(otherNumerator)))
                .isSameAs(expected);
    }

    @Test
    void isLessThanOrEqualTo_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isLessThanOrEqualTo(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 0, false
        0, 0, true
        0, 1, true
    """)
    void isLessThanOrEqualTo_should_succeed(final long numerator, final long otherNumerator, final boolean expected) {
        assertThat(LongRational.of(numerator).isLessThanOrEqualTo(LongRational.of(otherNumerator)))
                .isSameAs(expected);
    }

    @Test
    void isGreaterThan_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isGreaterThan(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, false
        0, 1, false
        1, 0, true
    """)
    void isGreaterThan_should_succeed(final long numerator, final long otherNumerator, final boolean expected) {
        assertThat(LongRational.of(numerator).isGreaterThan(LongRational.of(otherNumerator)))
                .isSameAs(expected);
    }

    @Test
    void isGreaterThanOrEqualTo_should_throw_Exception_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongRational.ZERO.isGreaterThanOrEqualTo(null))
                .withMessage("other")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 1, false
        1, 1, true
        1, 0, true
    """)
    void isGreaterThanOrEqualTo_should_succeed(
            final long numerator, final long otherNumerator, final boolean expected) {
        assertThat(LongRational.of(numerator).isGreaterThanOrEqualTo(LongRational.of(otherNumerator)))
                .isSameAs(expected);
    }
}
