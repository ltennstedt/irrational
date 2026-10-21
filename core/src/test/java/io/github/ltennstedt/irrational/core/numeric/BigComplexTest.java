package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;

import io.github.ltennstedt.irrational.core.util.PiCalculator;
import java.math.BigDecimal;
import java.math.MathContext;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class BigComplexTest {
    private static final Offset<BigDecimal> OFFSET = within(new BigDecimal("1e-5"));

    @ParameterizedTest
    @CsvSource(textBlock = """
         , 1, real
        1,  , imaginary
        """)
    void new_should_throw_when_any_argument_is_null(
            final BigDecimal real, final BigDecimal imaginary, final String message) {
        assertThatNullPointerException()
                .isThrownBy(() -> new BigComplex(real, imaginary))
                .withMessage(message)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        -0, -0, 0, 0
         1,  0, 1, 0
         0,  1, 0, 1
        """)
    void new_should_succeed(
            final BigDecimal real,
            final BigDecimal imaginary,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var actual = new BigComplex(real, imaginary);

        final var expected = new BigComplex(expectedReal, expectedImaginary);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         , 1, precision=0 roundingMode=HALF_EVEN, radius
        1,  , precision=0 roundingMode=HALF_EVEN, angle
        1, 1,                                   , mathContext
        """)
    void ofPolar_should_throw_when_any_argument_is_null(
            final BigDecimal radius, final BigDecimal angle, final MathContext mathContext, final String message) {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ofPolar(radius, angle, mathContext))
                .withMessage(message)
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
            final BigDecimal radius,
            final BigDecimal factor,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var actual = BigComplex.ofPolar(
                radius,
                factor.multiply(PiCalculator.pi(MathContext.DECIMAL32), MathContext.DECIMAL32),
                MathContext.DECIMAL32);

        assertThat(actual.real()).isCloseTo(expectedReal, OFFSET);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, false
        1, 1, true
        """)
    void isInvertible_should_succeed(final BigDecimal real, final BigDecimal imaginary, final boolean expected) {
        final var complex = new BigComplex(real, imaginary);

        final var actual = complex.isInvertible();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, true
        0, 1, false
        1, 0, false
        """)
    void isZero_should_succeed(final BigDecimal real, final BigDecimal imaginary, final boolean expected) {
        final var complex = new BigComplex(real, imaginary);

        final var actual = complex.isZero();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add_without_MathContext_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_without_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));
        final var summand = new BigComplex(BigDecimal.valueOf(3L), BigDecimal.valueOf(4L));

        final var actual = complex.add(summand);

        final var expected = new BigComplex(BigDecimal.valueOf(4L), BigDecimal.valueOf(6L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add_with_MathContext_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.add(null, MathContext.DECIMAL32))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.add(BigComplex.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void add_with_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));
        final var summand = new BigComplex(BigDecimal.valueOf(3L), BigDecimal.valueOf(4L));

        final var actual = complex.add(summand, MathContext.DECIMAL32);

        final var expected = new BigComplex(BigDecimal.valueOf(4L), BigDecimal.valueOf(6L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtract_without_MathContext_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_without_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));
        final var subtrahend = new BigComplex(BigDecimal.valueOf(3L), BigDecimal.valueOf(4L));

        final var actual = complex.subtract(subtrahend);

        final var expected = new BigComplex(BigDecimal.valueOf(-2L), BigDecimal.valueOf(-2L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtract_with_MathContext_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.subtract(null, MathContext.DECIMAL32))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.subtract(BigComplex.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void subtract_with_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));
        final var subtrahend = new BigComplex(BigDecimal.valueOf(3L), BigDecimal.valueOf(4L));

        final var actual = complex.subtract(subtrahend, MathContext.DECIMAL32);

        final var expected = new BigComplex(BigDecimal.valueOf(-2L), BigDecimal.valueOf(-2L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void multiply_without_MathContext_should_throw_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_without_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));
        final var multiplier = new BigComplex(BigDecimal.valueOf(3L), BigDecimal.valueOf(4L));

        final var actual = complex.multiply(multiplier);

        final var expected = new BigComplex(BigDecimal.valueOf(-5L), BigDecimal.TEN);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void multiply_with_MathContext_should_throw_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.multiply(null, MathContext.DECIMAL32))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.multiply(BigComplex.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void multiply_with_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));
        final var multiplier = new BigComplex(BigDecimal.valueOf(3L), BigDecimal.valueOf(4L));

        final var actual = complex.multiply(multiplier, MathContext.DECIMAL32);

        final var expected = new BigComplex(BigDecimal.valueOf(-5L), BigDecimal.TEN);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void divide_should_throw_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.divide(null, MathContext.DECIMAL32))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.divide(BigComplex.ONE, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigComplex.ONE.divide(BigComplex.ZERO, MathContext.DECIMAL32))
                .withMessage("divisor must be invertible but was BigComplex[real=0, imaginary=0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 3, 1, 0
        2, 1, 1, 1
        """)
    void divide_should_succeed(
            final BigDecimal real,
            final BigDecimal imaginary,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(3L));
        final var divisor = new BigComplex(real, imaginary);

        final var actual = complex.divide(divisor, MathContext.DECIMAL32);

        assertThat(actual.real()).isEqualByComparingTo(expectedReal);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @Test
    void negate_without_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));

        final var actual = complex.negate();

        final var expected = new BigComplex(BigDecimal.valueOf(-1L), BigDecimal.valueOf(-2L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void negate_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.negate(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void negate_with_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));

        final var actual = complex.negate(MathContext.DECIMAL32);

        final var expected = new BigComplex(BigDecimal.valueOf(-1L), BigDecimal.valueOf(-2L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void pow_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.pow(0, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void pow_should_throw_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigComplex.ZERO.pow(-1, MathContext.DECIMAL32))
                .withMessage("this must be invertible but was BigComplex[real=0, imaginary=0]")
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
            final BigDecimal real,
            final BigDecimal imaginary,
            final int exponent,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var complex = new BigComplex(real, imaginary);

        final var actual = complex.pow(exponent, MathContext.DECIMAL32);

        assertThat(actual.real()).isCloseTo(expectedReal, OFFSET);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @Test
    void reciprocal_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.reciprocal(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void reciprocal_should_throw_when_this_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigComplex.ZERO.reciprocal(MathContext.DECIMAL32))
                .withMessage("this must be invertible but was BigComplex[real=0, imaginary=0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         1,  0,  1,  0
         0,  1,  0, -1
        -1,  0, -1,  0
         0, -1,  0,  1
        """)
    void reciprocal_with_MathContext_should_succeed(
            final BigDecimal real,
            final BigDecimal imaginary,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var complex = new BigComplex(real, imaginary);

        final var actual = complex.reciprocal(MathContext.DECIMAL32);

        assertThat(actual.real()).isCloseTo(expectedReal, OFFSET);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, OFFSET);
    }

    @Test
    void conjugate_without_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));

        final var actual = complex.conjugate();

        final var expected = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(-2L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void conjugate_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ONE.conjugate(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void conjugate_with_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));

        final var actual = complex.conjugate(MathContext.DECIMAL32);

        final var expected = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(-2L));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void norm_without_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));

        final var actual = complex.norm();

        assertThat(actual).isCloseTo(BigDecimal.valueOf(5L), OFFSET);
    }

    @Test
    void norm_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ONE.norm(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void norm_with_MathContext_should_succeed() {
        final var complex = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));

        final var actual = complex.norm(MathContext.DECIMAL32);

        assertThat(actual).isCloseTo(BigDecimal.valueOf(5L), OFFSET);
    }

    @Test
    void abs_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ONE.abs(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 0, 2
        0, 3, 3
        """)
    void abs_should_succeed(final BigDecimal real, final BigDecimal imaginary, final BigDecimal expected) {
        final var complex = new BigComplex(real, imaginary);

        final var actual = complex.abs(MathContext.DECIMAL32);

        assertThat(actual).isCloseTo(expected, OFFSET);
    }

    @Test
    void arg_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ONE.arg(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void arg_should_succeed() {
        final var actual = BigComplex.ONE.arg(MathContext.DECIMAL32);

        assertThat(actual).isCloseTo(BigDecimal.ZERO, OFFSET);
    }
}
