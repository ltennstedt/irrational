package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.LongGaussian

/** component1 */
public operator fun LongGaussian.component1(): Long = real

/** component2 */
public operator fun LongGaussian.component2(): Long = imaginary
