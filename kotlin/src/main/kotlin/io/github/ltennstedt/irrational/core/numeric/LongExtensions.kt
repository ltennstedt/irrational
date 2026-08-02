package io.github.ltennstedt.irrational.core.numeric

import java.math.BigDecimal
import java.math.BigInteger

/**
 * Extension method for converting [Long] to [LongRational]
 */
public fun Long.toLongRational(): LongRational = LongRational(this, 1L)

/**
 * Extension method for converting [Long] to [BigRational]
 */
public fun Long.toBigRational(): BigRational = BigRational(BigInteger.valueOf(this), BigInteger.ONE)

/**
 * Extension method for converting [Long] to [LongGaussian]
 */
public fun Long.toLongGaussian(): LongGaussian = LongGaussian(this, 0L)

/**
 * Extension method for converting [Long] to [BigGaussian]
 */
public fun Long.toBigGaussian(): BigGaussian = BigGaussian(BigInteger.valueOf(this), BigInteger.ZERO)

/**
 * Extension method for converting [Long] to [DoubleComplex]
 */
public fun Long.toDoubleComplex(): DoubleComplex = DoubleComplex(toDouble(), 0.0)

/**
 * Extension method for converting [Long] to [BigComplex]
 */
public fun Long.toBigComplex(): BigComplex = BigComplex(BigDecimal.valueOf(this), BigDecimal.ZERO)

/**
 * Extension method for converting [Long] to [DoubleQuaternion]
 */
public fun Long.toDoubleQuaternion(): DoubleQuaternion = DoubleQuaternion(toDouble(), 0.0, 0.0, 0.0)

/**
 * Extension method for converting [Long] to [BigQuaternion]
 */
public fun Long.toBigQuaternion(): BigQuaternion =
    BigQuaternion(BigDecimal.valueOf(this), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
