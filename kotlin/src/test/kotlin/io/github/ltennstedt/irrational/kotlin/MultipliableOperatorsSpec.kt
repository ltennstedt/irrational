package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual

class MultipliableOperatorsSpec :
    ShouldSpec({
        context("times") {
            val rational = LongRational(1L, 2L)
            val multiplier = LongRational(3L, 4L)
            should("succeed") {
                rational * multiplier shouldBeEqual LongRational(3L, 8L)
            }
        }
    })
