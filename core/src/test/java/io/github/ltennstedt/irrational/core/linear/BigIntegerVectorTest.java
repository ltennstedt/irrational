package io.github.ltennstedt.irrational.core.linear;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import io.github.ltennstedt.irrational.core.linear.BigIntegerVector.BigIntegerVectorEntry;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

final class BigIntegerVectorTest {
    private static Stream<Arguments> newProvider() {
        final var entries = new ArrayList<>(1);
        entries.add(null);
        return Stream.of(
                arguments(null, NullPointerException.class, "entries"),
                arguments(
                        Collections.unmodifiableList(entries),
                        NullPointerException.class,
                        "entries must not contain null but found null at index 0"),
                arguments(
                        List.of(new BigIntegerVectorEntry(2, BigInteger.ZERO)),
                        IllegalArgumentException.class,
                        "indices must be a consecutive integer sequence starting with 1 and ending with entries.size "
                                + "but was [2]"),
                arguments(
                        List.of(
                                new BigIntegerVectorEntry(1, BigInteger.ZERO),
                                new BigIntegerVectorEntry(3, BigInteger.ZERO)),
                        IllegalArgumentException.class,
                        "indices must be a consecutive integer sequence starting with 1 and ending with entries.size "
                                + "but was [1, 3]"));
    }

    @ParameterizedTest
    @MethodSource("newProvider")
    <T extends RuntimeException> void new_should_throw(
            final List<BigIntegerVectorEntry> entries, final Class<T> exceptionType, final String message) {
        assertThatExceptionOfType(exceptionType)
                .isThrownBy(() -> new BigIntegerVector(entries))
                .withMessage(message)
                .withNoCause();
    }

    @Test
    void new_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ZERO);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.ZERO);

        final var actual = new BigIntegerVector(List.of(entry2, entry1));

        assertThat(actual).isEqualTo(new BigIntegerVector(List.of(entry1, entry2)));
    }

    @Test
    void builder_should_throw_when_size_is_negative() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BigIntegerVector.builder(-1))
                .withMessage("size must be greater than or equal to 0 but was -1")
                .withNoCause();
    }

    @Test
    void builder_should_succeed() {
        final var actual = BigIntegerVector.builder(0);

        assertThat(actual).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void entry_should_throw_when_index_is_out_of_bounds(final int index) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.entry(index))
                .withMessage("index must be greater than 0 and less than or equal to 0 but was " + index)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, false
        0, true
        """)
    void isZero_should_succeed(final BigInteger value, final boolean expected) {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ZERO);
        final var entry2 = new BigIntegerVectorEntry(2, value);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));

        final var actual = vector.isZero();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         0, 0, false
        -1, 0, false
         1, 1, false
         0, 1, true
         1, 0, true
        """)
    void isStandardBasisVector_should_succeed(
            final BigInteger value1, final BigInteger value2, final boolean expected) {
        final var entry1 = new BigIntegerVectorEntry(1, value1);
        final var entry2 = new BigIntegerVectorEntry(2, value2);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));

        final var actual = vector.isStandardBasisVector();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
         0,  0, false
        -1, -1, false
         1,  1, false
         0, -1, true
         2,  0, true
        """)
    void isUnitVector_should_succeed(final BigInteger value1, final BigInteger value2, final boolean expected) {
        final var entry1 = new BigIntegerVectorEntry(1, value1);
        final var entry2 = new BigIntegerVectorEntry(2, value2);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));

        final var actual = vector.isUnitVector();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void size_should_succeed() {
        final var actual = BigIntegerVector.EMPTY.size();

        assertThat(actual).isZero();
    }

    @Test
    void indices_should_succeed() {
        final var vector = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.ZERO)));

        final var actual = vector.indices();

        assertThat(actual).containsExactly(1);
    }

    @Test
    void values_should_succeed() {
        final var vector = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.ZERO)));

        final var actual = vector.values();

        assertThat(actual).containsExactly(BigInteger.ZERO);
    }

    @Test
    void value_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ZERO);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.ONE);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));

        final var actual = vector.value(1);

        assertThat(actual).isZero();
    }

    @Test
    void entry_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ZERO);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.ZERO);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));

        final var actual = vector.entry(1);

        assertThat(actual).isSameAs(entry1);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, false
        1, true
        """)
    void containsIndex_should_succeed(final int index, final boolean expected) {
        final var vector = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.ZERO)));

        final var actual = vector.containsIndex(index);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void containsValue_should_throw_when_value_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.containsValue(null))
                .withMessage("value")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, false
        0, true
        """)
    void containsValue_should_succeed(final BigInteger value, final boolean expected) {
        final var vector = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.ZERO)));

        final var actual = vector.containsValue(value);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void containsEntry_should_throw_when_entry_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.containsEntry(null))
                .withMessage("entry")
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 2, false
        2, 1, false
        1, 1, true
        """)
    void containsEntry_should_succeed(final int index, final BigInteger value, final boolean expected) {
        final var vector = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.ONE)));

        final var actual = vector.containsEntry(new BigIntegerVectorEntry(index, value));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_throw_when_sizes_are_different() {
        final var summand = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.ONE)));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.add(summand))
                .withMessage("vectors must be of the same size but were 0 and 1")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ONE);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.TWO);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));
        final var entry3 = new BigIntegerVectorEntry(1, BigInteger.valueOf(3L));
        final var entry4 = new BigIntegerVectorEntry(2, BigInteger.valueOf(4L));
        final var summand = new BigIntegerVector(List.of(entry3, entry4));

        final var actual = vector.add(summand);

        final var entry5 = new BigIntegerVectorEntry(1, BigInteger.valueOf(4L));
        final var entry6 = new BigIntegerVectorEntry(2, BigInteger.valueOf(6L));
        final var expected = new BigIntegerVector(List.of(entry5, entry6));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtract_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_throw_when_sizes_are_different() {
        final var subtrahend = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.ONE)));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.subtract(subtrahend))
                .withMessage("vectors must be of the same size but were 0 and 1")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ONE);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.TWO);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));
        final var entry3 = new BigIntegerVectorEntry(1, BigInteger.valueOf(3L));
        final var entry4 = new BigIntegerVectorEntry(2, BigInteger.valueOf(5L));
        final var subtrahend = new BigIntegerVector(List.of(entry3, entry4));

        final var actual = vector.subtract(subtrahend);

        final var entry5 = new BigIntegerVectorEntry(1, BigInteger.valueOf(-2L));
        final var entry6 = new BigIntegerVectorEntry(2, BigInteger.valueOf(-3L));
        final var expected = new BigIntegerVector(List.of(entry5, entry6));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void scalarMultiply_should_throw_when_scalar_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.scalarMultiply(null))
                .withMessage("scalar")
                .withNoCause();
    }

    @Test
    void scalarMultiply_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ONE);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.TWO);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));

        final var actual = vector.scalarMultiply(BigInteger.valueOf(3L));

        final var entry3 = new BigIntegerVectorEntry(1, BigInteger.valueOf(3L));
        final var entry4 = new BigIntegerVectorEntry(2, BigInteger.valueOf(6L));
        final var expected = new BigIntegerVector(List.of(entry3, entry4));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void dotProduct_should_throw_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.dotProduct(null))
                .withMessage("other")
                .withNoCause();
    }

    @Test
    void dotProduct_should_throw_when_sizes_are_different() {
        final var other = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.ZERO)));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.dotProduct(other))
                .withMessage("vectors must be of the same size but were 0 and 1")
                .withNoCause();
    }

    @Test
    void dotProduct_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ONE);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.TWO);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));
        final var entry3 = new BigIntegerVectorEntry(1, BigInteger.valueOf(3L));
        final var entry4 = new BigIntegerVectorEntry(2, BigInteger.valueOf(4L));
        final var other = new BigIntegerVector(List.of(entry3, entry4));

        final var actual = vector.dotProduct(other);

        assertThat(actual).isEqualTo(BigInteger.valueOf(11L));
    }

    @Test
    void negate_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ONE);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.TWO);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));
        final var entry3 = new BigIntegerVectorEntry(1, BigInteger.valueOf(-1L));
        final var entry4 = new BigIntegerVectorEntry(2, BigInteger.valueOf(-2L));
        final var expected = new BigIntegerVector(List.of(entry3, entry4));

        final var actual = vector.negate();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void norm_should_throw_when_mathContext_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> BigIntegerVector.EMPTY.norm(null))
                .withMessage("mathContext")
                .withNoCause();
    }

    @Test
    void norm_should_succeed() {
        final var entry1 = new BigIntegerVectorEntry(1, BigInteger.ONE);
        final var entry2 = new BigIntegerVectorEntry(2, BigInteger.TWO);
        final var vector = new BigIntegerVector(List.of(entry1, entry2));

        final var actual = vector.norm(MathContext.DECIMAL32);

        assertThat(actual).isEqualByComparingTo(new BigDecimal("2.236068"));
    }

    @Nested
    final class BigIntegerVectorEntryTest {
        @ParameterizedTest
        @CsvSource(textBlock = """
            java.lang.NullPointerException,     1,  , value
            java.lang.IllegalArgumentException, 0, 1, index must be greater than 0 but was 0
            """)
        <T extends RuntimeException> void new_should_throw(
                final Class<T> exceptionType, final int index, final BigInteger value, final String message) {
            assertThatExceptionOfType(exceptionType)
                    .isThrownBy(() -> new BigIntegerVectorEntry(index, value))
                    .withMessage(message)
                    .withNoCause();
        }

        @Test
        void withIndex_should_throw_when_index_is_less_than_one() {
            final var entry = new BigIntegerVectorEntry(1, BigInteger.ZERO);

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> entry.withIndex(0))
                    .withMessage("index must be greater than 0 but was 0")
                    .withNoCause();
        }

        @Test
        void withIndex_should_succeed() {
            final var entry = new BigIntegerVectorEntry(1, BigInteger.ZERO);

            final var actual = entry.withIndex(2);

            assertThat(actual).isEqualTo(new BigIntegerVectorEntry(2, BigInteger.ZERO));
        }

        @Test
        void withValue_should_throw_when_newValue_is_null() {
            final var entry = new BigIntegerVectorEntry(1, BigInteger.ZERO);

            assertThatNullPointerException()
                    .isThrownBy(() -> entry.withValue(null))
                    .withMessage("newValue")
                    .withNoCause();
        }

        @Test
        void withValue_should_succeed() {
            final var entry = new BigIntegerVectorEntry(1, BigInteger.ZERO);

            assertThat(entry.withValue(BigInteger.TWO)).isEqualTo(new BigIntegerVectorEntry(1, BigInteger.TWO));
        }
    }

    @Nested
    final class BigIntegerVectorBuilderTest {
        @ParameterizedTest
        @ValueSource(ints = {0, 2})
        void entry_should_throw_when_index_is_out_of_bounds(final int index) {
            final var builder = BigIntegerVector.builder(1);

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> builder.entry(index, BigInteger.ZERO))
                    .withMessage("index must be greater than 0 and less than or equal to 1 but was " + index)
                    .withNoCause();
        }

        @Test
        void entry_should_throw_when_index_is_already_present() {
            final var builder = BigIntegerVector.builder(2).entry(1, BigInteger.ZERO);

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> builder.entry(1, BigInteger.ONE))
                    .withMessage("index 1 is already present")
                    .withNoCause();
        }

        @Test
        void entry_should_throw_when_value_is_null() {
            final var builder = BigIntegerVector.builder(1);

            assertThatNullPointerException()
                    .isThrownBy(() -> builder.entry(1, null))
                    .withMessage("value")
                    .withNoCause();
        }

        @Test
        void entry_should_succeed() {
            final var builder = BigIntegerVector.builder(1);

            final var actual = builder.entry(1, BigInteger.TWO);

            assertThat(actual.getEntries()).containsExactly(new BigIntegerVectorEntry(1, BigInteger.TWO));
            assertThat(actual).isSameAs(builder);
        }

        @Test
        void build_should_throw_when_sizes_are_different() {
            final var builder = BigIntegerVector.builder(2).entry(1, BigInteger.ZERO);

            assertThatIllegalStateException()
                    .isThrownBy(builder::build)
                    .withMessage("Expected 2 entries but was 1")
                    .withNoCause();
        }

        @Test
        void build_should_succeed() {
            final var builder =
                    BigIntegerVector.builder(2).entry(2, BigInteger.ONE).entry(1, BigInteger.TWO);

            final var actual = builder.build();

            final var entry1 = new BigIntegerVectorEntry(1, BigInteger.TWO);
            final var entry2 = new BigIntegerVectorEntry(2, BigInteger.ONE);
            final var expected = new BigIntegerVector(List.of(entry1, entry2));
            assertThat(actual).isEqualTo(expected);
        }
    }
}
