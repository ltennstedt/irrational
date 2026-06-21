package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;

import io.github.ltennstedt.irrational.core.util.Doubles;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class LongGaussianTest {
    private final Offset<Double> withinEpsilon = within(Doubles.EPSILON);
    private final LongGaussian complex1 = new LongGaussian(1L, 2L);
    private final LongGaussian complex2 = new LongGaussian(3L, 4L);

    @ParameterizedTest
    @CsvSource(textBlock = """
        -0, -0, 0, 0
         1,  0, 1, 0
         0,  1, 0, 1
        """)
    void new_should_succeed(
            final long real, final long imaginary, final long expectedReal, final long expectedImaginary) {
        assertThat(new LongGaussian(real, imaginary)).isEqualTo(new LongGaussian(expectedReal, expectedImaginary));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, false
        0, 1, true
        1, 0, true
        """)
    void isInvertible_should_succeed(final long real, final long imaginary, final boolean expected) {
        assertThat(new LongGaussian(real, imaginary).isInvertible()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 1, false
        0, 0, true
        """)
    void isZero_should_succeed(final long real, final long imaginary, final boolean expected) {
        assertThat(new LongGaussian(real, imaginary).isZero()).isEqualTo(expected);
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
        assertThat(new LongGaussian(real, imaginary).isUnit()).isEqualTo(expected);
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongGaussian.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(complex1.add(complex2)).isEqualTo(new LongGaussian(4L, 6L));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongGaussian.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(complex1.subtract(complex2)).isEqualTo(new LongGaussian(-2L, -2L));
    }

    @Test
    void multiply_should_throw_exception_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongGaussian.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        assertThat(complex1.multiply(complex2)).isEqualTo(new LongGaussian(-5L, 10L));
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongGaussian.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_not_invertible() {
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
        assertThat(new LongGaussian(1L, 3L).divide(new LongGaussian(real, imaginary)))
                .isEqualTo(new DoubleComplex(expectedReal, expectedImaginary));
    }

    @Test
    void pow_should_throw_exception_when_exponent_is_negative_and_is_not_invertible() {
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
        final var actual = new LongGaussian(real, imaginary).pow(exponent);

        assertThat(actual.real()).isCloseTo(expectedReal, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void reciprocal_should_throw_exception_when_this_not_invertible() {
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
            final long real, final long imaginary, final long expectedReal, final long expectedImaginary) {
        final var actual = new LongGaussian(real, imaginary).reciprocal();

        assertThat(actual.real()).isCloseTo(expectedReal, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void negate_should_succeed() {
        assertThat(complex1.negate()).isEqualTo(new LongGaussian(-1L, -2L));
    }

    @Test
    void conjugate_should_succeed() {
        assertThat(complex1.conjugate()).isEqualTo(new LongGaussian(1L, -2L));
    }

    @Test
    void norm_should_succeed() {
        assertThat(complex1.norm()).isCloseTo(5D, withinEpsilon);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 0, 2
        0, 3, 3
        """)
    void abs_should_succeed(final long real, final long imaginary, final long expected) {
        assertThat(new LongGaussian(real, imaginary).abs()).isCloseTo(expected, withinEpsilon);
    }

    @Test
    void arg_should_succeed() {
        assertThat(LongGaussian.ONE.arg()).isCloseTo(0D, withinEpsilon);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         1,  0, 1, 0
         0,  1, 1, 0.5
        -1,  0, 1, 1
         0, -1, 1, 1.5
        """)
    void toPolar_should_succeed(
            final long real, final long imaginary, final double expectedRadius, final double expectedFactor) {
        final var polar = new LongGaussian(real, imaginary).toPolar();

        assertThat(polar.radius()).isCloseTo(expectedRadius, withinEpsilon);
        assertThat(polar.angle()).isCloseTo(expectedFactor * StrictMath.PI, withinEpsilon);
    }
}
