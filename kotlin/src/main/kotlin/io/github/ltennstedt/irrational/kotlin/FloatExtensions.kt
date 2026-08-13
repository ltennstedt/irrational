package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import java.math.BigDecimal

/** Extension method for converting [Float] to [DoubleComplex] */
public fun Float.toDoubleComplex(): DoubleComplex = DoubleComplex(toDouble(), 0.0)

/** Extension method for converting [Float] to [BigComplex] */
public fun Float.toBigComplex(): BigComplex = BigComplex(BigDecimal(toString()), BigDecimal.ZERO)

/** Extension method for converting [Float] to [DoubleQuaternion] */
public fun Float.toDoubleQuaternion(): DoubleQuaternion = DoubleQuaternion(toDouble(), 0.0, 0.0, 0.0)

/** Extension method for converting [Float] to [BigQuaternion] */
public fun Float.toBigQuaternion(): BigQuaternion =
    BigQuaternion(BigDecimal(toString()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
