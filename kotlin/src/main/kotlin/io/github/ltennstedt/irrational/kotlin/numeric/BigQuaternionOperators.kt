package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import java.math.BigDecimal

/** component1 */
public operator fun BigQuaternion.component1(): BigDecimal = w

/** component2 */
public operator fun BigQuaternion.component2(): BigDecimal = x

/** component3 */
public operator fun BigQuaternion.component3(): BigDecimal = y

/** component4 */
public operator fun BigQuaternion.component4(): BigDecimal = z
