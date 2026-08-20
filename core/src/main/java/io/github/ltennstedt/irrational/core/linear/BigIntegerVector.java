package io.github.ltennstedt.irrational.core.linear;

import io.github.ltennstedt.irrational.core.linear.BigIntegerVector.BigIntegerVectorEntry;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Immutable implementation of a {@link Vector} based on long
 *
 * @param entries entries
 */
public record BigIntegerVector(List<BigIntegerVectorEntry> entries)
        implements Vector<BigIntegerVector, BigIntegerVectorEntry> {
    /** Empty vector */
    public static final BigIntegerVector EMPTY = new BigIntegerVector(List.of());

    /**
     * Canonical constructor
     *
     * @param entries entries
     * @throws NullPointerException when entries is null
     * @throws NullPointerException when entries contains null
     * @throws IllegalArgumentException when indices are not a consecutive integer sequence
     */
    public BigIntegerVector {
        Objects.requireNonNull(entries, "entries");
        for (var i = 0; i < entries.size(); i++) {
            Objects.requireNonNull(entries.get(i), "entries must not contain null but found null at index " + i);
        }
        entries = entries.stream()
                .sorted(Comparator.comparingInt(BigIntegerVectorEntry::index))
                .toList();
        for (var i = 0; i < entries.size(); i++) {
            if (entries.get(i).index() != i + 1) {
                throw new IllegalArgumentException(
                        "indices must be a consecutive integer sequence starting with 1 and ending with entries.size "
                                + "but was "
                                + entries.stream()
                                        .map(BigIntegerVectorEntry::index)
                                        .toList());
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
    public static BigIntegerVectorBuilder builder(final int size) {
        return new BigIntegerVectorBuilder(size);
    }

    @Override
    public boolean isZero() {
        return entries.stream().map(BigIntegerVectorEntry::value).allMatch(v -> v.equals(BigInteger.ZERO));
    }

    @Override
    public boolean isStandardBasisVector() {
        var foundZeros = 0;
        var foundValue = BigInteger.ZERO;
        for (final var entry : entries) {
            if (entry.value().equals(BigInteger.ZERO)) {
                foundZeros++;
            } else {
                foundValue = entry.value();
            }
        }
        return foundZeros == size() - 1 && foundValue.equals(BigInteger.ONE);
    }

    @Override
    public boolean isUnitVector() {
        var foundZeros = 0;
        for (final var entry : entries) {
            if (entry.value().equals(BigInteger.ZERO)) {
                foundZeros++;
            }
        }
        return foundZeros == size() - 1;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public int[] indices() {
        return entries.stream().mapToInt(BigIntegerVectorEntry::index).toArray();
    }

    /**
     * Returns the values
     *
     * @return values
     */
    public List<BigInteger> values() {
        return entries.stream().map(BigIntegerVectorEntry::value).toList();
    }

    /**
     * Returns the value at the given index
     *
     * @param index index
     * @return value
     * @throws IllegalArgumentException when index is less than one or greater than size
     * @see #size()
     */
    public BigInteger value(final int index) {
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
    public BigIntegerVectorEntry entry(final int index) {
        if (index < 1 || index > size()) {
            throw new IllegalArgumentException(
                    "index must be greater than 0 and less than or equal to %s but was %s".formatted(size(), index));
        }
        return entries.get(index - 1);
    }

    @Override
    public boolean containsIndex(final int index) {
        return entries.stream().mapToInt(BigIntegerVectorEntry::index).anyMatch(i -> i == index);
    }

    /**
     * Returns if this contains value
     *
     * @param value value
     * @return boolean
     * @throws NullPointerException when value is null
     */
    public boolean containsValue(final BigInteger value) {
        Objects.requireNonNull(value, "value");
        return entries.stream().map(BigIntegerVectorEntry::value).anyMatch(v -> v.equals(value));
    }

    @Override
    public boolean containsEntry(final BigIntegerVectorEntry entry) {
        Objects.requireNonNull(entry, "entry");
        return entries.contains(entry);
    }

    /** @throws IllegalArgumentException when vector sizes differ */
    @Override
    public BigIntegerVector add(final BigIntegerVector summand) {
        Objects.requireNonNull(summand, "summand");
        if (size() != summand.size()) {
            throw new IllegalArgumentException(
                    "vectors must be of the same size but were %s and %s".formatted(size(), summand.size()));
        }
        return new BigIntegerVector(entries.stream()
                .map(e -> e.withValue(e.value().add(summand.value(e.index()))))
                .toList());
    }

    /** @throws IllegalArgumentException when vector sizes differ */
    @Override
    public BigIntegerVector subtract(final BigIntegerVector subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        if (size() != subtrahend.size()) {
            throw new IllegalArgumentException(
                    "vectors must be of the same size but were %s and %s".formatted(size(), subtrahend.size()));
        }
        return new BigIntegerVector(entries.stream()
                .map(e -> e.withValue(e.value().subtract(subtrahend.value(e.index()))))
                .toList());
    }

    /**
     * Returns the scalar product of this and the scalar
     *
     * @param scalar scalar
     * @return scalar product
     * @throws NullPointerException when scalar is null
     */
    public BigIntegerVector scalarMultiply(final BigInteger scalar) {
        Objects.requireNonNull(scalar, "scalar");
        return new BigIntegerVector(entries.stream()
                .map(e -> e.withValue(scalar.multiply(e.value())))
                .toList());
    }

    /**
     * Returns the dot product of this and the other vector
     *
     * @param other other vector
     * @return dot product
     * @throws NullPointerException when other is null
     * @throws IllegalArgumentException when vector sizes differ
     */
    public BigInteger dotProduct(final BigIntegerVector other) {
        Objects.requireNonNull(other, "other");
        if (size() != other.size()) {
            throw new IllegalArgumentException(
                    "vectors must be of the same size but were %s and %s".formatted(size(), other.size()));
        }
        return entries.stream()
                .map(e -> e.value().multiply(other.value(e.index())))
                .reduce(BigInteger::add)
                .orElse(BigInteger.ZERO);
    }

    @Override
    public BigIntegerVector negate() {
        return new BigIntegerVector(
                entries.stream().map(e -> e.withValue(e.value().negate())).toList());
    }

    /**
     * Returns the norm of this
     *
     * @param mathContext {@link MathContext}
     * @return norm
     * @throws NullPointerException when mathContext is null
     */
    public BigDecimal norm(final MathContext mathContext) {
        Objects.requireNonNull(mathContext, "mathContext");
        return entries.stream()
                .map(BigIntegerVectorEntry::value)
                .map(BigDecimal::new)
                .map(v -> v.multiply(v))
                .reduce(BigDecimal::add)
                .map(d -> d.sqrt(mathContext))
                .orElse(BigDecimal.ZERO);
    }

    /**
     * Entry for {@link BigIntegerVector}
     *
     * @param index index
     * @param value value
     */
    public record BigIntegerVectorEntry(int index, BigInteger value) {
        /**
         * Canonical constructor
         *
         * @param index index
         * @param value value
         * @throws NullPointerException when value is null
         * @throws IllegalArgumentException when index is less than 1
         */
        public BigIntegerVectorEntry {
            Objects.requireNonNull(value, "value");
            if (index < 1) {
                throw new IllegalArgumentException("index must be greater than 0 but was " + index);
            }
        }

        /**
         * Returns a new {@link BigIntegerVectorEntry} with index newIndex
         *
         * @param newIndex new index
         * @return {@link BigIntegerVectorEntry}
         * @throws IllegalArgumentException when index is less than 1
         */
        public BigIntegerVectorEntry withIndex(final int newIndex) {
            return new BigIntegerVectorEntry(newIndex, value);
        }

        /**
         * Returns a new {@link BigIntegerVectorEntry} with value newValue
         *
         * @param newValue new value
         * @return {@link BigIntegerVectorEntry}
         * @throws NullPointerException when newValue is null
         */
        public BigIntegerVectorEntry withValue(final BigInteger newValue) {
            Objects.requireNonNull(newValue, "newValue");
            return new BigIntegerVectorEntry(index, newValue);
        }
    }

    /** Builder for {@link BigIntegerVector} */
    public static final class BigIntegerVectorBuilder {
        /** size */
        private final int size;

        /** present */
        private final boolean[] present;

        /** entries */
        private final List<BigIntegerVectorEntry> entries;

        private BigIntegerVectorBuilder(final int size) {
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
         * @throws NullPointerException when value is null
         * @throws IllegalArgumentException when index is less than 1 or greater than size
         * @throws IllegalArgumentException when index is already present
         */
        public BigIntegerVectorBuilder entry(final int index, final BigInteger value) {
            Objects.requireNonNull(value, "value");
            if (index < 1 || index > size) {
                throw new IllegalArgumentException(
                        "index must be greater than 0 and less than or equal to %s but was %s".formatted(size, index));
            }
            if (present[index - 1]) {
                throw new IllegalArgumentException("index %s is already present".formatted(index));
            }
            present[index - 1] = true;
            entries.add(new BigIntegerVectorEntry(index, value));
            return this;
        }

        /**
         * Builds a {@link BigIntegerVector}
         *
         * @return vector
         * @throws IllegalStateException when number of entries and size are unequal
         */
        public BigIntegerVector build() {
            if (entries.size() != size) {
                throw new IllegalStateException("Expected %s entries but was %s".formatted(size, entries.size()));
            }
            return new BigIntegerVector(entries);
        }

        List<BigIntegerVectorEntry> getEntries() {
            return entries;
        }
    }
}
