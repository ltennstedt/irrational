package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeSameInstanceAs

class AdditiveOperatorsSpec :
    ShouldSpec({
        context("unaryPlus") {
            val rational = LongRational(1L, 2L)
            should("succeed") {
                +rational shouldBeSameInstanceAs rational
            }
        }
        context("plus") {
            val rational = LongRational(1L, 2L)
            val summand = LongRational(3L, 4L)
            should("succeed") {
                rational + summand shouldBeEqual LongRational(5L, 4L)
            }
        }
    })
