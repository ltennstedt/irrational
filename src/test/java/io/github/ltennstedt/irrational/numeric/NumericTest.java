package io.github.ltennstedt.irrational.numeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Execution(ExecutionMode.CONCURRENT)
final class NumericTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            1, false
            0, true
        """)
    void isNotInvertible_should_succeed(final long numerator, final boolean expected) {
        assertThat(LongRational.of(numerator).isNotInvertible()).isSameAs(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 1, false
        1, 2, true
    """)
    void isNotInteger_should_succeed(final long numerator, final long denominator, final boolean expected) {
        assertThat(LongRational.of(numerator, denominator).isNotInteger()).isSameAs(expected);
    }
}
