package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import io.github.ltennstedt.irrational.core.util.Constants;
import java.math.BigDecimal;
import java.math.MathContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class BigPolarTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
        -5, 1.570796327, 5, 4.712388980589793
         5, 1.570796327, 5, 1.570796327
        """)
    void new_should_succeed(
            final BigDecimal radial,
            final BigDecimal angular,
            final BigDecimal expectedRadial,
            final BigDecimal expectedAngular) {
        assertThat(new BigPolar(radial, angular)).isEqualTo(new BigPolar(expectedRadial, expectedAngular));
    }

    @Test
    void ofComplex_without_MathContext_should_throw_exception_when_complex_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ofComplex(null))
                .withMessage("complex")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        3, 0, 3, 0
        0, 4, 4, 1.570796326794896
        """)
    void ofComplex_without_MathContext_should_succeed(
            final BigDecimal real,
            final BigDecimal imaginary,
            final BigDecimal expectedRadial,
            final BigDecimal expectedAngular) {
        assertThat(BigPolar.ofComplex(new BigComplex(real, imaginary)))
                .isEqualTo(new BigPolar(expectedRadial, expectedAngular));
    }

    @Test
    void ofComplex_with_MathContext_should_throw_exception_when_complex_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ofComplex(null, MathContext.DECIMAL32))
                .withMessage("complex")
                .withNoCause();
    }

    @Test
    void ofComplex_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ofComplex(BigComplex.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        3, 0, 3, 0
        0, 4, 4, 1.570796
        """)
    void ofComplex_with_MathContext_should_succeed(
            final BigDecimal real,
            final BigDecimal imaginary,
            final BigDecimal expectedRadial,
            final BigDecimal expectedAngular) {
        assertThat(BigPolar.ofComplex(new BigComplex(real, imaginary), MathContext.DECIMAL32))
                .isEqualTo(new BigPolar(expectedRadial, expectedAngular));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 0, false
        0, 1, true
        """)
    void isZero_should_succeed(final BigDecimal radial, final BigDecimal angular, final boolean expected) {
        assertThat(new BigPolar(radial, angular).isZero()).isEqualTo(expected);
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ZERO.add(null, MathContext.DECIMAL32))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ZERO.add(BigPolar.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(new BigPolar(BigDecimal.valueOf(3L), BigDecimal.ZERO)
                        .add(new BigPolar(BigDecimal.valueOf(4L), Constants.HALF_BIG_PI)))
                .isEqualTo(new BigPolar(new BigDecimal("5.000000000000000"), new BigDecimal("0.9272952180015818")));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ZERO.subtract(null, MathContext.DECIMAL32))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ZERO.subtract(BigPolar.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(new BigPolar(BigDecimal.valueOf(5L), BigDecimal.ZERO)
                        .subtract(new BigPolar(BigDecimal.valueOf(3L), Constants.HALF_BIG_PI)))
                .isEqualTo(new BigPolar(new BigDecimal("5.830951894845300"), new BigDecimal("5.7427658069090018")));
    }

    @Test
    void multiply_without_MathContext_should_throw_exception_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L)).multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_without_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L))
                .multiply(new BigPolar(BigDecimal.valueOf(5L), BigDecimal.ONE));

        assertThat(actual.radius()).isEqualByComparingTo(BigDecimal.valueOf(50L));
        assertThat(actual.angle()).isEqualByComparingTo(BigDecimal.valueOf(5L));
    }

    @Test
    void multiply_with_MathContext_should_throw_exception_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() ->
                        new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L)).multiply(null, MathContext.DECIMAL32))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L)).multiply(BigPolar.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void multiply_with_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L))
                .multiply(new BigPolar(BigDecimal.valueOf(5L), BigDecimal.ONE), MathContext.DECIMAL32);

        assertThat(actual.radius()).isEqualByComparingTo(BigDecimal.valueOf(50L));
        assertThat(actual.angle()).isEqualByComparingTo(BigDecimal.valueOf(5L));
    }

    @Test
    void divide_without_MathContext_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L)).divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_without_MathContext_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L)).divide(BigPolar.ZERO))
                .withMessage("divisor must be invertible but was BigPolar[radius=0, angle=0E-15]")
                .withNoCause();
    }

    @Test
    void divide_without_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L))
                .divide(new BigPolar(BigDecimal.valueOf(5L), BigDecimal.ONE));

        assertThat(actual.radius()).isEqualByComparingTo(BigDecimal.valueOf(2L));
        assertThat(actual.angle()).isEqualByComparingTo(BigDecimal.valueOf(3L));
    }

    @Test
    void divide_with_MathContext_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(
                        () -> new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L)).divide(null, MathContext.DECIMAL32))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_with_MathContext_should_throw_exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L)).divide(BigPolar.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void divide_with_MathContext_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L))
                        .divide(BigPolar.ZERO, MathContext.DECIMAL32))
                .withMessage("divisor must be invertible but was BigPolar[radius=0, angle=0E-15]")
                .withNoCause();
    }

    @Test
    void divide_with_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.TEN, BigDecimal.valueOf(4L))
                .divide(new BigPolar(BigDecimal.valueOf(5L), BigDecimal.ONE), MathContext.DECIMAL32);

        assertThat(actual.radius()).isEqualByComparingTo(BigDecimal.valueOf(2L));
        assertThat(actual.angle()).isEqualByComparingTo(BigDecimal.valueOf(3L));
    }

    @Test
    void pow_without_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.valueOf(3L), new BigDecimal("0.523598776")).pow(2);

        assertThat(actual.radius()).isEqualByComparingTo(BigDecimal.valueOf(9L));
        assertThat(actual.angle()).isEqualByComparingTo(new BigDecimal("1.047197552"));
    }

    @Test
    void pow_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ZERO.pow(0, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void pow_with_MathContext_should_succeed() {
        final var actual =
                new BigPolar(BigDecimal.valueOf(3L), new BigDecimal("0.523598776")).pow(2, MathContext.DECIMAL32);

        assertThat(actual.radius()).isEqualByComparingTo(BigDecimal.valueOf(9L));
        assertThat(actual.angle()).isEqualByComparingTo(new BigDecimal("1.047198"));
    }

    @Test
    void reciprocal_without_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.TEN, new BigDecimal("0.523598776")).reciprocal();

        assertThat(actual.radius()).isEqualByComparingTo(new BigDecimal("0.1"));
        assertThat(actual.angle()).isEqualByComparingTo(new BigDecimal("5.759586531179586"));
    }

    @Test
    void reciprocal_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ZERO.reciprocal(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void reciprocal_with_MathContext_should_succeed() {
        final var actual =
                new BigPolar(BigDecimal.TEN, new BigDecimal("0.523598776")).reciprocal(MathContext.DECIMAL32);

        assertThat(actual.radius()).isEqualByComparingTo(new BigDecimal("0.1"));
        assertThat(actual.angle()).isEqualByComparingTo(new BigDecimal("5.759586507179586"));
    }

    @Test
    void negate_without_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.ONE, Constants.BIG_PI).negate();

        assertThat(actual.radius()).isOne();
        assertThat(actual.angle()).isZero();
    }

    @Test
    void negate_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ZERO.negate(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void negate_with_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.ONE, Constants.BIG_PI).negate(MathContext.DECIMAL32);

        assertThat(actual.radius()).isOne();
        assertThat(actual.angle()).isZero();
    }

    @Test
    void toComplex_without_MathContext_should_succeed() {
        final var actual = new BigPolar(BigDecimal.valueOf(2L), new BigDecimal("0.523598776")).toComplex();

        assertThat(actual.real()).isEqualByComparingTo(new BigDecimal("1.7320508071671762"));
        assertThat(actual.imaginary()).isEqualByComparingTo(new BigDecimal("1.0000000006957668"));
    }

    @Test
    void toComplex_with_MathContext_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigPolar.ZERO.toComplex(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void toComplex_with_MathContext_should_succeed() {
        final var actual =
                new BigPolar(BigDecimal.valueOf(2L), new BigDecimal("0.523598776")).toComplex(MathContext.DECIMAL32);

        assertThat(actual.real()).isEqualByComparingTo(new BigDecimal("1.732051"));
        assertThat(actual.imaginary()).isOne();
    }
}
