package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion

/** component1 */
public operator fun DoubleQuaternion.component1(): Double = w

/** component2 */
public operator fun DoubleQuaternion.component2(): Double = x

/** component3 */
public operator fun DoubleQuaternion.component3(): Double = y

/** component4 */
public operator fun DoubleQuaternion.component4(): Double = z
