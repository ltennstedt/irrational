package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;

import io.github.ltennstedt.irrational.core.util.Constants;
import io.github.ltennstedt.irrational.core.util.PiCalculator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class BigGaussianTest {
    private final Offset<BigDecimal> withinEpsilon = within(new BigDecimal("1e-6"));
    private final BigGaussian gaussian1 = new BigGaussian(BigInteger.ONE, BigInteger.valueOf(2L));
    private final BigGaussian gaussian2 = new BigGaussian(BigInteger.valueOf(3L), BigInteger.valueOf(4L));

    @ParameterizedTest
    @CsvSource(textBlock = """
         , 1, real
        1,  , imaginary
        """)
    void new_should_throw_exception_when_any_argument_is_null(
            final BigInteger real, final BigInteger imaginary, final String message) {
        assertThatNullPointerException()
                .isThrownBy(() -> new BigGaussian(real, imaginary))
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
            final BigInteger real,
            final BigInteger imaginary,
            final BigInteger expectedReal,
            final BigInteger expectedImaginary) {
        assertThat(new BigGaussian(real, imaginary)).isEqualTo(new BigGaussian(expectedReal, expectedImaginary));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, false
        1, 1, true
        """)
    void isInvertible_should_succeed(final BigInteger real, final BigInteger imaginary, final boolean expected) {
        assertThat(new BigGaussian(real, imaginary).isInvertible()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, true
        0, 1, false
        1, 0, false
        """)
    void isZero_should_succeed(final BigInteger real, final BigInteger imaginary, final boolean expected) {
        assertThat(new BigGaussian(real, imaginary).isZero()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         0,  0, false
         1,  0, true
         0,  1, true
        -1,  0, true
         0, -1, true
        """)
    void isUnit_should_succeed(final BigInteger real, final BigInteger imaginary, final boolean expected) {
        assertThat(new BigGaussian(real, imaginary).isUnit()).isEqualTo(expected);
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(gaussian1.add(gaussian2)).isEqualTo(new BigGaussian(BigInteger.valueOf(4L), BigInteger.valueOf(6L)));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(gaussian1.subtract(gaussian2))
                .isEqualTo(new BigGaussian(BigInteger.valueOf(-2L), BigInteger.valueOf(-2L)));
    }

    @Test
    void multiply_should_throw_exception_when_factor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        assertThat(gaussian1.multiply(gaussian2)).isEqualTo(new BigGaussian(BigInteger.valueOf(-5L), BigInteger.TEN));
    }

    @Test
    void divide_without_MathContext_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_without_MathContext_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigGaussian.ONE.divide(BigGaussian.ZERO))
                .withMessage("divisor must be invertible but was BigComplex[real=0, imaginary=0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 3, 1, 0
        2, 1, 1, 1
        """)
    void divide_without_MathContext_should_succeed(
            final BigInteger real,
            final BigInteger imaginary,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var actual =
                new BigGaussian(BigInteger.ONE, BigInteger.valueOf(3L)).divide(new BigGaussian(real, imaginary));
        assertThat(actual.real()).isEqualByComparingTo(expectedReal);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void divide_with_MathContext_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ZERO.divide(null, MathContext.DECIMAL32))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ZERO.divide(BigGaussian.ONE, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void divide_with_MathContext_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigGaussian.ONE.divide(BigGaussian.ZERO, MathContext.DECIMAL32))
                .withMessage("divisor must be invertible but was BigComplex[real=0, imaginary=0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 3, 1, 0
        2, 1, 1, 1
        """)
    void divide_with_MathContext_should_succeed(
            final BigInteger real,
            final BigInteger imaginary,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var actual = new BigGaussian(BigInteger.ONE, BigInteger.valueOf(3L))
                .divide(new BigGaussian(real, imaginary), MathContext.DECIMAL32);

        assertThat(actual.real()).isEqualByComparingTo(expectedReal);
        assertThat(actual.imaginary()).isCloseTo(expectedImaginary, withinEpsilon);
    }

    @Test
    void pow_without_MathContext_should_throw_exception_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigGaussian.ZERO.pow(-1))
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
    void pow_without_MathContext_should_succeed(
            final BigInteger real,
            final BigInteger imaginary,
            final int exponent,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var actual = new BigGaussian(real, imaginary).pow(exponent);

        assertThat(actual.real()).isEqualByComparingTo(expectedReal);
        assertThat(actual.imaginary()).isEqualByComparingTo(expectedImaginary);
    }

    @Test
    void pow_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ZERO.pow(0, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void pow_with_MathContext_should_throw_exception_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigGaussian.ZERO.pow(-1, MathContext.DECIMAL32))
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
    void pow_with_MathContext_should_succeed(
            final BigInteger real,
            final BigInteger imaginary,
            final int exponent,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var actual = new BigGaussian(real, imaginary).pow(exponent, MathContext.DECIMAL32);

        assertThat(actual.real()).isEqualByComparingTo(expectedReal);
        assertThat(actual.imaginary()).isEqualByComparingTo(expectedImaginary);
    }

    @Test
    void reciprocal_without_MathContext_should_throw_exception_when_this_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(BigGaussian.ZERO::reciprocal)
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
    void reciprocal_without_MathContext_should_succeed(
            final BigInteger real,
            final BigInteger imaginary,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var actual = new BigGaussian(real, imaginary).reciprocal();

        assertThat(actual.real()).isEqualByComparingTo(expectedReal);
        assertThat(actual.imaginary()).isEqualByComparingTo(expectedImaginary);
    }

    @Test
    void reciprocal_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ZERO.reciprocal(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void reciprocal_with_MathContext_should_throw_exception_when_this_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigGaussian.ZERO.reciprocal(MathContext.DECIMAL32))
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
            final BigInteger real,
            final BigInteger imaginary,
            final BigDecimal expectedReal,
            final BigDecimal expectedImaginary) {
        final var actual = new BigGaussian(real, imaginary).reciprocal(MathContext.DECIMAL32);

        assertThat(actual.real()).isEqualByComparingTo(expectedReal);
        assertThat(actual.imaginary()).isEqualByComparingTo(expectedImaginary);
    }

    @Test
    void negate_should_succeed() {
        assertThat(gaussian1.negate()).isEqualTo(new BigGaussian(BigInteger.valueOf(-1L), BigInteger.valueOf(-2L)));
    }

    @Test
    void conjugate_should_succeed() {
        assertThat(gaussian1.conjugate()).isEqualTo(new BigGaussian(BigInteger.ONE, BigInteger.valueOf(-2L)));
    }

    @Test
    void norm_should_succeed() {
        assertThat(gaussian1.norm()).isEqualTo(BigInteger.valueOf(5L));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 0, 2
        0, 3, 3
        """)
    void abs_without_MathContext_should_succeed(
            final BigInteger real, final BigInteger imaginary, final BigDecimal expected) {
        assertThat(new BigGaussian(real, imaginary).abs()).isEqualByComparingTo(expected);
    }

    @Test
    void abs_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ONE.abs(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 0, 2
        0, 3, 3
        """)
    void abs_with_MathContext_should_succeed(
            final BigInteger real, final BigInteger imaginary, final BigDecimal expected) {
        assertThat(new BigGaussian(real, imaginary).abs(MathContext.DECIMAL32)).isEqualByComparingTo(expected);
    }

    @Test
    void arg_without_MathContext_should_succeed() {
        assertThat(BigGaussian.ONE.arg()).isZero();
    }

    @Test
    void arg_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigGaussian.ONE.arg(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void arg_with_MathContext_should_succeed() {
        assertThat(BigGaussian.ONE.arg(MathContext.DECIMAL32)).isZero();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         1,  0, 1, 0
         0,  1, 1, 0.5
        -1,  0, 1, 1
         0, -1, 1, 1.5
        """)
    void toPolar_without_MathContext_should_succeed(
            final BigInteger real,
            final BigInteger imaginary,
            final BigDecimal expectedRadius,
            final BigDecimal expectedFactor) {
        final var polar = new BigGaussian(real, imaginary).toPolar();

        assertThat(polar.radius()).isEqualByComparingTo(expectedRadius);
        assertThat(polar.angle()).isCloseTo(expectedFactor.multiply(Constants.BIG_PI), withinEpsilon);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         1,  0, 1, 0
         0,  1, 1, 0.5
        -1,  0, 1, 1
         0, -1, 1, 1.5
        """)
    void toPolar_with_MathContext_should_succeed(
            final BigInteger real,
            final BigInteger imaginary,
            final BigDecimal expectedRadius,
            final BigDecimal expectedFactor) {
        final var polar = new BigGaussian(real, imaginary).toPolar(MathContext.DECIMAL32);

        assertThat(polar.radius()).isEqualByComparingTo(expectedRadius);
        assertThat(polar.angle())
                .isCloseTo(expectedFactor.multiply(PiCalculator.pi(MathContext.DECIMAL32)), withinEpsilon);
    }
}
