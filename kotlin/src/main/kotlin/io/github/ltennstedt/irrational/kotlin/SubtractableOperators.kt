package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.Subtractable

/** Unary - operator */
public operator fun <S : Subtractable<S>> S.unaryMinus(): S = negate()

/** Binary - operator */
public operator fun <S : Subtractable<S>> S.minus(subtrahend: S): S = subtract(subtrahend)
