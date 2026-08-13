package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import io.github.ltennstedt.irrational.core.numeric.LongGaussian
import io.github.ltennstedt.irrational.core.numeric.LongRational
import java.math.BigDecimal
import java.math.BigInteger

/** Extension method for converting [Byte] to [LongRational] */
public fun Byte.toLongRational(): LongRational = LongRational(toLong(), 1L)

/** Extension method for converting [Byte] to [BigRational] */
public fun Byte.toBigRational(): BigRational = BigRational(BigInteger.valueOf(toLong()), BigInteger.ONE)

/** Extension method for converting [Byte] to [LongGaussian] */
public fun Byte.toLongGaussian(): LongGaussian = LongGaussian(toLong(), 0L)

/** Extension method for converting [Byte] to [BigGaussian] */
public fun Byte.toBigGaussian(): BigGaussian = BigGaussian(BigInteger.valueOf(toLong()), BigInteger.ZERO)

/** Extension method for converting [Byte] to [DoubleComplex] */
public fun Byte.toDoubleComplex(): DoubleComplex = DoubleComplex(toDouble(), 0.0)

/** Extension method for converting [Byte] to [BigComplex] */
public fun Byte.toBigComplex(): BigComplex = BigComplex(BigDecimal.valueOf(toLong()), BigDecimal.ZERO)

/** Extension method for converting [Byte] to [DoubleQuaternion] */
public fun Byte.toDoubleQuaternion(): DoubleQuaternion = DoubleQuaternion(toDouble(), 0.0, 0.0, 0.0)

/** Extension method for converting [Byte] to [BigQuaternion] */
public fun Byte.toBigQuaternion(): BigQuaternion =
    BigQuaternion(BigDecimal.valueOf(toLong()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
