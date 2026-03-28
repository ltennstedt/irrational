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

final class DoublePolarTest {
    private final Offset<Double> withinEpsilon = within(Doubles.EPSILON);

    @ParameterizedTest
    @CsvSource(textBlock = """
        -5, 1.570796327, 5, 4.712388980589793
         5, 1.570796327, 5, 1.570796327
        """)
    void new_should_succeed(
            final double radial, final double angular, final double expectedRadial, final double expectedAngular) {
        assertThat(new DoublePolar(radial, angular)).isEqualTo(new DoublePolar(expectedRadial, expectedAngular));
    }

    @Test
    void ofComplex_should_throw_exception_when_complex_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoublePolar.ofComplex(null))
                .withMessage("complex")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        3, 0, 3, 0
        0, 4, 4, 1.5707963267948966
        """)
    void ofComplex_should_succeed(
            final double real, final double imaginary, final double expectedRadial, final double expectedAngular) {
        assertThat(DoublePolar.ofComplex(new DoubleComplex(real, imaginary)))
                .isEqualTo(new DoublePolar(expectedRadial, expectedAngular));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 0, false
        0, 1, true
        """)
    void isZero_should_succeed(final double radial, final double angular, final boolean expected) {
        assertThat(new DoublePolar(radial, angular).isZero()).isEqualTo(expected);
    }

    @Test
    void multiply_should_throw_exception_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> new DoublePolar(10D, 4D).multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        final var actual = new DoublePolar(10D, 4D).multiply(new DoublePolar(5D, 1D));

        assertThat(actual.radial()).isCloseTo(50D, withinEpsilon);
        assertThat(actual.angular()).isCloseTo(5D, withinEpsilon);
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> new DoublePolar(10D, 4D).divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> new DoublePolar(10D, 4D).divide(new DoublePolar(0D, 0D)))
                .withMessage("divisor must be invertible but was DoublePolar[radial=0.0, angular=0.0]")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        final var actual = new DoublePolar(10D, 4D).divide(new DoublePolar(5D, 1D));

        assertThat(actual.radial()).isCloseTo(2D, withinEpsilon);
        assertThat(actual.angular()).isCloseTo(3D, withinEpsilon);
    }

    @Test
    void pow_should_succeed() {
        final var actual = new DoublePolar(3D, 0.523598776D).pow(2);

        assertThat(actual.radial()).isCloseTo(9D, withinEpsilon);
        assertThat(actual.angular()).isCloseTo(1.047197552D, withinEpsilon);
    }

    @Test
    void reciprocal_should_succeed() {
        final var actual = new DoublePolar(10D, 0.523598776D).reciprocal();

        assertThat(actual.radial()).isCloseTo(0.1D, withinEpsilon);
        assertThat(actual.angular()).isCloseTo(5.75958653118D, withinEpsilon);
    }

    @Test
    void negate_should_succeed() {
        final var actual = new DoublePolar(1D, Math.PI).negate();

        assertThat(actual.radial()).isEqualTo(1D);
        assertThat(actual.angular()).isCloseTo(0D, withinEpsilon);
    }

    @Test
    void toComplex_should_succeed() {
        final var actual = new DoublePolar(2D, 0.523598776D).toComplex();

        assertThat(actual.real()).isCloseTo(1.732050807167D, withinEpsilon);
        assertThat(actual.imaginary()).isCloseTo(1.000000000696D, withinEpsilon);
    }
}
