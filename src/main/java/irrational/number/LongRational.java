package irrational.number;

import irrational.util.Longs;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/** Immutable implementation of a rational number */
public final class LongRational extends AbstractRational<LongRational> {
    /** Comparator */
    public static final Comparator<LongRational> COMPARATOR = Comparable::compareTo;

    /** 0 */
    public static final LongRational ZERO = new LongRational(0L, 1L);

    /** 1 */
    public static final LongRational ONE = new LongRational(1L, 1L);

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
    private LongRational(final long numerator, final long denominator) {
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
    public static LongRational of(final long numerator) {
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
    public static LongRational of(final long numerator, final long denominator) {
        if (numerator == 0L) {
            return ZERO;
        }
        if (numerator == denominator) {
            return ONE;
        }
        return new LongRational(numerator, denominator);
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
        final var quotient = Math.log(denominator) / Math.log(2);
        return (long) Math.ceil(quotient) == (long) Math.floor(quotient);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public boolean isProper() {
        return Math.absExact(numerator) < Math.absExact(denominator);
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
    public LongRational negate() {
        return new LongRational(Math.negateExact(numerator), denominator);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public LongRational abs() {
        return new LongRational(Math.absExact(numerator), Math.absExact(denominator));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public LongRational add(final LongRational summand) {
        Objects.requireNonNull(summand, "summand");
        return new LongRational(
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
    public LongRational subtract(final LongRational subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new LongRational(
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
    public LongRational multiply(final LongRational multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new LongRational(
                Math.multiplyExact(numerator, multiplier.getNumerator()),
                Math.multiplyExact(denominator, multiplier.getDenominator()));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public LongRational divide(final LongRational divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (divisor.isNotInvertible()) {
            throw new IllegalArgumentException("divisor must be invertible but was " + divisor);
        }
        return new LongRational(
                Math.multiplyExact(numerator, divisor.getNumerator()),
                Math.multiplyExact(denominator, divisor.getDenominator()));
    }

    @Override
    public LongRational invert() {
        if (isNotInvertible()) {
            throw new IllegalStateException("this must be invertible but was " + this);
        }
        return new LongRational(denominator, numerator);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    @Override
    public LongRational power(final int exponent) {
        return new LongRational((long) Longs.power(numerator, exponent), (long) Longs.power(denominator, exponent));
    }

    @Override
    public int signum() {
        return Long.signum(numerator);
    }

    @Override
    public LongRational min(final LongRational other) {
        Objects.requireNonNull(other, "other");
        return isLessThanOrEqualTo(other) ? this : other;
    }

    @Override
    public LongRational max(final LongRational other) {
        Objects.requireNonNull(other, "other");
        return isGreaterThanOrEqualTo(other) ? this : other;
    }

    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), MathContext.DECIMAL128);
    }

    @Override
    public int compareTo(final LongRational other) {
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
        if (!(object instanceof final LongRational that)) {
            return false;
        }
        return numerator == that.getNumerator() && denominator == that.getDenominator();
    }

    @Override
    public String toString() {
        return "LongRational{numerator=" + numerator + ", denominator=" + denominator + '}';
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
