package io.github.ltennstedt.irrational.core.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class DoublesTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
        1e-13, true
        1e-12, false
        """)
    void isNear_should_succeed(final double x, final boolean expected) {
        assertThat(Doubles.isNear(x, 0D)).isEqualTo(expected);
    }
}
