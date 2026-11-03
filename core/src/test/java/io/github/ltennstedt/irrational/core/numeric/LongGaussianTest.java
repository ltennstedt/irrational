package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class LongGaussianTest {
    private static final Offset<Double> OFFSET = within(1e-12D);

    @ParameterizedTest
    @CsvSource(textBlock = """
        -0, -0, 0, 0
         1,  0, 1, 0
         0,  1, 0, 1
        """)
    void new_should_succeed(
            final long real, final long imaginary, final long expectedReal, final long expectedImaginary) {
        final var actual = new LongGaussian(real, imaginary);

        final var expected = new LongGaussian(expectedReal, expectedImaginary);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, false
        0, 1, true
        1, 0, true
        """)
    void isInvertible_should_succeed(final long real, final long imaginary, final boolean expected) {
        final var gaussian = new LongGaussian(real, imaginary);

        final var actual = gaussian.isInvertible();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 1, false
        0, 0, true
        """)
    void isZero_should_succeed(final long real, final long imaginary, final boolean expected) {
        final var gaussian = new LongGaussian(real, imaginary);

        final var actual = gaussian.isZero();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         0,  0, false
         1,  0, true
         0,  1, true
        -1,  0, true
         0, -1, true
        """)
    void isUnit_should_succeed(final long real, final long imaginary, final boolean expected) {
        final var gaussian = new LongGaussian(real, imaginary);

        final var actual = gaussian.isUnit();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongGaussian.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        final var gaussian = new LongGaussian(1L, 2L);
        final var summand = new LongGaussian(3L, 4L);

        final var actual = gaussian.add(summand);

        final var expected = new LongGaussian(4L, 6L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtract_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongGaussian.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        final var gaussian = new LongGaussian(1L, 2L);
        final var subtrahend = new LongGaussian(3L, 4L);

        final var actual = gaussian.subtract(subtrahend);

        final var expected = new LongGaussian(-2L, -2L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void multiply_should_throw_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongGaussian.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        final var gaussian = new LongGaussian(1L, 2L);
        final var multiplier = new LongGaussian(3L, 4L);

        final var actual = gaussian.multiply(multiplier);

        final var expected = new LongGaussian(-5L, 10L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void divide_should_throw_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongGaussian.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> LongGaussian.ONE.divide(LongGaussian.ZERO))
                .withMessage("divisor must be invertible but was LongGaussian[real=0, imaginary=0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 3, 1, 0
        2, 1, 1, 1
        """)
    void divide_should_succeed(
            final long real, final long imaginary, final double expectedReal, final double expectedImaginary) {
        final var gaussian = new LongGaussian(1L, 3L);
        final var divisor = new LongGaussian(real, imaginary);

        final var actual = gaussian.divide(divisor);

        final var expected = new DoubleComplex(expectedReal, expectedImaginary);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void negate_should_succeed() {
        final var gaussian = new LongGaussian(1L, 2L);

        final var actual = gaussian.negate();

        final var expected = new LongGaussian(-1L, -2L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void pow_should_throw_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> LongGaussian.ZERO.pow(-1))
                .withMessage("this must be invertible but was LongGaussian[real=0, imaginary=0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 1, -1,   0.4, -0.2
        0, 1,  0,   1,    0
        2, 3,  1,   2,    3
        0, 0,  2,   0,    0
        0, 1,  2,  -1,    0
        2, 3,  3, -46,    9
        """)
    void pow_should_succeed(
            final long real,
            final long imaginary,
            final int exponent,
            final double expectedReal,
            final double expectedImaginary) {
        final var gaussian = new LongGaussian(real, imaginary);

        final var actual = gaussian.pow(exponent);

        assertThat(actual.real()).isCloseTo(expectedReal, OFFSET);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @Test
    void reciprocal_should_throw_when_this_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(LongGaussian.ZERO::reciprocal)
                .withMessage("this must be invertible but was LongGaussian[real=0, imaginary=0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         1,  0,  1,  0
         0,  1,  0, -1
        -1,  0, -1,  0
         0, -1,  0,  1
        """)
    void reciprocal_should_succeed(
            final long real, final long imaginary, final double expectedReal, final double expectedImaginary) {
        final var gaussian = new LongGaussian(real, imaginary);

        final var actual = gaussian.reciprocal();

        assertThat(actual.real()).isCloseTo(expectedReal, OFFSET);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @Test
    void conjugate_should_succeed() {
        final var gaussian = new LongGaussian(1L, 2L);

        final var actual = gaussian.conjugate();

        final var expected = new LongGaussian(1L, -2L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void norm_should_succeed() {
        final var gaussian = new LongGaussian(1L, 2L);

        final var actual = gaussian.norm();

        assertThat(actual).isCloseTo(5.0D, OFFSET);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 0, 2
        0, 3, 3
        """)
    void abs_should_succeed(final long real, final long imaginary, final double expected) {
        final var gaussian = new LongGaussian(real, imaginary);

        final var actual = gaussian.abs();

        assertThat(actual).isCloseTo(expected, OFFSET);
    }

    @Test
    void arg_should_succeed() {
        final var actual = LongGaussian.ONE.arg();

        assertThat(actual).isCloseTo(0D, OFFSET);
    }
}
