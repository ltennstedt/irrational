package io.github.ltennstedt.irrational.core.numeric

import java.math.BigInteger

/**
 * component1
 */
public operator fun BigRational.component1(): BigInteger = numerator

/**
 * component2
 */
public operator fun BigRational.component2(): BigInteger = denominator

/**
 * Binary / operator
 */
public operator fun BigRational.div(divisor: BigRational): BigRational = divide(divisor)
