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
        assertThat(new LongRational(numerator, 1L).isImproper()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            -1, true
             0, false
             1, false
        """)
    void isNegative_should_succeed(final long numerator, final boolean expected) {
        assertThat(new LongRational(numerator, 1L).isNegative()).isEqualTo(expected);
    }
}
