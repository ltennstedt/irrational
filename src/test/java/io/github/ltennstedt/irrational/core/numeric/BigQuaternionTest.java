package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import java.math.BigDecimal;
import java.math.MathContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class BigQuaternionTest {
    private final BigQuaternion quaternion1 =
            new BigQuaternion(BigDecimal.ONE, BigDecimal.valueOf(2L), BigDecimal.valueOf(3L), BigDecimal.valueOf(4L));
    private final BigQuaternion quaternion2 = new BigQuaternion(
            BigDecimal.valueOf(5L), BigDecimal.valueOf(6L), BigDecimal.valueOf(7L), BigDecimal.valueOf(8L));

    @ParameterizedTest
    @CsvSource(textBlock = """
         , 0, 0, 0, w
        0,  , 0, 0, x
        0, 0,  , 0, y
        0, 0, 0,  , z
        """)
    void new_should_throw(
            final BigDecimal w, final BigDecimal x, final BigDecimal y, final BigDecimal z, final String message) {
        assertThatNullPointerException()
                .isThrownBy(() -> new BigQuaternion(w, x, y, z))
                .withMessage(message)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, 0, 0, false
        1, 0, 0, 0, true
        """)
    void isInvertible_should_succeed(
            final BigDecimal w, final BigDecimal x, final BigDecimal y, final BigDecimal z, final boolean expected) {
        assertThat(new BigQuaternion(w, x, y, z).isInvertible()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 0, 0, 0, false
        0, 1, 0, 0, false
        0, 0, 1, 0, false
        0, 0, 0, 1, false
        0, 0, 0, 0, true
        """)
    void isZero_should_succeed(
            final BigDecimal w, final BigDecimal x, final BigDecimal y, final BigDecimal z, final boolean expected) {
        assertThat(new BigQuaternion(w, x, y, z).isZero()).isEqualTo(expected);
    }

    @Test
    void add_without_MathContext_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_without_MathContext_should_succeed() {
        assertThat(quaternion1.add(quaternion2))
                .isEqualTo(new BigQuaternion(
                        BigDecimal.valueOf(6L), BigDecimal.valueOf(8L), BigDecimal.TEN, BigDecimal.valueOf(12L)));
    }

    @Test
    void add_with_MathContext_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.add(null, MathContext.DECIMAL32))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.add(BigQuaternion.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void add_with_MathContext_should_succeed() {
        assertThat(quaternion1.add(quaternion2, MathContext.DECIMAL32))
                .isEqualTo(new BigQuaternion(
                        BigDecimal.valueOf(6L), BigDecimal.valueOf(8L), BigDecimal.TEN, BigDecimal.valueOf(12L)));
    }

    @Test
    void subtract_without_MathContext_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_without_MathContext_should_succeed() {
        assertThat(quaternion1.subtract(quaternion2))
                .isEqualTo(new BigQuaternion(
                        BigDecimal.valueOf(-4L),
                        BigDecimal.valueOf(-4L),
                        BigDecimal.valueOf(-4L),
                        BigDecimal.valueOf(-4L)));
    }

    @Test
    void subtract_with_MathContext_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.subtract(null, MathContext.DECIMAL32))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.subtract(BigQuaternion.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void subtract_with_MathContext_should_succeed() {
        assertThat(quaternion1.subtract(quaternion2, MathContext.DECIMAL32))
                .isEqualTo(new BigQuaternion(
                        BigDecimal.valueOf(-4L),
                        BigDecimal.valueOf(-4L),
                        BigDecimal.valueOf(-4L),
                        BigDecimal.valueOf(-4L)));
    }

    @Test
    void multiply_without_MathContext_should_throw_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_without_MathContext_should_succeed() {
        assertThat(quaternion1.multiply(quaternion2))
                .isEqualTo(new BigQuaternion(
                        BigDecimal.valueOf(-60L),
                        BigDecimal.valueOf(12L),
                        BigDecimal.valueOf(30L),
                        BigDecimal.valueOf(24L)));
    }

    @Test
    void multiply_with_MathContext_should_throw_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.multiply(null, MathContext.DECIMAL32))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.multiply(BigQuaternion.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void multiply_with_MathContext_should_succeed() {
        assertThat(quaternion1.multiply(quaternion2, MathContext.DECIMAL32))
                .isEqualTo(new BigQuaternion(
                        BigDecimal.valueOf(-60L),
                        BigDecimal.valueOf(12L),
                        BigDecimal.valueOf(30L),
                        BigDecimal.valueOf(24L)));
    }

    @Test
    void divide_should_throw_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.divide(null, MathContext.DECIMAL32))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ONE.divide(BigQuaternion.ZERO, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigQuaternion.ONE.divide(BigQuaternion.ZERO, MathContext.DECIMAL32))
                .withMessage("divisor must be invertible but was BigQuaternion[w=0, x=0, y=0, z=0]")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        assertThat(quaternion1.divide(quaternion2, MathContext.DECIMAL32))
                .isEqualTo(new BigQuaternion(
                        new BigDecimal("0.4022988"),
                        new BigDecimal("0.0459771"),
                        new BigDecimal("0E-7"),
                        new BigDecimal("0.0919540")));
    }

    @Test
    void negate_without_MathContext_should_succeed() {
        assertThat(quaternion1.negate())
                .isEqualTo(new BigQuaternion(
                        BigDecimal.valueOf(-1L),
                        BigDecimal.valueOf(-2L),
                        BigDecimal.valueOf(-3L),
                        BigDecimal.valueOf(-4L)));
    }

    @Test
    void negate_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.negate(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void negate_with_MathContext_should_succeed() {
        assertThat(quaternion1.negate(MathContext.DECIMAL32))
                .isEqualTo(new BigQuaternion(
                        BigDecimal.valueOf(-1L),
                        BigDecimal.valueOf(-2L),
                        BigDecimal.valueOf(-3L),
                        BigDecimal.valueOf(-4L)));
    }

    @Test
    void pow_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.pow(0, null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void pow_should_throw_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigQuaternion.ZERO.pow(-1, MathContext.DECIMAL32))
                .withMessage("this must be invertible but was BigQuaternion[w=0, x=0, y=0, z=0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1,     1,     1,     1,     -1,  0.25, -0.25, -0.25, -0.25
        1,     1,     1,     1,      0,  1,     0,     0,     0
        1,     1,     1,     1,      1,  1,     1,     1,     1
        0, 0, 0, 0,  2,  0,     0,     0,     0
        1,     1,     1,     1,      2, -2,     2,     2,     2
        """)
    void pow_should_succeed(
            final BigDecimal w,
            final BigDecimal x,
            final BigDecimal y,
            final BigDecimal z,
            final int exponent,
            final BigDecimal expectedW,
            final BigDecimal expectedX,
            final BigDecimal expectedY,
            final BigDecimal expectedZ) {
        assertThat(new BigQuaternion(w, x, y, z).pow(exponent, MathContext.DECIMAL32))
                .isEqualTo(new BigQuaternion(expectedW, expectedX, expectedY, expectedZ));
    }

    @Test
    void reciprocal_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.reciprocal(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void reciprocal_should_throw_when_this_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> BigQuaternion.ZERO.reciprocal(MathContext.DECIMAL32))
                .withMessage("this must be invertible but was BigQuaternion[w=0, x=0, y=0, z=0]")
                .withNoCause();
    }

    @Test
    void reciprocal_should_succeed() {
        assertThat(quaternion1.reciprocal(MathContext.DECIMAL32))
                .isEqualTo(new BigQuaternion(
                        new BigDecimal("0.03333333"),
                        new BigDecimal("-0.06666667"),
                        new BigDecimal("-0.1"),
                        new BigDecimal("-0.1333333")));
    }

    @Test
    void conjugate_without_MathContext_should_succeed() {
        assertThat(quaternion1.conjugate())
                .isEqualTo(new BigQuaternion(
                        BigDecimal.ONE, BigDecimal.valueOf(-2L), BigDecimal.valueOf(-3L), BigDecimal.valueOf(-4L)));
    }

    @Test
    void conjugate_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.conjugate(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void conjugate_with_MathContext_should_succeed() {
        assertThat(quaternion1.conjugate(MathContext.DECIMAL32))
                .isEqualTo(new BigQuaternion(
                        BigDecimal.ONE, BigDecimal.valueOf(-2L), BigDecimal.valueOf(-3L), BigDecimal.valueOf(-4L)));
    }

    @Test
    void norm_without_MathContext_should_succeed() {
        assertThat(quaternion1.norm()).isEqualByComparingTo(BigDecimal.valueOf(30L));
    }

    @Test
    void norm_with_MathContext_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.norm(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void norm_with_MathContext_should_succeed() {
        assertThat(quaternion1.norm(MathContext.DECIMAL32)).isEqualByComparingTo(BigDecimal.valueOf(30L));
    }

    @Test
    void abs_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigQuaternion.ZERO.abs(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void abs_should_succeed() {
        assertThat(quaternion1.abs(MathContext.DECIMAL32)).isEqualByComparingTo(new BigDecimal("5.477226"));
    }
}
