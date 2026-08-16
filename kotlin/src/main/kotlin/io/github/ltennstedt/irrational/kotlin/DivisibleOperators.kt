package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.Divisible

/** Binary / operator */
public operator fun <D : Divisible<D, Q>, Q : Divisible<Q, Q>> D.div(divisor: D): Q = divide(divisor)
