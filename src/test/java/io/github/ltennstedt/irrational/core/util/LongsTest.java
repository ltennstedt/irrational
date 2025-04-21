package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class LongsTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
              9,  3, 3
              8,  4, 4
              5,  3, 1
              1,  0, 1
              0,  0, 0
             -1,  0, 1
             -5, -3, 1
             -8,  4, 4
             -9, -3, 3
        """)
    void gcd_should_succeed(final long a, final long b, final long expected) {
        assertThat(Longs.gcd(a, b)).isEqualTo(expected);
    }
}
