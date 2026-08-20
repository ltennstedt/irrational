package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.Additive

/** Unary + operator */
public operator fun <A : Additive<A>> A.unaryPlus(): A = this

/** Binary + operator */
public operator fun <A : Additive<A>> A.plus(summand: A): A = add(summand)
