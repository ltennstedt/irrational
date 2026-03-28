package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class NumericTest {
    @Test
    void pow_should_throw_exception_when_exponent_is_negative_and_is_not_invertible() {
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
        assertThat(new LongRational(numerator, denominator).pow(exponent))
                .isEqualTo(new LongRational(expectedNumerator, expectedDenominator));
    }
}
