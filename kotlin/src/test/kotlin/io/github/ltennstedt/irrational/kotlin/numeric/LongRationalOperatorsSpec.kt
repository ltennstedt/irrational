package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.longs.shouldBeExactly

class LongRationalOperatorsSpec :
    FunSpec(
        {
            test("Destructuring should succeed") {
                shouldNotThrow<Throwable> {
                    listOf(LongRational.ZERO).map { (n, d) -> n + d }
                }
            }
            test("component1 should succeed") {
                LongRational.ZERO.component1() shouldBeExactly 0L
            }
            test("component2 should succeed") {
                LongRational.ZERO.component2() shouldBeExactly 1L
            }
        },
    )
