package sliderule.core.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class LongRationalTest {
    private final LongRational rational1 = new LongRational(2, 3);
    private final LongRational rational2 = new LongRational(4, 5);

    @Test
    void new_should_throw_exception_when_denominator_is_zero() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LongRational(1, 0))
                .withMessage("Expected denominator != 0 but denominator=0")
                .withNoCause();
    }

    @Test
    void new_should_succeed() {
        assertThat(rational1.getNumerator()).isEqualByComparingTo(2L);
        assertThat(rational1.getDenominator()).isEqualByComparingTo(3L);
    }

    @Test
    void isInvertible_should_return_false_when_numerator_is_0() {
        assertThat(LongRational.ZERO.isInvertible()).isFalse();
    }

    @Test
    void isInvertible_should_return_true_when_numerator_is_not_0() {
        assertThat(LongRational.ONE.isInvertible()).isTrue();
    }

    @Test
    void isUnit_should_return_false_when_numerator_is_not_1() {
        assertThat(rational1.isUnit()).isFalse();
    }

    @Test
    void isUnit_should_return_true_when_numerator_is_one() {
        assertThat(LongRational.ONE.isUnit()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {3, 5, 6, 7, 9})
    void isDyadic_should_return_false_when_denominator_is_not_power_of_two(final long denominator) {
        assertThat(new LongRational(1, denominator).isDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 4, 8})
    void isDyadic_should_return_true_when_denominator_is_power_of_two(final long denominator) {
        assertThat(new LongRational(1, denominator).isDyadic()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 4, 8})
    void isNotDyadic_should_return_false_when_denominator_is_power_of_two(final long denominator) {
        assertThat(new LongRational(1, denominator).isNotDyadic()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {3, 5, 6, 7, 9})
    void isNotDyadic_should_return_true_when_denominator_is_not_power_of_two(final long denominator) {
        assertThat(new LongRational(1, denominator).isNotDyadic()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 3, 4, 5, 6, 7, 8, 9})
    void hasInvisibleDenominator_should_return_false_when_denominator_is_greater_than_one(final long denominator) {
        assertThat(new LongRational(1, denominator).hasInvisibleDenominator()).isFalse();
    }

    @Test
    void hasInvisibleDenominator_should_return_true_when_denominator_is_one() {
        assertThat(LongRational.ONE.hasInvisibleDenominator()).isTrue();
    }

    @Test
    void doesNothaveInvisibleDenominator_should_return_false_when_denominator_is_one() {
        assertThat(LongRational.ONE.doesNotHaveInvisibleDenominator()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 3, 4, 5, 6, 7, 8, 9})
    void doesNotHaveInvisibleDenominator_should_return_true_when_denominator_is_greater_than_one(
            final long denominator) {
        assertThat(new LongRational(1, denominator).doesNotHaveInvisibleDenominator())
                .isTrue();
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
        assertThat(rational1.add(rational2)).isEqualTo(new LongRational(22, 15));
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
        assertThat(rational1.subtract(rational2)).isEqualTo(new LongRational(-2, 15));
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
        assertThat(rational1.multiply(rational2)).isEqualTo(new LongRational(8, 15));
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
                .isThrownBy(() -> rational1.divide(LongRational.ZERO))
                .withMessage("divisor must be invertible but is LongRational{numerator=0, denominator=1}")
                .withNoCause();
    }

    @Test
    void divide_should_succeed() {
        assertThat(rational1.divide(rational2)).isEqualTo(new LongRational(8, 15));
    }

    @Test
    void invert_should_throw_exception_when_this_is_not_invertible() {
        assertThatIllegalStateException()
                .isThrownBy(LongRational.ZERO::invert)
                .withMessage("must be invertible but is LongRational{numerator=0, denominator=1}")
                .withNoCause();
    }

    @Test
    void invert_should_succeed() {
        assertThat(rational1.invert()).isEqualTo(new LongRational(3, 2));
    }

    @Test
    void power_should_succeed() {
        assertThat(rational1.power(2)).isEqualTo(new LongRational(4, 9));
    }

    @Test
    void hashCode_and_equals_should_succeed() {
        EqualsVerifier.forClass(LongRational.class).verify();
    }

    @Test
    void toString_should_succeed() {
        assertThat(rational1).hasToString("LongRational{numerator=2, denominator=3}");
    }
}
