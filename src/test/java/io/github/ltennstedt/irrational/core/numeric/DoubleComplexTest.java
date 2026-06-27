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

final class DoubleComplexTest {
    private final Offset<Double> withinEpsilon = within(Doubles.EPSILON);
    private final DoubleComplex complex1 = new DoubleComplex(1D, 2D);
    private final DoubleComplex complex2 = new DoubleComplex(3D, 4D);

    @ParameterizedTest
    @CsvSource(textBlock = """
        NaN,       0,         real,      NaN
        -Infinity, 0,         real,      -Infinity
         Infinity, 0,         real,       Infinity
        0,         NaN,       imaginary, NaN
        0,         -Infinity, imaginary, -Infinity
        0,          Infinity, imaginary,  Infinity
        """)
    void new_should_throw_exception(final double real, final double imaginary, final String name, final String part) {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new DoubleComplex(real, imaginary))
                .withMessage("%s must not be NaN and must be finite but was %s".formatted(name, part))
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        -0, -0, 0, 0
         1,  0, 1, 0
         0,  1, 0, 1
        """)
    void new_should_succeed(
            final double real, final double imaginary, final double expectedReal, final double expectedImaginary) {
        assertThat(new DoubleComplex(real, imaginary)).isEqualTo(new DoubleComplex(expectedReal, expectedImaginary));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         NaN,       0,        radius,  NaN
        -Infinity,  0,        radius, -Infinity
         Infinity,  0,        radius,  Infinity
         0,         NaN,      angle,   NaN
         0,        -Infinity, angle,  -Infinity
         0,         Infinity, angle,   Infinity
        """)
    void ofPolar_should_throw_exception(
            final double radius, final double argument, final String name, final String part) {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> DoubleComplex.ofPolar(radius, argument))
                .withMessage("%s must not be NaN and must be finite but was %s".formatted(name, part))
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         0,   0,  0,  0
        -1,   0, -1,  0
        -1, 0.5,  0, -1
        -1,   1,  1,  0
        -1, 1.5,  0,  1
         1,   0,  1,  0
         1, 0.5,  0,  1
         1,   1, -1,  0
         1, 1.5,  0, -1
        """)
    void ofPolar_should_succeed(
            final double radius, final double factor, final double expectedReal, final double expectedImaginary) {
        final var actual = DoubleComplex.ofPolar(radius, factor * StrictMath.PI);

        assertThat(actual.real()).isCloseTo(expectedReal, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, false
        0, 1, true
        1, 0, true
        """)
    void isInvertible_should_succeed(final double real, final double imaginary, final boolean expected) {
        assertThat(new DoubleComplex(real, imaginary).isInvertible()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1,     1,     false
        1e-13, 1e-13, true
        """)
    void isZero_should_succeed(final double real, final double imaginary, final boolean expected) {
        assertThat(new DoubleComplex(real, imaginary).isZero()).isEqualTo(expected);
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleComplex.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(complex1.add(complex2)).isEqualTo(new DoubleComplex(4D, 6D));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleComplex.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(complex1.subtract(complex2)).isEqualTo(new DoubleComplex(-2D, -2D));
    }

    @Test
    void multiply_should_throw_exception_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleComplex.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        assertThat(complex1.multiply(complex2)).isEqualTo(new DoubleComplex(-5D, 10D));
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleComplex.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> DoubleComplex.ONE.divide(DoubleComplex.ZERO))
                .withMessage("divisor must be invertible but was DoubleComplex[real=0.0, imaginary=0.0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 3, 1, 0
        2, 1, 1, 1
        """)
    void divide_should_succeed(
            final double real, final double imaginary, final double expectedReal, final double expectedImaginary) {
        assertThat(new DoubleComplex(1D, 3D).divide(new DoubleComplex(real, imaginary)))
                .isEqualTo(new DoubleComplex(expectedReal, expectedImaginary));
    }

    @Test
    void negate_should_succeed() {
        assertThat(complex1.negate()).isEqualTo(new DoubleComplex(-1D, -2D));
    }

    @Test
    void pow_should_throw_exception_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> DoubleComplex.ZERO.pow(-1))
                .withMessage("this must be invertible but was DoubleComplex[real=0.0, imaginary=0.0]")
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
            final double real,
            final double imaginary,
            final int exponent,
            final double expectedReal,
            final double expectedImaginary) {
        final var actual = new DoubleComplex(real, imaginary).pow(exponent);

        assertThat(actual.real()).isCloseTo(expectedReal, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void reciprocal_should_throw_exception_when_this_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(DoubleComplex.ZERO::reciprocal)
                .withMessage("this must be invertible but was DoubleComplex[real=0.0, imaginary=0.0]")
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
            final double real, final double imaginary, final double expectedReal, final double expectedImaginary) {
        final var actual = new DoubleComplex(real, imaginary).reciprocal();

        assertThat(actual.real()).isCloseTo(expectedReal, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void conjugate_should_succeed() {
        assertThat(complex1.conjugate()).isEqualTo(new DoubleComplex(1D, -2D));
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
    void abs_should_succeed(final double real, final double imaginary, final double expected) {
        assertThat(new DoubleComplex(real, imaginary).abs()).isCloseTo(expected, withinEpsilon);
    }

    @Test
    void arg_should_succeed() {
        assertThat(DoubleComplex.ONE.arg()).isCloseTo(0D, withinEpsilon);
    }
}
