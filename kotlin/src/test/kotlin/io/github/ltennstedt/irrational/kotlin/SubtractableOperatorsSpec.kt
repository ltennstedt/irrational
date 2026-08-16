package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class SubtractableOperatorsSpec :
    ShouldSpec({
        context("unaryMinus") {
            val rational = LongRational(1L, 2L)
            should("succeed") {
                -rational shouldBe LongRational(1L, -2L)
            }
        }
        context("minus") {
            val rational = LongRational(1L, 2L)
            val subtrahend = LongRational(3L, 4L)
            should("succeed") {
                rational - subtrahend shouldBeEqual LongRational(-1L, 4L)
            }
        }
    })
