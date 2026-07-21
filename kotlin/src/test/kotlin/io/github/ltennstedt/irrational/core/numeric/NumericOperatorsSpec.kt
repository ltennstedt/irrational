package io.github.ltennstedt.irrational.core.numeric

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeSameInstanceAs

class NumericOperatorsSpec :
    ShouldSpec({
        val fraction1 = LongRational(1L, 2L)
        val fraction2 = LongRational(3L, 4L)
        context("unaryPlus") {
            should("succeed") {
                +fraction1 shouldBeSameInstanceAs fraction1
            }
        }
        context("unaryMinus") {
            should("succeed") {
                -fraction1 shouldBeEqual LongRational(-1L, 2L)
            }
        }
        context("plus") {
            should("succeed") {
                fraction1 + fraction2 shouldBeEqual LongRational(5L, 4L)
            }
        }
        context("minus") {
            should("succeed") {
                fraction1 - fraction2 shouldBeEqual LongRational(-1L, 4L)
            }
        }
        context("times") {
            should("succeed") {
                fraction1 * fraction2 shouldBeEqual LongRational(3L, 8L)
            }
        }
    })
