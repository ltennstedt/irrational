package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeSameInstanceAs

class AdditiveOperatorsSpec :
    FunSpec(
        {
            test("unaryPlus should succeed") {
                +LongRational.ZERO shouldBeSameInstanceAs LongRational.ZERO
            }
            test("plus should succeed") {
                LongRational(1L, 2L) + LongRational(3L, 4L) shouldBeEqual LongRational(5L, 4L)
            }
        },
    )
