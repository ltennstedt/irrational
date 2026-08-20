package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withShoulds
import io.kotest.matchers.equals.shouldBeEqual

class LongVectorOperatorsSpec :
    ShouldSpec(
        {
            context("get") {
                val vector = LongVector(listOf(LongVectorEntry(1, 3L), LongVectorEntry(2, 4L)))
                should("succeed") {
                    vector[1] shouldBeEqual 3L
                }
            }
            context("contains") {
                val vector = LongVector(listOf(LongVectorEntry(1, 0L)))
                withShoulds(1L to false, 0L to true) { (value, expected) -> (value in vector) shouldBeEqual expected }
                withShoulds(
                    LongVectorEntry(2, 0L) to false,
                    LongVectorEntry(1, 1L) to false,
                    LongVectorEntry(1, 0L) to true,
                ) { (entry, expected) -> (entry in vector) shouldBeEqual expected }
            }
        },
    )
