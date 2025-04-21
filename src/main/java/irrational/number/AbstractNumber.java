package irrational.number;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Base class for numbers
 *
 * @param <N> type of the number
 */
abstract class AbstractNumber<N extends AbstractNumber<N>> {
    /**
     * Indicates if this is invertible
     *
     * @return boolean
     */
    public abstract boolean isInvertible();

    /**
     * Indicates if this is not invertible
     *
     * @return boolean
     */
    public final boolean isNotInvertible() {
        return !isInvertible();
    }

    /**
     * Indicates if this is an element of the ring of integers
     *
     * @return boolean
     */
    public abstract boolean isInteger();

    /**
     * Indicates if this is not an element of the ring of integers
     *
     * @return boolean
     */
    public final boolean isNotInteger() {
        return !isInteger();
    }

    /**
     * Indicates if this is 0
     *
     * @return boolean
     */
    public abstract boolean isZero();

    /**
     * Indicates if this is 1
     *
     * @return boolean
     */
    public abstract boolean isOne();

    /**
     * Returns the negated number
     *
     * @return negated number
     */
    public abstract N negate();

    /**
     * Returns the absolute value
     *
     * @return absolute value
     */
    public abstract N abs();

    /**
     * Returns the sum of this and the summand
     *
     * @param summand summand
     * @return sum
     * @throws NullPointerException when summand is null
     */
    public abstract N add(N summand);

    /**
     * Returns the difference of this and the subtrahend
     *
     * @param subtrahend subtrahend
     * @return difference
     * @throws NullPointerException when subtrahend is null
     */
    public abstract N subtract(N subtrahend);

    /**
     * Returns the product of this and the multiplier
     *
     * @param multiplier multiplier
     * @return product
     * @throws NullPointerException when multiplier is null
     */
    public abstract N multiply(N multiplier);

    /**
     * Returns the quotient of this and the divisor
     *
     * @param divisor divisor
     * @return quotient
     * @throws NullPointerException when divisor is null
     * @throws IllegalArgumentException when divisor is not invertible
     */
    public abstract N divide(N divisor);

    /**
     * Returns the inverted number
     *
     * @return inverted number
     * @throws IllegalStateException when this is not invertible
     */
    public abstract N invert();

    /**
     * Returns this by the power of exponent
     *
     * @param exponent exponent
     * @return power
     */
    public abstract N power(int exponent);

    /**
     * Returns the byte value
     *
     * @return byte
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public final int toByte() {
        return toBigDecimal().byteValueExact();
    }

    /**
     * Returns the short value
     *
     * @return short
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public final int toShort() {
        return toBigDecimal().shortValueExact();
    }

    /**
     * Returns the int value
     *
     * @return int
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public final int toInt() {
        return toBigDecimal().intValueExact();
    }

    /**
     * Returns the long value
     *
     * @return long
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public final long toLong() {
        return toBigDecimal().longValueExact();
    }

    /**
     * Returns the float value
     *
     * @return float
     */
    public final float toFloat() {
        return toBigDecimal().floatValue();
    }

    /**
     * Returns the double value
     *
     * @return double
     */
    public final double toDouble() {
        return toBigDecimal().doubleValue();
    }

    /**
     * Returns this as {@link BigInteger}
     *
     * @return {@link BigInteger}
     * @throws ArithmeticException when an arithmetic overflow occurs
     */
    public final BigInteger toBigInteger() {
        return toBigDecimal().toBigIntegerExact();
    }

    /**
     * Returns this as {@link BigDecimal}
     *
     * @return {@link BigDecimal}
     */
    public abstract BigDecimal toBigDecimal();
}
