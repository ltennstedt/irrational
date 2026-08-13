package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual

class LongOperatorsTest :
    ShouldSpec(
        {
            context("times")
            val vector = LongVector(listOf(LongVectorEntry(1, 1L), LongVectorEntry(2, 2L)))
            should("succeed") {
                val expected = LongVector(listOf(LongVectorEntry(1, 3L), LongVectorEntry(2, 6L)))
                3L * vector shouldBeEqual expected
            }
        },
    )
