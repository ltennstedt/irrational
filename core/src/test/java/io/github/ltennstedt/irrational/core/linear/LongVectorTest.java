package io.github.ltennstedt.irrational.core.linear;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import io.github.ltennstedt.irrational.core.linear.BigIntegerVector.BigIntegerVectorEntry;
import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry;
import io.github.ltennstedt.irrational.core.util.Doubles;
import java.math.BigInteger;
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

final class LongVectorTest {
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
                        List.of(new LongVectorEntry(2, 0L)),
                        IllegalArgumentException.class,
                        "indices must be a consecutive integer sequence starting with 1 and ending with entries.size "
                                + "but was [2]"),
                arguments(
                        List.of(new LongVectorEntry(1, 0L), new LongVectorEntry(3, 0L)),
                        IllegalArgumentException.class,
                        "indices must be a consecutive integer sequence starting with 1 and ending with entries.size "
                                + "but was [1, 3]"));
    }

    @ParameterizedTest
    @MethodSource("newProvider")
    <T extends RuntimeException> void new_should_throw(
            final List<LongVectorEntry> entries, final Class<T> exceptionType, final String message) {
        assertThatExceptionOfType(exceptionType)
                .isThrownBy(() -> new LongVector(entries))
                .withMessage(message)
                .withNoCause();
    }

    @Test
    void new_should_succeed() {
        final var actual = new LongVector(List.of(new LongVectorEntry(2, 0L), new LongVectorEntry(1, 0L)));
        final var expected = new LongVector(List.of(new LongVectorEntry(1, 0L), new LongVectorEntry(2, 0L)));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void builder_should_throw_when_size_is_negative() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LongVector.builder(-1))
                .withMessage("size must be greater than or equal to 0 but was -1")
                .withNoCause();
    }

    @Test
    void builder_should_succeed() {
        final var actual = LongVector.builder(0);

        assertThat(actual).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void entry_should_throw_when_index_is_out_of_bounds(final int index) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LongVector.EMPTY.entry(index))
                .withMessage("index must be greater than 0 and less than or equal to 0 but was " + index)
                .withNoCause();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, false
        0, true
        """)
    void isZero_should_succeed(final long value, final boolean expected) {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 0L), new LongVectorEntry(2, value)));

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
    void isStandardBasisVector_should_succeed(final long value1, final long value2, final boolean expected) {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, value1), new LongVectorEntry(2, value2)));

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
    void isUnitVector_should_succeed(final long value1, final long value2, final boolean expected) {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, value1), new LongVectorEntry(2, value2)));

        final var actual = vector.isUnitVector();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void size_should_succeed() {
        final var actual = LongVector.EMPTY.size();

        assertThat(actual).isZero();
    }

    @Test
    void indices_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 0L)));

        final var actual = vector.indices();

        assertThat(actual).containsExactly(1);
    }

    @Test
    void values_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 0L)));

        final var actual = vector.values();

        assertThat(actual).containsExactly(0);
    }

    @Test
    void value_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 0L), new LongVectorEntry(2, 1L)));

        final var value = vector.value(1);

        assertThat(value).isZero();
    }

    @Test
    void entry_should_succeed() {
        final var entry = new LongVectorEntry(1, 0L);
        final var vector = new LongVector(List.of(entry, new LongVectorEntry(2, 0L)));

        final var actual = vector.entry(1);

        assertThat(actual).isSameAs(entry);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, false
        1, true
        """)
    void containsIndex_should_succeed(final int index, final boolean expected) {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 0L)));

        final var actual = vector.containsIndex(index);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, false
        0, true
        """)
    void containsValue_should_succeed(final long value, final boolean expected) {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 0L)));

        final var actual = vector.containsValue(value);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        1, 2, false
        2, 1, false
        1, 1, true
        """)
    void containsEntry_should_succeed(final int index, final long value, final boolean expected) {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 1L)));

        final var actual = vector.containsEntry(new LongVectorEntry(index, value));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void containsEntry_should_throw_when_entry_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongVector.EMPTY.containsEntry(null))
                .withMessage("entry")
                .withNoCause();
    }

    @Test
    void add_should_throw_when_summand_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongVector.EMPTY.add(null))
                .withMessage("summand")
                .withNoCause();
    }

    @Test
    void add_should_throw_when_sizes_are_different() {
        final var summand = new LongVector(List.of(new LongVectorEntry(1, 1L)));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> LongVector.EMPTY.add(summand))
                .withMessage("vectors must be of the same size but were 0 and 1")
                .withNoCause();
    }

    @Test
    void add_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 1L), new LongVectorEntry(2, 2L)));
        final var summand = new LongVector(List.of(new LongVectorEntry(1, 3L), new LongVectorEntry(2, 4L)));

        final var actual = vector.add(summand);

        final var expected = new LongVector(List.of(new LongVectorEntry(1, 4L), new LongVectorEntry(2, 6L)));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtract_should_throw_when_subtrahend_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongVector.EMPTY.subtract(null))
                .withMessage("subtrahend")
                .withNoCause();
    }

    @Test
    void subtract_should_throw_when_sizes_are_different() {
        final var subtrahend = new LongVector(List.of(new LongVectorEntry(1, 1L)));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> LongVector.EMPTY.subtract(subtrahend))
                .withMessage("vectors must be of the same size but were 0 and 1")
                .withNoCause();
    }

    @Test
    void subtract_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 1L), new LongVectorEntry(2, 2L)));
        final var subtrahend = new LongVector(List.of(new LongVectorEntry(1, 3L), new LongVectorEntry(2, 5L)));

        final var actual = vector.subtract(subtrahend);

        final var expected = new LongVector(List.of(new LongVectorEntry(1, -2L), new LongVectorEntry(2, -3L)));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void scalarMultiply_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 1L), new LongVectorEntry(2, 2L)));

        final var actual = vector.scalarMultiply(3L);

        final var expected = new LongVector(List.of(new LongVectorEntry(1, 3L), new LongVectorEntry(2, 6L)));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void dotProduct_should_throw_when_other_is_null() {
        assertThatNullPointerException()
                .isThrownBy(() -> LongVector.EMPTY.dotProduct(null))
                .withMessage("other")
                .withNoCause();
    }

    @Test
    void dotProduct_should_throw_when_sizes_are_different() {
        final var other = new LongVector(List.of(new LongVectorEntry(1, 0L)));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> LongVector.EMPTY.dotProduct(other))
                .withMessage("vectors must be of the same size but were 0 and 1")
                .withNoCause();
    }

    @Test
    void dotProduct_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 1L), new LongVectorEntry(2, 2L)));
        final var other = new LongVector(List.of(new LongVectorEntry(1, 3L), new LongVectorEntry(2, 4L)));

        final var actual = vector.dotProduct(other);

        assertThat(actual).isEqualTo(11L);
    }

    @Test
    void negate_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 1L), new LongVectorEntry(2, 2L)));

        final var actual = vector.negate();

        final var expected = new LongVector(List.of(new LongVectorEntry(1, -1L), new LongVectorEntry(2, -2L)));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void norm_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 1L), new LongVectorEntry(2, 2L)));

        final var actual = vector.norm();

        assertThat(actual).isCloseTo(2.23606797749979D, within(Doubles.EPSILON));
    }

    @Test
    void toBigIntegerVector_should_succeed() {
        final var vector = new LongVector(List.of(new LongVectorEntry(1, 2L)));

        final var actual = vector.toBigIntegerVector();

        final var expected = new BigIntegerVector(List.of(new BigIntegerVectorEntry(1, BigInteger.TWO)));
        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    final class LongVectorEntryTest {
        @Test
        void new_should_throw_when_index_is_less_than_one() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new LongVectorEntry(0, 1L))
                    .withMessage("index must be greater than 0 but was 0")
                    .withNoCause();
        }

        @Test
        void withIndex_should_throw_when_index_is_less_than_one() {
            final var entry = new LongVectorEntry(1, 0L);

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> entry.withIndex(0))
                    .withMessage("index must be greater than 0 but was 0")
                    .withNoCause();
        }

        @Test
        void withIndex_should_succeed() {
            final var actual = new LongVectorEntry(1, 0L).withIndex(2);

            assertThat(actual).isEqualTo(new LongVectorEntry(2, 0L));
        }

        @Test
        void withValue_should_succeed() {
            final var entry = new LongVectorEntry(1, 0L);

            final var actual = entry.withValue(2L);

            assertThat(actual).isEqualTo(new LongVectorEntry(1, 2L));
        }

        @Test
        void toBigIntegerVectorEntry_should_succeed() {
            final var entry = new LongVectorEntry(1, 2L);

            final var actual = entry.toBigIntegerVectorEntry();

            assertThat(actual).isEqualTo(new BigIntegerVectorEntry(1, BigInteger.TWO));
        }
    }

    @Nested
    final class LongVectorBuilderTest {
        @ParameterizedTest
        @ValueSource(ints = {0, 2})
        void entry_should_throw_when_index_is_out_of_bounds(final int index) {
            final var builder = LongVector.builder(1);

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> builder.entry(index, 0L))
                    .withMessage("index must be greater than 0 and less than or equal to 1 but was " + index)
                    .withNoCause();
        }

        @Test
        void entry_should_throw_when_index_is_already_present() {
            final var builder = LongVector.builder(2).entry(1, 0L);

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> builder.entry(1, 1L))
                    .withMessage("index 1 is already present")
                    .withNoCause();
        }

        @Test
        void entry_should_succeed() {
            final var builder = LongVector.builder(1);

            final var actual = builder.entry(1, 2L);

            assertThat(actual.getEntries()).containsExactly(new LongVectorEntry(1, 2L));
            assertThat(actual).isSameAs(builder);
        }

        @Test
        void build_should_throw_when_sizes_are_different() {
            final var builder = LongVector.builder(2).entry(1, 0L);

            assertThatIllegalStateException()
                    .isThrownBy(() -> builder.build())
                    .withMessage("Expected 2 entries but was 1")
                    .withNoCause();
        }

        @Test
        void build_should_succeed() {
            final var builder = LongVector.builder(2).entry(2, 1L).entry(1, 2L);

            final var actual = builder.build();

            final var expected = new LongVector(List.of(new LongVectorEntry(1, 2L), new LongVectorEntry(2, 1L)));
            assertThat(actual).isEqualTo(expected);
        }
    }
}
