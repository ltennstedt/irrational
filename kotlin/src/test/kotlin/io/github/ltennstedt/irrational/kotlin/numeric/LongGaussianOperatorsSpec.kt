package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.LongGaussian
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.longs.shouldBeExactly

class LongGaussianOperatorsSpec :
    FunSpec(
        {
            test("Destructuring should succeed") {
                shouldNotThrow<Throwable> {
                    listOf(LongGaussian.ZERO).map { (r, i) -> r + i }
                }
            }
            test("component1 should succeed") {
                LongGaussian.ONE.component1() shouldBeExactly 1L
            }
            test("component2 should succeed") {
                LongGaussian.ONE.component2() shouldBeExactly 0L
            }
        },
    )
