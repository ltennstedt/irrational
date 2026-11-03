package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class DoubleQuaternionTest {
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
                .withMessage(name + " must be a finite number but was " + part)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 0, 0, 0, false
        1, 0, 0, 0, true
        """)
    void isInvertible_should_succeed(
            final double w, final double x, final double y, final double z, final boolean expected) {
        final var quaternion = new DoubleQuaternion(w, x, y, z);

        final var actual = quaternion.isInvertible();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 0, 0, 0, false
        0, 1, 0, 0, false
        0, 0, 1, 0, false
        0, 0, 0, 1, false
        0, 0, 0, 0, true
        """)
    void isZero_should_succeed(final double w, final double x, final double y, final double z, final boolean expected) {
        final var quaternion = new DoubleQuaternion(w, x, y, z);

        final var actual = quaternion.isZero();

        assertThat(actual).isEqualTo(expected);
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
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);
        final var summand = new DoubleQuaternion(5.0D, 6.0D, 7.0D, 8.0D);

        final var actual = quaternion.add(summand);

        final var expected = new DoubleQuaternion(6.0D, 8.0D, 10.0D, 12.0D);
        assertThat(actual).isEqualTo(expected);
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
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);
        final var subtrahend = new DoubleQuaternion(5.0D, 6.0D, 7.0D, 8.0D);

        final var actual = quaternion.subtract(subtrahend);

        final var expected = new DoubleQuaternion(-4.0D, -4.0D, -4.0D, -4.0D);
        assertThat(actual).isEqualTo(expected);
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
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);
        final var multiplier = new DoubleQuaternion(5.0D, 6.0D, 7.0D, 8.0D);

        final var actual = quaternion.multiply(multiplier);

        final var expected = new DoubleQuaternion(-60.0D, 12.0D, 30.0D, 24.0D);
        assertThat(actual).isEqualTo(expected);
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
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);
        final var divisor = new DoubleQuaternion(5.0D, 6.0D, 7.0D, 8.0D);

        final var actual = quaternion.divide(divisor);

        final var expected = new DoubleQuaternion(0.4022988505747126D, 0.04597701149425287D, 0D, 0.09195402298850573D);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void negate_should_succeed() {
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);

        final var actual = quaternion.negate();

        final var expected = new DoubleQuaternion(-1.0D, -2.0D, -3.0D, -4.0D);

        assertThat(actual).isEqualTo(expected);
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
        1, 1, 1, 1, -1,  0.25, -0.25, -0.25, -0.25
        1, 1, 1, 1,  0,  1,     0,     0,     0
        1, 1, 1, 1,  1,  1,     1,     1,     1
        0, 0, 0, 0,  2,  0,     0,     0,     0
        1, 1, 1, 1,  2, -2,     2,     2,     2
        1, 1, 1, 1,  3, -8,     0,     0,     0
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
        final var quaternion = new DoubleQuaternion(w, x, y, z);

        final var actual = quaternion.pow(exponent);

        final var expected = new DoubleQuaternion(expectedW, expectedX, expectedY, expectedZ);
        assertThat(actual).isEqualTo(expected);
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
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);

        final var actual = quaternion.reciprocal();

        final var expected =
                new DoubleQuaternion(0.03333333333333333D, -0.06666666666666667D, -0.1D, -0.13333333333333333D);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void conjugate_should_succeed() {
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);

        final var actual = quaternion.conjugate();

        final var expected = new DoubleQuaternion(1.0D, -2.0D, -3.0D, -4.0D);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void norm_should_succeed() {
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);

        final var actual = quaternion.norm();

        assertThat(actual).isEqualByComparingTo(30.0D);
    }

    @Test
    void abs_should_succeed() {
        final var quaternion = new DoubleQuaternion(1.0D, 2.0D, 3.0D, 4.0D);

        final var actual = quaternion.abs();

        assertThat(actual).isEqualByComparingTo(5.477225575051661D);
    }
}
