package io.github.ltennstedt.irrational.core.numeric

/**
 * Binary / operator
 */
public operator fun <N : PrimitiveNumeric<N, Q>, Q : PrimitiveNumeric<Q, Q>> PrimitiveNumeric<N, Q>.div(divisor: N): Q =
    divide(divisor)
