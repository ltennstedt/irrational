package io.github.ltennstedt.irrational.core.numeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class RationalTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
            -1, true
             0, false
             1, true
        """)
    void isImproper_should_succeed(final long numerator, final boolean expected) {
        final var rational = new LongRational(numerator, 1L);

        final var actual = rational.isImproper();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            -1, true
             0, false
             1, false
        """)
    void isNegative_should_succeed(final long numerator, final boolean expected) {
        final var rational = new LongRational(numerator, 1L);

        final var actual = rational.isNegative();

        assertThat(actual).isEqualTo(expected);
    }
}
