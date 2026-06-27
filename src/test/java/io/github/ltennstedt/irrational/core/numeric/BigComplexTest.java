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
    private final Offset<BigDecimal> withinEpsilon = within(new BigDecimal("1e-6"));
    private final BigComplex complex1 = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(2L));
    private final BigComplex complex2 = new BigComplex(BigDecimal.valueOf(3L), BigDecimal.valueOf(4L));

    @ParameterizedTest
    @CsvSource(textBlock = """
         , 1, real
        1,  , imaginary
        """)
    void new_should_throw_exception_when_any_argument_is_null(
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
        assertThat(new BigComplex(real, imaginary)).isEqualTo(new BigComplex(expectedReal, expectedImaginary));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         , 1, precision=0 roundingMode=HALF_EVEN, radius
        1,  , precision=0 roundingMode=HALF_EVEN, angle
        1, 1,                                   , mathContext
        """)
    void ofPolar_should_throw_exception_when_any_argument_is_null(
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

        assertThat(actual.real()).isCloseTo(expectedReal, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, false
        1, 1, true
        """)
    void isInvertible_should_succeed(final BigDecimal real, final BigDecimal imaginary, final boolean expected) {
        assertThat(new BigComplex(real, imaginary).isInvertible()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, true
        0, 1, false
        1, 0, false
        """)
    void isZero_should_succeed(final BigDecimal real, final BigDecimal imaginary, final boolean expected) {
        assertThat(new BigComplex(real, imaginary).isZero()).isEqualTo(expected);
    }

    @Test
    void add_without_MathContext_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_without_MathContext_should_succeed() {
        assertThat(complex1.add(complex2)).isEqualTo(new BigComplex(BigDecimal.valueOf(4L), BigDecimal.valueOf(6L)));
    }

    @Test
    void add_with_MathContext_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.add(null, MathContext.DECIMAL32))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.add(BigComplex.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void add_with_MathContext_should_succeed() {
        assertThat(complex1.add(complex2, MathContext.DECIMAL32))
                .isEqualTo(new BigComplex(BigDecimal.valueOf(4L), BigDecimal.valueOf(6L)));
    }

    @Test
    void subtract_without_MathContext_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_without_MathContext_should_succeed() {
        assertThat(complex1.subtract(complex2))
                .isEqualTo(new BigComplex(BigDecimal.valueOf(-2L), BigDecimal.valueOf(-2L)));
    }

    @Test
    void subtract_with_MathContext_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.subtract(null, MathContext.DECIMAL32))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.subtract(BigComplex.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void subtract_with_MathContext_should_succeed() {
        assertThat(complex1.subtract(complex2, MathContext.DECIMAL32))
                .isEqualTo(new BigComplex(BigDecimal.valueOf(-2L), BigDecimal.valueOf(-2L)));
    }

    @Test
    void multiply_without_MathContext_should_throw_exception_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_without_MathContext_should_succeed() {
        assertThat(complex1.multiply(complex2)).isEqualTo(new BigComplex(BigDecimal.valueOf(-5L), BigDecimal.TEN));
    }

    @Test
    void multiply_with_MathContext_should_throw_exception_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.multiply(null, MathContext.DECIMAL32))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.multiply(BigComplex.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void multiply_with_MathContext_should_succeed() {
        assertThat(complex1.multiply(complex2, MathContext.DECIMAL32))
                .isEqualTo(new BigComplex(BigDecimal.valueOf(-5L), BigDecimal.TEN));
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.divide(null, MathContext.DECIMAL32))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.divide(BigComplex.ONE, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_not_invertible() {
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
        final var actual = new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(3L))
                .divide(new BigComplex(real, imaginary), MathContext.DECIMAL32);

        assertThat(actual.real()).isEqualByComparingTo(expectedReal);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void negate_without_MathContext_should_succeed() {
        assertThat(complex1.negate()).isEqualTo(new BigComplex(BigDecimal.valueOf(-1L), BigDecimal.valueOf(-2L)));
    }

    @Test
    void negate_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.negate(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void negate_with_MathContext_should_succeed() {
        assertThat(complex1.negate(MathContext.DECIMAL32))
                .isEqualTo(new BigComplex(BigDecimal.valueOf(-1L), BigDecimal.valueOf(-2L)));
    }

    @Test
    void pow_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.pow(0, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void pow_should_throw_exception_when_exponent_is_negative_and_is_not_invertible() {
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
        final var actual = new BigComplex(real, imaginary).pow(exponent, MathContext.DECIMAL32);

        assertThat(actual.real()).isCloseTo(expectedReal, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void reciprocal_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ZERO.reciprocal(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void reciprocal_should_throw_exception_when_this_not_invertible() {
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
        final var actual = new BigComplex(real, imaginary).reciprocal(MathContext.DECIMAL32);

        assertThat(actual.real()).isCloseTo(expectedReal, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void conjugate_without_MathContext_should_succeed() {
        assertThat(complex1.conjugate()).isEqualTo(new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(-2L)));
    }

    @Test
    void conjugate_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ONE.conjugate(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void conjugate_with_MathContext_should_succeed() {
        assertThat(complex1.conjugate(MathContext.DECIMAL32))
                .isEqualTo(new BigComplex(BigDecimal.ONE, BigDecimal.valueOf(-2L)));
    }

    @Test
    void norm_without_MathContext_should_succeed() {
        assertThat(complex1.norm()).isCloseTo(BigDecimal.valueOf(5L), withinEpsilon);
    }

    @Test
    void norm_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ONE.norm(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void norm_with_MathContext_should_succeed() {
        assertThat(complex1.norm(MathContext.DECIMAL32)).isCloseTo(BigDecimal.valueOf(5L), withinEpsilon);
    }

    @Test
    void abs_should_throw_Exception_when_mathContext_is_null() {
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
        assertThat(new BigComplex(real, imaginary).abs(MathContext.DECIMAL32)).isCloseTo(expected, withinEpsilon);
    }

    @Test
    void arg_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigComplex.ONE.arg(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void arg_should_succeed() {
        assertThat(BigComplex.ONE.arg(MathContext.DECIMAL32)).isCloseTo(BigDecimal.ZERO, withinEpsilon);
    }
}
