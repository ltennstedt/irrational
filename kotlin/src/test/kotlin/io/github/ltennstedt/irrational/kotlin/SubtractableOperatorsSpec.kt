package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class SubtractableOperatorsSpec :
    FunSpec(
        {
            test("unaryMinus should succeed") {
                -LongRational.ONE shouldBe LongRational(-1L, 1L)
            }
            test("minus should succeed") {
                LongRational(1L, 2L) - LongRational(3L, 4L) shouldBeEqual LongRational(-1L, 4L)
            }
        },
    )
