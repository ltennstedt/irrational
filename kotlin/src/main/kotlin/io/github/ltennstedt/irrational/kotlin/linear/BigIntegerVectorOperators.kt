package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.BigIntegerVector
import io.github.ltennstedt.irrational.core.linear.BigIntegerVector.BigIntegerVectorEntry
import java.math.BigInteger

/** in operator */
public operator fun BigIntegerVector.contains(value: BigInteger): Boolean = containsValue(value)

/** in operator */
public operator fun BigIntegerVector.contains(entry: BigIntegerVectorEntry): Boolean = containsEntry(entry)

/** Square brackets operator */
public operator fun BigIntegerVector.get(index: Int): BigInteger = value(index)
