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
    void pi_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> PiCalculator.pi(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void pi_should_succeed() {
        final var pi = new BigDecimal(String.valueOf(StrictMath.PI));
        final var mathContext = new MathContext(pi.precision(), RoundingMode.HALF_EVEN);

        final var actual = PiCalculator.pi(mathContext);

        final var offset = within(new BigDecimal("1e-12"));
        assertThat(actual).isCloseTo(pi, offset);
    }
}
