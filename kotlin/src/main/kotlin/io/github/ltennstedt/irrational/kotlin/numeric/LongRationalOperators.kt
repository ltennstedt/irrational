package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.LongRational

/** component1 */
public operator fun LongRational.component1(): Long = numerator

/** component2 */
public operator fun LongRational.component2(): Long = denominator
