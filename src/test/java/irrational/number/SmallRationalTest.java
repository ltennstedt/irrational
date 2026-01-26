package irrational.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

@Execution(ExecutionMode.CONCURRENT)
final class SmallRationalTest {
    private final SmallRational rational1 = SmallRational.of(2L, 3L);
    private final SmallRational rational2 = SmallRational.of(4L, 5L);

    @Test
    void of_numerator_should_return_ZERO_when_numerator_is_0() {
        final var actual = SmallRational.of(0L);

        assertThat(actual).isSameAs(SmallRational.ZERO);
    }

    @Test
    void of_numerator_should_return_ONE_when_numerator_is_one() {
        final var actual = SmallRational.of(1L);

        assertThat(actual).isSameAs(SmallRational.ONE);
    }

    @Test
    void of_numerator_should_succeed() {
        final var actual = SmallRational.of(2L);

        assertThat(actual.getNumerator()).isEqualByComparingTo(2L);
        assertThat(actual.getDenominator()).isEqualByComparingTo(1L);
    }

    @Test
    void of_numerator_and_denominator_should_throw_exception_when_denominator_is_zero() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> SmallRational.of(1L, 0L))
                .withMessage("denominator must not be 0 but was 0")
                .withNoCause();
    }

    @Test
    void of_numerator_and_denominator_should_return_ZERO_when_numerator_is_0() {
        final var actual = SmallRational.of(0L, 1L);

        assertThat(actual).isSameAs(SmallRational.ZERO);
    }

    @ParameterizedTest
    @LongRangeSource(from = 1L, to = 10L)
    void of_numerator_and_denominator_should_return_ONE_when_numerator_equals_denominator(final long l) {
        final var actual = SmallRational.of(l, l);

        assertThat(actual).isSameAs(SmallRational.ONE);
    }

    @Test
    void of_numerator_and_denominator_should_normalize() {
        final var actual = SmallRational.of(2L, -4L);

        assertThat(actual.getNumerator()).isEqualByComparingTo(-1L);
        assertThat(actual.getDenominator()).isEqualByComparingTo(2L);
    }

    @Test
    void of_numerator_and_denominator_should_succeed() {
        assertThat(rational1.getNumerator()).isEqualByComparingTo(2L);
        assertThat(rational1.getDenominator()).isEqualByComparingTo(3L);
    }

    @Test
    void isInvertible_should_be_false_when_numerator_is_0() {
        assertThat(SmallRational.ZERO.isInvertible()).isFalse();
    }

    @Test
    void isInvertible_should_be_true_when_numerator_is_not_0() {
        assertThat(SmallRational.ONE.isInvertible()).isTrue();
    }

    @Test
    void isZero_should_be_false_when_rational_is_not_zero() {
        assertThat(SmallRational.ONE.isZero()).isFalse();
    }

    @Test
    void isZero_should_be_true_when_rational_is_zero() {
        assertThat(SmallRational.ZERO.isZero()).isTrue();
    }

    @Test
    void isOne_should_be_false_when_rational_is_not_one() {
        assertThat(SmallRational.ZERO.isOne()).isFalse();
    }

    @Test
    void isOne_should_be_true_when_rational_is_one() {
        assertThat(SmallRational.ONE.isOne()).isTrue();
    }

    @ParameterizedTest
    @LongRangeSource(from = 2L, to = 10L)
    void isInteger_should_be_false_when_denominator_is_greater_than_one(final long denominator) {
        assertThat(SmallRational.of(1L, denominator).isInteger()).isFalse();
    }

    @Test
    void isInteger_should_be_true_when_denominator_is_one() {
        assertThat(SmallRational.ONE.isInteger()).isTrue();
    }

    @Test
    void isUnit_should_be_false_when_numerator_is_not_1() {
        assertThat(rational1.isUnit()).isFalse();
    }

    @Test
    void isUnit_should_be_true_when_numerator_is_one() {
        assertThat(SmallRational.ONE.isUnit()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 5L, 6L, 7L, 9L})
    void isDyadic_should_be_false_when_denominator_is_not_power_of_two(final long denominator) {
        assertThat(SmallRational.of(1L, denominator).isDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 4L, 8L})
    void isDyadic_should_be_true_when_denominator_is_power_of_two(final long denominator) {
        assertThat(SmallRational.of(1L, denominator).isDyadic()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 4L, 8L})
    void isNotDyadic_should_be_false_when_denominator_is_power_of_two(final long denominator) {
        assertThat(SmallRational.of(1L, denominator).isNotDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 5L, 6L, 7L, 9L})
    void isNotDyadic_should_be_true_when_denominator_is_not_power_of_two(final long denominator) {
        assertThat(SmallRational.of(1L, denominator).isNotDyadic()).isTrue();
    }

    @Test
    void add_should_throw_exception_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> rational1.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        assertThat(rational1.add(rational2)).isEqualTo(SmallRational.of(22L, 15L));
    }

    @Test
    void subtract_should_throw_exception_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> rational1.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        assertThat(rational1.subtract(rational2)).isEqualTo(SmallRational.of(-2L, 15L));
    }

    @Test
    void multiply_should_throw_exception_when_multiplier_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> rational1.multiply(null))
                .withMessage("multiplier")
                .withNoCause();
    }

    @Test
    void multiply_should_succeed() {
        assertThat(rational1.multiply(rational2)).isEqualTo(SmallRational.of(8L, 15L));
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> rational1.divide(null))
                .withMessage("divisor")
                .withNoCause();
    }

    @Test
    void divide_should_throw_exception_when_divisor_is_not_invertible() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> rational1.divide(SmallRational.ZERO))
                .withMessage("divisor must be invertible but was LongRational{numerator=0, denominator=1}")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        assertThat(rational1.divide(rational2)).isEqualTo(SmallRational.of(8L, 15L));
    }

    @Test
    void invert_should_throw_exception_when_this_is_not_invertible() {
        assertThatIllegalStateException()
                .isThrownBy(SmallRational.ZERO::invert)
                .withMessage("this must be invertible but was LongRational{numerator=0, denominator=1}")
                .withNoCause();
    }

    @Test
    void invert_should_succeed() {
        assertThat(rational1.invert()).isEqualTo(SmallRational.of(3L, 2L));
    }

    @Test
    void power_should_succeed() {
        assertThat(rational1.power(2)).isEqualTo(SmallRational.of(4L, 9L));
    }

    @Test
    void hashCode_and_equals_should_succeed() {
        EqualsVerifier.forClass(SmallRational.class).verify();
    }

    @Test
    void toString_should_succeed() {
        assertThat(rational1).hasToString("LongRational{numerator=2, denominator=3}");
    }
}
