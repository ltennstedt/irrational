package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.Multipliable

/** Binary * operator */
public operator fun <M : Multipliable<M>> M.times(multiplier: M): M = multiply(multiplier)
