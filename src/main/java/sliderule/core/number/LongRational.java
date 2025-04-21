package sliderule.core.number;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import sliderule.core.util.Longs;

/** Immutable implementation of a rational number */
public final class LongRational extends AbstractRational<LongRational> {
    /** Comparator */
    public static final @NonNull Comparator<LongRational> COMPARATOR = Comparable::compareTo;

    /** 0 */
    public static final @NonNull LongRational ZERO = new LongRational(0, 1);

    /** 1 */
    public static final @NonNull LongRational ONE = new LongRational(1, 1);

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
     */
    public LongRational(final long numerator, final long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Expected denominator != 0 but denominator=" + 0);
        }
        final var absNumerator = Math.abs(numerator);
        this.numerator = Long.signum(numerator) * Long.signum(denominator) < 0 ? -absNumerator : absNumerator;
        this.denominator = Math.abs(denominator);
    }

    @Override
    public boolean isInvertible() {
        return numerator != 0;
    }

    @Override
    public boolean isUnit() {
        return numerator == 1;
    }

    @Override
    public boolean isDyadic() {
        final var quotient = Math.log(denominator) / Math.log(2);
        return (long) Math.ceil(quotient) == (long) Math.floor(quotient);
    }

    @Override
    public boolean isProper() {
        return Math.abs(numerator) < Math.abs(denominator);
    }

    /**
     * Indicates if this rational number is proper
     *
     * @return boolean
     * @throws ArithmeticException when an operation overflows
     */
    public boolean isProperExact() {
        return Math.absExact(numerator) < Math.absExact(denominator);
    }

    /**
     * Indicates if this rational number is improper
     *
     * @return boolean
     * @throws ArithmeticException when an operation overflows
     */
    public boolean isImproperExact() {
        return !isProperExact();
    }

    @Override
    public boolean hasInvisibleDenominator() {
        return denominator == 1;
    }

    @Override
    public LongRational negate() {
        return new LongRational(-numerator, denominator);
    }

    /**
     * Returns the negated number
     *
     * @return negated number
     * @throws ArithmeticException when an operation overflows
     */
    public LongRational negateExact() {
        return new LongRational(Math.negateExact(numerator), denominator);
    }

    @Override
    public @NonNull LongRational abs() {
        return new LongRational(Math.abs(numerator), Math.abs(denominator));
    }

    /**
     * Returns the absolute value
     *
     * @return absolute value
     * @throws ArithmeticException when an operation overflows
     */
    public @NonNull LongRational absExact() {
        return new LongRational(Math.absExact(numerator), Math.absExact(denominator));
    }

    @Override
    public @NonNull LongRational add(final @NonNull LongRational summand) {
        Objects.requireNonNull(summand, "summand");
        return new LongRational(
                summand.getDenominator() * numerator + denominator * summand.getNumerator(),
                denominator * summand.getDenominator());
    }

    /**
     * Returns the sum of this number and the summand
     *
     * @param summand summand
     * @return sum
     * @throws NullPointerException when summand is null
     * @throws ArithmeticException when an operation overflows
     */
    public @NonNull LongRational addExact(final @NonNull LongRational summand) {
        Objects.requireNonNull(summand, "summand");
        return new LongRational(
                Math.addExact(
                        Math.multiplyExact(summand.getDenominator(), numerator),
                        Math.multiplyExact(denominator, summand.getNumerator())),
                Math.multiplyExact(denominator, summand.getDenominator()));
    }

    @Override
    public @NonNull LongRational subtract(final @NonNull LongRational subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new LongRational(
                subtrahend.getDenominator() * numerator - denominator * subtrahend.getNumerator(),
                denominator * subtrahend.getDenominator());
    }

    /**
     * Returns the difference of this number and the subtrahend
     *
     * @param subtrahend subtrahend
     * @return difference
     * @throws NullPointerException when subtrahend is null
     * @throws ArithmeticException when an operation overflows
     */
    public @NonNull LongRational subtractExact(final @NonNull LongRational subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend");
        return new LongRational(
                Math.subtractExact(
                        Math.multiplyExact(subtrahend.getDenominator(), numerator),
                        Math.multiplyExact(denominator, subtrahend.getNumerator())),
                Math.multiplyExact(denominator, subtrahend.getDenominator()));
    }

    @Override
    public @NonNull LongRational multiply(final @NonNull LongRational multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new LongRational(numerator * multiplier.getNumerator(), denominator * multiplier.getDenominator());
    }

    /**
     * Returns the product of this number and the multiplier
     *
     * @param multiplier multiplier
     * @return product
     * @throws NullPointerException when multiplier is null
     * @throws ArithmeticException when an operation overflows
     */
    public @NonNull LongRational multiplyExact(final @NonNull LongRational multiplier) {
        Objects.requireNonNull(multiplier, "multiplier");
        return new LongRational(
                Math.multiplyExact(numerator, multiplier.getNumerator()),
                Math.multiplyExact(denominator, multiplier.getDenominator()));
    }

    @Override
    public @NonNull LongRational divide(final @NonNull LongRational divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (divisor.isNotInvertible()) {
            throw new IllegalArgumentException("divisor must be invertible but is " + divisor);
        }
        return new LongRational(numerator * divisor.getNumerator(), denominator * divisor.getDenominator());
    }

    /**
     * Returns the quotient of this number and the divisor
     *
     * @param divisor divisor
     * @return quotient
     * @throws NullPointerException when divisor is null
     * @throws IllegalArgumentException when divisor is not invertible
     * @throws ArithmeticException when an operation overflows
     */
    public @NonNull LongRational divideExact(final @NonNull LongRational divisor) {
        Objects.requireNonNull(divisor, "divisor");
        if (divisor.isNotInvertible()) {
            throw new IllegalArgumentException("divisor must be invertible but is " + divisor);
        }
        return new LongRational(
                Math.multiplyExact(numerator, divisor.getNumerator()),
                Math.multiplyExact(denominator, divisor.getDenominator()));
    }

    @Override
    public @NonNull LongRational invert() {
        if (isNotInvertible()) {
            throw new IllegalStateException("must be invertible but is " + this);
        }
        return new LongRational(denominator, numerator);
    }

    @Override
    public @NonNull LongRational power(final int exponent) {
        return new LongRational((long) Longs.power(numerator, exponent), (long) Longs.power(denominator, exponent));
    }

    /**
     * Returns this by the power of exponent
     *
     * @param exponent exponent
     * @return power
     * @throws ArithmeticException when an operation overflows
     */
    public @NonNull LongRational powerExact(final int exponent) {
        return new LongRational(
                (long) Longs.powerExact(numerator, exponent), (long) Longs.powerExact(denominator, exponent));
    }

    @Override
    public int signum() {
        return Long.signum(numerator);
    }

    @Override
    public @NonNull LongRational min(final @NonNull LongRational other) {
        Objects.requireNonNull(other, "other");
        return isLessThanOrEqualTo(other) ? this : other;
    }

    @Override
    public @NonNull LongRational max(final @NonNull LongRational other) {
        Objects.requireNonNull(other, "other");
        return isGreaterThanOrEqualTo(other) ? this : other;
    }

    @Override
    public @NonNull LongRational canonical() {
        final var gcd = Longs.gcd(numerator, denominator);
        return new LongRational(numerator / gcd, denominator / gcd);
    }

    /**
     * Returns the canonical representation
     *
     * @return canonical representation
     * @throws ArithmeticException when an operation overflows
     */
    public @NonNull LongRational canonicalExact() {
        final var gcd = Longs.gcdExact(numerator, denominator);
        return new LongRational(numerator / gcd, denominator / gcd);
    }

    @Override
    public @NonNull BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), MathContext.DECIMAL128);
    }

    @Override
    public int compareTo(final @NonNull LongRational other) {
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
    public @NonNull String toString() {
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
