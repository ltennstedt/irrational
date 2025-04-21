package io.github.ltennstedt.irrational.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Execution(ExecutionMode.CONCURRENT)
final class LongsTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
            0,  1, 0
            0,  1, 0
            1, -1, 1
            2,  1, 2
            2,  3, 8
            -3, 2, 9
            2, -2, 0.25
        """)
    void power_should_succeed(final long base, final int exponent, final double expected) {
        assertThat(Longs.power(base, exponent)).isEqualByComparingTo(expected);
    }
}
