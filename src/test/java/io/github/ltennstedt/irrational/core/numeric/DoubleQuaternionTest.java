package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class DoubleQuaternionTest {
    private final DoubleQuaternion quaternion1 = new DoubleQuaternion(1D, 2D, 3D, 4D);
    private final DoubleQuaternion quaternion2 = new DoubleQuaternion(5D, 6D, 7D, 8D);

    @ParameterizedTest
    @CsvSource(textBlock = """
         NaN,       0,         0,         0,        w,  NaN
        -Infinity,  0,         0,         0,        w, -Infinity
         Infinity,  0,         0,         0,        w,  Infinity
         0,         NaN,       0,         0,        x,  NaN
         0,        -Infinity,  0,         0,        x, -Infinity
         0,         Infinity,  0,         0,        x,  Infinity
         0,         0,         NaN,       0,        y,  NaN
         0,         0,        -Infinity,  0,        y, -Infinity
         0,         0,         Infinity,  0,        y,  Infinity
         0,         0,         0,         NaN,      z,  NaN
         0,         0,         0,        -Infinity, z, -Infinity
         0,         0,         0,         Infinity, z,  Infinity
        """)
    void new_should_throw(
            final double w, final double x, final double y, final double z, final String name, final String part) {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new DoubleQuaternion(w, x, y, z))
                .withMessageContainingAll(name, part)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, 0, 0, false
        1, 0, 0, 0, true
        """)
    void isInvertible_should_succeed(
            final double w, final double x, final double y, final double z, final boolean expected) {
        assertThat(new DoubleQuaternion(w, x, y, z).isInvertible()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1e-12, 1e-13, 1e-13, 1e-13, false
        1e-13, 1e-12, 1e-13, 1e-13, false
        1e-13, 1e-13, 1e-12, 1e-13, false
        1e-13, 1e-13, 1e-13, 1e-12, false
        1e-13, 1e-13, 1e-13, 1e-13, true
        """)
    void isZero_should_succeed(final double w, final double x, final double y, final double z, final boolean expected) {
        assertThat(new DoubleQuaternion(w, x, y, z).isZero()).isEqualTo(expected);
    }

    @Test
    void add_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleQuaternion.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(quaternion1.add(quaternion2)).isEqualTo(new DoubleQuaternion(6D, 8D, 10D, 12D));
    }

    @Test
    void subtract_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleQuaternion.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(quaternion1.subtract(quaternion2)).isEqualTo(new DoubleQuaternion(-4D, -4D, -4D, -4D));
    }

    @Test
    void multiply_should_throw_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleQuaternion.ZERO.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        assertThat(quaternion1.multiply(quaternion2)).isEqualTo(new DoubleQuaternion(-60D, 12D, 30D, 24D));
    }

    @Test
    void divide_should_throw_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoubleQuaternion.ZERO.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> DoubleQuaternion.ONE.divide(DoubleQuaternion.ZERO))
                .withMessage("divisor must be invertible but was DoubleQuaternion[w=0.0, x=0.0, y=0.0, z=0.0]")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        assertThat(quaternion1.divide(quaternion2))
                .isEqualTo(new DoubleQuaternion(0.4022988505747126D, 0.04597701149425287D, 0D, 0.09195402298850573D));
    }

    @Test
    void negate_should_succeed() {
        assertThat(quaternion1.negate()).isEqualTo(new DoubleQuaternion(-1D, -2D, -3D, -4D));
    }

    @Test
    void pow_should_throw_when_exponent_is_negative_and_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> DoubleQuaternion.ZERO.pow(-1))
                .withMessage("this must be invertible but was DoubleQuaternion[w=0.0, x=0.0, y=0.0, z=0.0]")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1,     1,     1,     1,     -1,  0.25, -0.25, -0.25, -0.25
        1,     1,     1,     1,      0,  1,     0,     0,     0
        1,     1,     1,     1,      1,  1,     1,     1,     1
        1e-13, 1e-13, 1e-13, 1e-13,  2,  0,     0,     0,     0
        1,     1,     1,     1,      2, -2,     2,     2,     2
        """)
    void pow_should_succeed(
            final double w,
            final double x,
            final double y,
            final double z,
            final int exponent,
            final double expectedW,
            final double expectedX,
            final double expectedY,
            final double expectedZ) {
        assertThat(new DoubleQuaternion(w, x, y, z).pow(exponent))
                .isEqualTo(new DoubleQuaternion(expectedW, expectedX, expectedY, expectedZ));
    }

    @Test
    void reciprocal_should_throw_when_this_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(DoubleQuaternion.ZERO::reciprocal)
                .withMessage("this must be invertible but was DoubleQuaternion[w=0.0, x=0.0, y=0.0, z=0.0]")
                .withNoCause();
    }

    @Test
    void reciprocal_should_succeed() {
        assertThat(quaternion1.reciprocal())
                .isEqualTo(new DoubleQuaternion(
                        0.03333333333333333D, -0.06666666666666667D, -0.1D, -0.13333333333333333D));
    }

    @Test
    void conjugate_should_succeed() {
        assertThat(quaternion1.conjugate()).isEqualTo(new DoubleQuaternion(1D, -2D, -3D, -4D));
    }

    @Test
    void norm_should_succeed() {
        assertThat(quaternion1.norm()).isEqualByComparingTo(30D);
    }

    @Test
    void abs_should_succeed() {
        assertThat(quaternion1.abs()).isEqualByComparingTo(5.477225575051661D);
    }
}
