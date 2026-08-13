package io.github.ltennstedt.irrational.core.linear;

import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry;
import io.github.ltennstedt.irrational.core.util.Doubles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Immutable implementation of a {@link Vector} based on long
 *
 * @param entries entries
 */
public record LongVector(List<LongVectorEntry> entries) implements Vector<LongVector, LongVectorEntry> {
    /** Empty vector */
    public static final LongVector EMPTY = new LongVector(List.of());

    /**
     * Canonical constructor
     *
     * @param entries entries
     * @throws NullPointerException when entries is null
     * @throws NullPointerException when entries contains null
     * @throws IllegalArgumentException when indices are not a consecutive integer sequence
     */
    public LongVector {
        Objects.requireNonNull(entries, "entries");
        for (var i = 0; i < entries.size(); i++) {
            Objects.requireNonNull(entries.get(i), "entries must not contain null but found null at index " + i);
        }
        entries = entries.stream()
                .sorted(Comparator.comparingInt(LongVectorEntry::index))
                .toList();
        for (var i = 0; i < entries.size(); i++) {
            if (entries.get(i).index() != i + 1) {
                throw new IllegalArgumentException(
                        "indices must be a consecutive integer sequence starting with 1 and ending with entries.size "
                                + "but was "
                                + entries.stream().map(LongVectorEntry::index).toList());
            }
        }
    }

    /**
     * Returns a builder
     *
     * @param size size
     * @return builder
     * @throws IllegalArgumentException when size is negative
     */
    public static LongVectorBuilder builder(final int size) {
        return new LongVectorBuilder(size);
    }

    @Override
    public boolean isZero() {
        return entries.stream().map(LongVectorEntry::value).noneMatch(v -> v != 0L);
    }

    @Override
    public boolean isStandardBasisVector() {
        var found = false;
        for (final var entry : entries) {
            if (entry.value() != 0L) {
                if (entry.value() != 1L) {
                    return false;
                }
                found = true;
            }
        }
        return found;
    }

    @Override
    public boolean isUnitVector() {
        return Doubles.isNear(norm(), 1.0D);
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public int[] indices() {
        return entries.stream().mapToInt(LongVectorEntry::index).toArray();
    }

    /**
     * Returns the values
     *
     * @return values
     */
    public long[] values() {
        return entries.stream().mapToLong(LongVectorEntry::value).toArray();
    }

    @Override
    public boolean containsIndex(final int index) {
        return entries.stream().mapToInt(LongVectorEntry::index).anyMatch(i -> i == index);
    }

    /**
     * Returns if this contains value
     *
     * @param value value
     * @return boolean
     */
    public boolean containsValue(final long value) {
        return entries.stream().mapToLong(LongVectorEntry::value).anyMatch(v -> v == value);
    }

    @Override
    public boolean containsEntry(final LongVectorEntry entry) {
        Objects.requireNonNull(entry, "entry");
        return entries.contains(entry);
    }

    /**
     * Returns the value at the given index
     *
     * @param index index
     * @return value
     * @throws IllegalArgumentException when index is less than one or greater than size
     * @see #size()
     */
    public long value(final int index) {
        return entry(index).value();
    }

    /**
     * Returns the entry matching the given index
     *
     * @param index index
     * @return entry
     * @throws IllegalArgumentException when index is less than one or greater than size
     * @see #size()
     */
    public LongVectorEntry entry(final int index) {
        if (index < 1 || index > size()) {
            throw new IllegalArgumentException(
                    "index must be greater than 0 and less than or equal to %s but was %s".formatted(size(), index));
        }
        return entries.get(index - 1);
    }

    /**
     * @throws IllegalArgumentException when vector sizes differ
     * @throws ArithmeticException when an operation overflows
     */
    @Override
    public LongVector add(final LongVector summand) {
        Objects.requireNonNull(summand, "summand");
        if (size() != summand.size()) {
            throw new IllegalArgumentException(
                    "vectors must be of the same size but were %s and %s".formatted(size(), summand.size()));
        }
        return new LongVector(entries.stream()
                .map(e -> e.withValue(Math.addExact(e.value(), summand.value(e.index()))))
                .toList());
    }

    /**
     * @throws IllegalArgumentException when vector sizes differ
     * @throws ArithmeticException when an operation overflows
     */
    @Override
    public LongVector subtract(final LongVector subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        if (size() != subtrahend.size()) {
            throw new IllegalArgumentException(
                    "vectors must be of the same size but were %s and %s".formatted(size(), subtrahend.size()));
        }
        return new LongVector(entries.stream()
                .map(e -> e.withValue(Math.subtractExact(e.value(), subtrahend.value(e.index()))))
                .toList());
    }

    /**
     * Returns the scalar product of this and the scalar
     *
     * @param scalar scalar
     * @return scalar product
     * @throws ArithmeticException when an operation overflows
     */
    public LongVector scalarMultiply(final long scalar) {
        return new LongVector(entries.stream()
                .map(e -> e.withValue(Math.multiplyExact(scalar, e.value())))
                .toList());
    }

    /**
     * Returns the dot product of this and the other vector
     *
     * @param other other vector
     * @return dot product
     * @throws NullPointerException when other is null
     * @throws IllegalArgumentException when vector sizes differ
     * @throws ArithmeticException when an operation overflows
     */
    public long dotProduct(final LongVector other) {
        Objects.requireNonNull(other, "other");
        if (size() != other.size()) {
            throw new IllegalArgumentException(
                    "vectors must be of the same size but were %s and %s".formatted(size(), other.size()));
        }
        return entries.stream()
                .mapToLong(e -> Math.multiplyExact(e.value(), other.value(e.index())))
                .reduce(Math::addExact)
                .orElse(0L);
    }

    /** @throws ArithmeticException when an operation overflows */
    @Override
    public LongVector negate() {
        return new LongVector(entries.stream()
                .map(e -> e.withValue(Math.negateExact(e.value())))
                .toList());
    }

    /**
     * Returns the norm of this
     *
     * @return norm
     */
    public double norm() {
        return Math.sqrt(entries.stream()
                .mapToDouble(e -> (double) e.value() * e.value())
                .sum());
    }

    /**
     * Entry for {@link LongVector}
     *
     * @param index index
     * @param value value
     */
    public record LongVectorEntry(int index, long value) {
        /**
         * Canonical constructor
         *
         * @param index index
         * @param value value
         * @throws IllegalArgumentException when index is less than 1
         */
        public LongVectorEntry {
            if (index < 1) {
                throw new IllegalArgumentException("index must be greater than 0 but was " + index);
            }
        }

        /**
         * Returns a new {@link LongVectorEntry} with index newIndex
         *
         * @param newIndex new index
         * @return {@link LongVectorEntry}
         * @throws IllegalArgumentException when index is less than 1
         */
        public LongVectorEntry withIndex(final int newIndex) {
            return new LongVectorEntry(newIndex, value);
        }

        /**
         * Returns a new {@link LongVectorEntry} with value newValue
         *
         * @param newValue new value
         * @return {@link LongVectorEntry}
         */
        public LongVectorEntry withValue(final long newValue) {
            return new LongVectorEntry(index, newValue);
        }
    }

    /** Builder for {@link LongVector} */
    public static final class LongVectorBuilder {
        /** size */
        private final int size;

        /** present */
        private final boolean[] present;

        /** entries */
        private final List<LongVectorEntry> entries;

        private LongVectorBuilder(final int size) {
            if (size < 0) {
                throw new IllegalArgumentException("size must be greater than or equal to 0 but was " + size);
            }
            this.size = size;
            present = new boolean[size];
            entries = new ArrayList<>(size);
        }

        /**
         * Adds value at index
         *
         * @param index index
         * @param value value
         * @return this
         * @throws IllegalArgumentException when index is less than 1 or greater than size
         * @throws IllegalArgumentException when index is already present
         */
        public LongVectorBuilder entry(final int index, final long value) {
            if (index < 1 || index > size) {
                throw new IllegalArgumentException(
                        "index must be greater than 0 and less than or equal to %s but was %s".formatted(size, index));
            }
            if (present[index - 1]) {
                throw new IllegalArgumentException("index %s is already present".formatted(index));
            }
            present[index - 1] = true;
            entries.add(new LongVectorEntry(index, value));
            return this;
        }

        /**
         * Builds a {@link LongVector}
         *
         * @return vector
         * @throws IllegalStateException when number of entries and size are unequal
         */
        public LongVector build() {
            if (entries.size() != size) {
                throw new IllegalStateException("Expected %s entries but was %s".formatted(size, entries.size()));
            }
            return new LongVector(entries);
        }

        List<LongVectorEntry> getEntries() {
            return entries;
        }
    }
}
