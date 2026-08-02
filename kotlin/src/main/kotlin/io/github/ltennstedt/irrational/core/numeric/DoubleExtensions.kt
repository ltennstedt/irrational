package io.github.ltennstedt.irrational.core.numeric

import java.math.BigDecimal

/**
 * Extension method for converting [Double] to [DoubleComplex]
 */
public fun Double.toDoubleComplex(): DoubleComplex = DoubleComplex(this, 0.0)

/**
 * Extension method for converting [Double] to [BigComplex]
 */
public fun Double.toBigComplex(): BigComplex = BigComplex(BigDecimal(toString()), BigDecimal.ZERO)

/**
 * Extension method for converting [Double] to [DoubleQuaternion]
 */
public fun Double.toDoubleQuaternion(): DoubleQuaternion = DoubleQuaternion(this, 0.0, 0.0, 0.0)

/**
 * Extension method for converting [Double] to [BigQuaternion]
 */
public fun Double.toBigQuaternion(): BigQuaternion =
    BigQuaternion(BigDecimal(toString()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
