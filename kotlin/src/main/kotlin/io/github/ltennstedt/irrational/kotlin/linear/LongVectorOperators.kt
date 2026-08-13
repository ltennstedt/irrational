package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry

/** Unary + operator */
public operator fun LongVector.unaryPlus(): LongVector = this

/** Unary - operator */
public operator fun LongVector.unaryMinus(): LongVector = negate()

/** Binary + operator */
public operator fun LongVector.plus(summand: LongVector): LongVector = add(summand)

/** Binary - operator */
public operator fun LongVector.minus(subtrahend: LongVector): LongVector = subtract(subtrahend)

/** Binary * operator */
public operator fun LongVector.times(other: LongVector): Long = dotProduct(other)

/** in operator */
public operator fun LongVector.contains(value: Long): Boolean = containsValue(value)

/** in operator */
public operator fun LongVector.contains(entry: LongVectorEntry): Boolean = containsEntry(entry)

/** Square brackets operator */
public operator fun LongVector.get(index: Int): Long = value(index)
