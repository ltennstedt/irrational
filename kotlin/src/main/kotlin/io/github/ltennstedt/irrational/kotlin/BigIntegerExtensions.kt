package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import java.math.BigDecimal
import java.math.BigInteger

/** Extension method for converting [BigInteger] to [BigRational] */
public fun BigInteger.toBigRational(): BigRational = BigRational(this, BigInteger.ONE)

/** Extension method for converting [BigInteger] to [BigGaussian] */
public fun BigInteger.toBigGaussian(): BigGaussian = BigGaussian(this, BigInteger.ZERO)

/** Extension method for converting [BigInteger] to [BigComplex] */
public fun BigInteger.toBigComplex(): BigComplex = BigComplex(BigDecimal(this), BigDecimal.ZERO)

/** Extension method for converting [BigInteger] to [BigQuaternion] */
public fun BigInteger.toBigQuaternion(): BigQuaternion =
    BigQuaternion(BigDecimal(this), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
