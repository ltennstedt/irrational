package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class DoublesTest {
    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY})
    void check_should_throw_when_d_is_NAN_or_INFINITE(final double d) {
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> Doubles.check(d, "name"))
                .withMessage("name must be a finite number but was " + d)
                .withNoCause();
    }

    @Test
    void check_should_return_value_when_d_is_a_number() {
        final var actual = Doubles.check(0.0D, "name");

        assertThat(actual).isZero();
    }
}
