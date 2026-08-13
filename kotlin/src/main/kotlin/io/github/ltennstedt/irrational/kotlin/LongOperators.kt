package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.linear.LongVector

/** Binary * operator */
public operator fun Long.times(vector: LongVector): LongVector = vector.scalarMultiply(this)
