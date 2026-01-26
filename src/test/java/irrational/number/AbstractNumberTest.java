package irrational.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Execution(ExecutionMode.CONCURRENT)
final class AbstractNumberTest {
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

    @ParameterizedTest
    @ValueSource(longs = {2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L})
    void isNotInteger_should_be_true_when_denominator_is_greater_than_one(final long denominator) {
        assertThat(LongRational.of(1L, denominator).isNotInteger()).isTrue();
    }

    @Test
    void toByte_should_throw_Exception_when_arithmetic_overflow_occurs() {
        final var rational = LongRational.of(Short.MAX_VALUE);

        assertThatExceptionOfType(ArithmeticException.class).isThrownBy(rational::toByte);
    }

    @Test
    void toByte_should_succeed() {
        assertThat(LongRational.ZERO.toByte()).isZero();
    }

    @Test
    void toShort_should_throw_Exception_when_arithmetic_overflow_occurs() {
        final var rational = LongRational.of(Integer.MAX_VALUE);

        assertThatExceptionOfType(ArithmeticException.class).isThrownBy(rational::toShort);
    }

    @Test
    void toShort_should_succeed() {
        assertThat(LongRational.ZERO.toShort()).isZero();
    }

    @Test
    void toInt_should_throw_Exception_when_arithmetic_overflow_occurs() {
        final var rational = LongRational.of(Long.MAX_VALUE);

        assertThatExceptionOfType(ArithmeticException.class).isThrownBy(rational::toInt);
    }

    @Test
    void toInt_should_succeed() {
        assertThat(LongRational.ZERO.toInt()).isZero();
    }

    @Test
    void toLong_should_succeed() {
        assertThat(LongRational.ZERO.toLong()).isZero();
    }

    @Test
    void toFloat_should_succeed() {
        assertThat(LongRational.ZERO.toFloat()).isZero();
    }

    @Test
    void toDouble_should_succeed() {
        assertThat(LongRational.ZERO.toDouble()).isZero();
    }

    @Test
    void toBigInteger_should_throw_Exception_when_arithmetic_overflow_occurs() {
        final var rational = LongRational.of(1L, 2L);

        assertThatExceptionOfType(ArithmeticException.class).isThrownBy(rational::toBigInteger);
    }

    @Test
    void toBigInteger_should_succeed() {
        assertThat(LongRational.ZERO.toBigInteger()).isZero();
    }
}
