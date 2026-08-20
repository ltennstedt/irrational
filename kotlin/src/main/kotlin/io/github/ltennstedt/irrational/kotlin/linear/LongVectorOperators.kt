package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry

/** in operator */
public operator fun LongVector.contains(value: Long): Boolean = containsValue(value)

/** in operator */
public operator fun LongVector.contains(entry: LongVectorEntry): Boolean = containsEntry(entry)

/** Square brackets operator */
public operator fun LongVector.get(index: Int): Long = value(index)
