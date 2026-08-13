package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import java.math.BigDecimal

/** component1 */
public operator fun BigComplex.component1(): BigDecimal = real

/** component2 */
public operator fun BigComplex.component2(): BigDecimal = imaginary
