package irrational.numeric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

@Execution(CONCURRENT)
final class NumericTest {
    @Test
    void isNotInvertible_should_be_false_isInvertible_is_true() {
        assertThat(LongRational.ONE.isNotInvertible()).isFalse();
    }

    @Test
    void isNotInvertible_should_be_true_isInvertible_is_true() {
        assertThat(LongRational.ZERO.isNotInvertible()).isTrue();
    }

    @Test
    void isNotInteger_should_be_false_when_denominator_is_one() {
        assertThat(LongRational.ONE.isNotInteger()).isFalse();
    }

    @Test
    void isNotInteger_should_be_true_when_denominator_is_greater_than_one() {
        assertThat(LongRational.of(1L, 2L).isNotInteger()).isTrue();
    }
}
