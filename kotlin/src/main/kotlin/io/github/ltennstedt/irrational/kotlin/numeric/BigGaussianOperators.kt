package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import java.math.BigInteger

/** component1 */
public operator fun BigGaussian.component1(): BigInteger = real

/** component2 */
public operator fun BigGaussian.component2(): BigInteger = imaginary
