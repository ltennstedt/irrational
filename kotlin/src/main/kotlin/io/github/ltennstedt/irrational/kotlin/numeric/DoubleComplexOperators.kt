package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.DoubleComplex

/** component1 */
public operator fun DoubleComplex.component1(): Double = real

/** component2 */
public operator fun DoubleComplex.component2(): Double = imaginary
