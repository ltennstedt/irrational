package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

class MultipliableOperatorsSpec :
    FunSpec(
        {
            test("times should succeed") {
                LongRational(1L, 2L) * LongRational(3L, 4L) shouldBeEqual LongRational(3L, 8L)
            }
        },
    )
