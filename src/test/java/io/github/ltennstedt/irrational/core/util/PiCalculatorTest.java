package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

final class PiCalculatorTest {
    @Test
    void pi_should_throw_Exception_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> PiCalculator.pi(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void pi_should_succeed() {
        final var big_pi = new BigDecimal(String.valueOf(Math.PI));

        final var actual = PiCalculator.pi(new MathContext(big_pi.precision(), RoundingMode.HALF_EVEN));

        assertThat(actual).isCloseTo(big_pi, within(new BigDecimal(String.valueOf(Doubles.EPSILON))));
    }
}
