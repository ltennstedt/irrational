package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class DoubleComplexTest {
    private static final Offset<Double> OFFSET = within(1e-12D);

    @ParameterizedTest
    @CsvSource(textBlock = """
        NaN,       0,         real,      NaN
        -Infinity, 0,         real,      -Infinity
         Infinity, 0,         real,       Infinity
        0,         NaN,       imaginary, NaN
        0,         -Infinity, imaginary, -Infinity
        0,          Infinity, imaginary,  Infinity
        """)
    void new_should_throw(final double real, final double imaginary, final String name, final String part) {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new DoubleComplex(real, imaginary))
                .withMessage(name + " must be a finite number but was " + part)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         1,  0, 1, 0
         0,  1, 0, 1
        """)
    void new_should_succeed(
            final double real, final double imaginary, final double expectedReal, final double expectedImaginary) {
        final var actual = new DoubleComplex(real, imaginary);

        final var expected = new DoubleComplex(expectedReal, expectedImaginary);
        assertThat(actual).isEqualTo(expected);
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
    void ofPolar_should_throw(final double radius, final double argument, final String name, final String part) {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> DoubleComplex.ofPolar(radius, argument))
                .withMessage(name + " must be a finite number but was " + part)
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

        assertThat(actual.real()).isCloseTo(expectedReal, OFFSET);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, false
        0, 1, true
        1, 0, true
        """)
    void isInvertible_should_succeed(final double real, final double imaginary, final boolean expected) {
        final var complex = new DoubleComplex(real, imaginary);

        final var actual = complex.isInvertible();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 1, false
        1, 0, false
        0, 0, true
        """)
    void isZero_should_succeed(final double real, final double imaginary, final boolean expected) {
        final var complex = new DoubleComplex(real, imaginary);

        final var actual = complex.isZero();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleComplex.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        final var complex = new DoubleComplex(1.0D, 2.0D);
        final var summand = new DoubleComplex(3.0D, 4.0D);

        final var actual = complex.add(summand);

        final var expected = new DoubleComplex(4.0D, 6.0D);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtract_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleComplex.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        final var complex = new DoubleComplex(1.0D, 2.0D);
        final var subtrahend = new DoubleComplex(3.0D, 4.0D);

        final var actual = complex.subtract(subtrahend);

        final var expected = new DoubleComplex(-2.0D, -2.0D);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void multiply_should_throw_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleComplex.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        final var complex = new DoubleComplex(1.0D, 2.0D);
        final var multiplier = new DoubleComplex(3.0D, 4.0D);

        final var actual = complex.multiply(multiplier);

        final var expected = new DoubleComplex(-5.0D, 10.0D);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void divide_should_throw_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleComplex.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_divisor_is_not_invertible() {
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
        final var complex = new DoubleComplex(1.0D, 3.0D);
        final var divisor = new DoubleComplex(real, imaginary);

        final var actual = complex.divide(divisor);

        final var expected = new DoubleComplex(expectedReal, expectedImaginary);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void negate_should_succeed() {
        final var complex = new DoubleComplex(1.0D, 2.0D);

        final var actual = complex.negate();

        final var expected = new DoubleComplex(-1.0D, -2.0D);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void pow_should_throw_when_exponent_is_negative_and_is_not_invertible() {
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
        final var complex = new DoubleComplex(real, imaginary);

        final var actual = complex.pow(exponent);

        assertThat(actual.real()).isCloseTo(expectedReal, OFFSET);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @Test
    void reciprocal_should_throw_when_this_not_invertible() {
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
        final var complex = new DoubleComplex(real, imaginary);

        final var actual = complex.reciprocal();

        assertThat(actual.real()).isCloseTo(expectedReal, OFFSET);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @Test
    void conjugate_should_succeed() {
        final var complex = new DoubleComplex(1.0D, 2.0D);

        final var actual = complex.conjugate();

        final var expected = new DoubleComplex(1.0D, -2.0D);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void norm_should_succeed() {
        final var complex = new DoubleComplex(1.0D, 2.0D);

        final var actual = complex.norm();

        assertThat(actual).isCloseTo(5.0D, OFFSET);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 0, 2
        0, 3, 3
        """)
    void abs_should_succeed(final double real, final double imaginary, final double expected) {
        final var complex = new DoubleComplex(real, imaginary);

        final var actual = complex.abs();

        assertThat(actual).isCloseTo(expected, OFFSET);
    }

    @Test
    void arg_should_succeed() {
        final var actual = DoubleComplex.ONE.arg();

        assertThat(actual).isCloseTo(0.0D, OFFSET);
    }
}
