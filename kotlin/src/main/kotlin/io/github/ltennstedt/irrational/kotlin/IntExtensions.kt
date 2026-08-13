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

/** Extension method for converting [Int] to [LongRational] */
public fun Int.toLongRational(): LongRational = LongRational(toLong(), 1L)

/** Extension method for converting [Int] to [BigRational] */
public fun Int.toBigRational(): BigRational = BigRational(BigInteger.valueOf(toLong()), BigInteger.ONE)

/** Extension method for converting [Int] to [LongGaussian] */
public fun Int.toLongGaussian(): LongGaussian = LongGaussian(toLong(), 0L)

/** Extension method for converting [Int] to [BigGaussian] */
public fun Int.toBigGaussian(): BigGaussian = BigGaussian(BigInteger.valueOf(toLong()), BigInteger.ZERO)

/** Extension method for converting [Int] to [DoubleComplex] */
public fun Int.toDoubleComplex(): DoubleComplex = DoubleComplex(toDouble(), 0.0)

/** Extension method for converting [Int] to [BigComplex] */
public fun Int.toBigComplex(): BigComplex = BigComplex(BigDecimal.valueOf(toLong()), BigDecimal.ZERO)

/** Extension method for converting [Int] to [DoubleQuaternion] */
public fun Int.toDoubleQuaternion(): DoubleQuaternion = DoubleQuaternion(toDouble(), 0.0, 0.0, 0.0)

/** Extension method for converting [Int] to [BigQuaternion] */
public fun Int.toBigQuaternion(): BigQuaternion =
    BigQuaternion(BigDecimal.valueOf(toLong()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
