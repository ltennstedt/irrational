package sliderule.core.number;

import org.jspecify.annotations.NonNull;

/**
 * Base class for rational numbers
 *
 * @param <R> type of the rational number
 */
abstract class AbstractRational<R extends AbstractRational<R>> extends AbstractNumber<R> {
    /**
     * Indicates if this rational number is a unit one
     *
     * @return boolean
     */
    public abstract boolean isUnit();

    /**
     * Indicates if this rational number is not a unit one
     *
     * @return boolean
     */
    public final boolean isNotUnit() {
        return !isUnit();
    }

    /**
     * Indicates if this rational number is dyadic
     *
     * @return boolean
     */
    public abstract boolean isDyadic();

    /**
     * Indicates if this rational number is not dyadic
     *
     * @return boolean
     */
    public final boolean isNotDyadic() {
        return !isDyadic();
    }

    /**
     * Indicates if this rational number is proper
     *
     * @return boolean
     */
    public abstract boolean isProper();

    /**
     * Indicates if this rational number is improper
     *
     * @return boolean
     */
    public final boolean isImproper() {
        return !isProper();
    }

    /**
     * Indicates if this rational has an invisible denominator
     *
     * @return boolean
     */
    public abstract boolean hasInvisibleDenominator();

    /**
     * Indicates if this rational does not have an invisible denominator
     *
     * @return boolean
     */
    public final boolean doesNotHaveInvisibleDenominator() {
        return !hasInvisibleDenominator();
    }

    /**
     * Returns the signum
     *
     * @return signum
     */
    public abstract int signum();

    /**
     * Returns the minimum
     *
     * @param other other
     * @return boolean
     */
    public abstract @NonNull R min(@NonNull R other);

    /**
     * Returns the maximum
     *
     * @param other other
     * @return boolean
     */
    public abstract @NonNull R max(@NonNull R other);

    /**
     * Returns the canonical representation
     *
     * @return canonical representation
     */
    public abstract @NonNull R canonical();
}
