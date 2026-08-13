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

/** Extension method for converting [Short] to [LongRational] */
public fun Short.toLongRational(): LongRational = LongRational(toLong(), 1L)

/** Extension method for converting [Short] to [BigRational] */
public fun Short.toBigRational(): BigRational = BigRational(BigInteger.valueOf(toLong()), BigInteger.ONE)

/** Extension method for converting [Short] to [LongGaussian] */
public fun Short.toLongGaussian(): LongGaussian = LongGaussian(toLong(), 0L)

/** Extension method for converting [Short] to [BigGaussian] */
public fun Short.toBigGaussian(): BigGaussian = BigGaussian(BigInteger.valueOf(toLong()), BigInteger.ZERO)

/** Extension method for converting [Short] to [DoubleComplex] */
public fun Short.toDoubleComplex(): DoubleComplex = DoubleComplex(toDouble(), 0.0)

/** Extension method for converting [Short] to [BigComplex] */
public fun Short.toBigComplex(): BigComplex = BigComplex(BigDecimal.valueOf(toLong()), BigDecimal.ZERO)

/** Extension method for converting [Short] to [DoubleQuaternion] */
public fun Short.toDoubleQuaternion(): DoubleQuaternion = DoubleQuaternion(toDouble(), 0.0, 0.0, 0.0)

/** Extension method for converting [Short] to [BigQuaternion] */
public fun Short.toBigQuaternion(): BigQuaternion =
    BigQuaternion(BigDecimal.valueOf(toLong()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
