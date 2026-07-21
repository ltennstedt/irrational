package io.github.ltennstedt.irrational.core.numeric

/**
 * Unary + operator
 */
@Suppress("UNCHECKED_CAST")
public operator fun <N : Numeric<N, Q>, Q : Numeric<Q, Q>> Numeric<N, Q>.unaryPlus(): N = this as N

/**
 * Unary - operator
 */
public operator fun <N : Numeric<N, Q>, Q : Numeric<Q, Q>> Numeric<N, Q>.unaryMinus(): N = negate()

/**
 * Binary + operator
 */
public operator fun <N : Numeric<N, Q>, Q : Numeric<Q, Q>> Numeric<N, Q>.plus(summand: N): N = add(summand)

/**
 * Binary - operator
 */
public operator fun <N : Numeric<N, Q>, Q : Numeric<Q, Q>> Numeric<N, Q>.minus(subtrahend: N): N = subtract(subtrahend)

/**
 * Binary * operator
 */
public operator fun <N : Numeric<N, Q>, Q : Numeric<Q, Q>> Numeric<N, Q>.times(multiplier: N): N = multiply(multiplier)
