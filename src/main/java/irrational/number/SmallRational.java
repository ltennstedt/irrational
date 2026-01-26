package irrational.number;

import irrational.util.Longs;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/** Immutable implementation of a rational number based on long */
public final class SmallRational implements Rational<SmallRational> {
    /** Comparator */
    public static final Comparator<SmallRational> COMPARATOR = Comparable::compareTo;

    /** 0 */
    public static final SmallRational ZERO = new SmallRational(0L, 1L);

    /** 1 */
    public static final SmallRational ONE = new SmallRational(1L, 1L);

    /** Numerator */
    private final long numerator;

    /** Denominator */
    private final long denominator;

    /**
     * All arguments constructor
     *
     * @param numerator numerator
     * @param denominator denominator
     * @throws IllegalArgumentException when denominator is 0
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    private SmallRational(final long numerator, final long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator must not be 0 but was " + denominator);
        }
        final var sign = denominator < 0L ? -1L : 1L;
        final var gcd = Longs.gcd(numerator, denominator);
        this.numerator = Math.multiplyExact(sign, numerator) / gcd;
        this.denominator = Math.multiplyExact(sign, denominator) / gcd;
    }

    /**
     * Static factory method
     *
     * @param numerator numerator
     * @return LongRational
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public static SmallRational of(final long numerator) {
        return of(numerator, 1L);
    }

    /**
     * Static factory method
     *
     * @param numerator numerator
     * @param denominator denominator
     * @return LongRational
     * @throws IllegalArgumentException when denominator is 0
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public static SmallRational of(final long numerator, final long denominator) {
        if (numerator == 0L) {
            return ZERO;
        }
        if (numerator == denominator) {
            return ONE;
        }
        return new SmallRational(numerator, denominator);
    }

    @Override
    public boolean isInvertible() {
        return numerator != 0L;
    }

    @Override
    public boolean isInteger() {
        return denominator == 1L;
    }

    @Override
    public boolean isZero() {
        return this == ZERO;
    }

    @Override
    public boolean isOne() {
        return this == ONE;
    }

    @Override
    public boolean isUnit() {
        return numerator == 1L;
    }

    @Override
    public boolean isDyadic() {
        return (denominator & Math.decrementExact(denominator)) == 0L;
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public boolean isProper() {
        return Math.absExact(numerator) < denominator;
    }

    @Override
    public boolean isPositive() {
        return numerator > 0L;
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public SmallRational negate() {
        return new SmallRational(Math.negateExact(numerator), denominator);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public SmallRational abs() {
        return new SmallRational(Math.absExact(numerator), Math.absExact(denominator));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public SmallRational add(final SmallRational summand) {
        Objects.requireNonNull(summand, "summand");
        return new SmallRational(
                Math.addExact(
                        Math.multiplyExact(summand.getDenominator(), numerator),
                        Math.multiplyExact(denominator, summand.getNumerator())),
                Math.multiplyExact(denominator, summand.getDenominator()));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public SmallRational subtract(final SmallRational subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new SmallRational(
                Math.subtractExact(
                        Math.multiplyExact(subtrahend.getDenominator(), numerator),
                        Math.multiplyExact(denominator, subtrahend.getNumerator())),
                Math.multiplyExact(denominator, subtrahend.getDenominator()));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public SmallRational multiply(final SmallRational multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new SmallRational(
                Math.multiplyExact(numerator, multiplier.getNumerator()),
                Math.multiplyExact(denominator, multiplier.getDenominator()));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public SmallRational divide(final SmallRational divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (divisor.isNotInvertible()) {
            throw new IllegalArgumentException("divisor must be invertible but was " + divisor);
        }
        return new SmallRational(
                Math.multiplyExact(numerator, divisor.getNumerator()),
                Math.multiplyExact(denominator, divisor.getDenominator()));
    }

    @Override
    public SmallRational invert() {
        if (isNotInvertible()) {
            throw new IllegalStateException("this must be invertible but was " + this);
        }
        return new SmallRational(denominator, numerator);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public SmallRational power(final int exponent) {
        return new SmallRational((long) Longs.power(numerator, exponent), (long) Longs.power(denominator, exponent));
    }

    @Override
    public int signum() {
        return Long.signum(numerator);
    }

    @Override
    public SmallRational min(final SmallRational other) {
        Objects.requireNonNull(other, "other");
        return isLessThanOrEqualTo(other) ? this : other;
    }

    @Override
    public SmallRational max(final SmallRational other) {
        Objects.requireNonNull(other, "other");
        return isGreaterThanOrEqualTo(other) ? this : other;
    }

    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), MathContext.DECIMAL128);
    }

    @Override
    public int compareTo(final SmallRational other) {
        Objects.requireNonNull(other, "other");
        return Long.compare(other.getDenominator() * numerator, denominator * other.getNumerator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public boolean equals(final @Nullable Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof final SmallRational that)) {
            return false;
        }
        return numerator == that.getNumerator() && denominator == that.getDenominator();
    }

    @Override
    public String toString() {
        return "LongRational{numerator=%s, denominator=%s}".formatted(numerator, denominator);
    }

    /**
     * Numerator
     *
     * @return numerator
     */
    public long getNumerator() {
        return numerator;
    }

    /**
     * Denominator
     *
     * @return denominator
     */
    public long getDenominator() {
        return denominator;
    }
}
