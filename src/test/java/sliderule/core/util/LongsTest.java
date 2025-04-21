package sliderule.core.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class LongsTest {
    static Stream<Arguments> baseAndExponentAndExpectedProvider() {
        return Stream.of(
                Arguments.of(0, 1, 0),
                Arguments.of(1, -1, 1),
                Arguments.of(-1, 0, 1),
                Arguments.of(2, 1, 2),
                Arguments.of(2, 3, 8),
                Arguments.of(-3, 2, 9),
                Arguments.of(2, -2, 0.25));
    }

    @ParameterizedTest
    @MethodSource("baseAndExponentAndExpectedProvider")
    void powerExact_should_succeed(final long base, final int exponent, final double expected) {
        assertThat(Longs.powerExact(base, exponent)).isEqualByComparingTo(expected);
    }
}
