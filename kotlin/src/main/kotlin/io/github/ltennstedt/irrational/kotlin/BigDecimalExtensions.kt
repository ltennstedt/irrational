package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import java.math.BigDecimal

/** Extension method for converting [BigDecimal] to [BigComplex] */
public fun BigDecimal.toBigComplex(): BigComplex = BigComplex(this, BigDecimal.ZERO)

/** Extension method for converting [BigDecimal] to [BigQuaternion] */
public fun BigDecimal.toBigQuaternion(): BigQuaternion =
    BigQuaternion(this, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
