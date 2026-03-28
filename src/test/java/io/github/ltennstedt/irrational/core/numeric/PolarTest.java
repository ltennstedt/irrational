package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class PolarTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 1, false
        1, 0, true
        """)
    void isInvertible_should_succeed(final double radial, final double angular, final boolean expected) {
        assertThat(new DoublePolar(radial, angular).isInvertible()).isEqualTo(expected);
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoublePolar.ZERO.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(new DoublePolar(3D, 0D).add(new DoublePolar(4D, Math.PI / 2D)))
                .isEqualTo(new DoublePolar(5D, Math.atan2(4D, 3D)));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> DoublePolar.ZERO.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(new DoublePolar(5D, 0D).subtract(new DoublePolar(3D, Math.PI / 2D)))
                .isEqualTo(new DoublePolar(Math.sqrt(34D), Math.atan2(-3D, 5D)));
    }
}
