package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class LongVectorOperatorsSpec :
    FunSpec(
        {
            test("get should succeed") {
                val vector = LongVector(listOf(LongVectorEntry(1, 3L), LongVectorEntry(2, 4L)))
                vector[1] shouldBeEqual 3L
            }
            context("contains should succeed") {
                val vector = LongVector(listOf(LongVectorEntry(1, 0L)))
                withData(1L to false, 0L to true) { (value, expected) -> (value in vector) shouldBeEqual expected }
                withData(
                    LongVectorEntry(2, 0L) to false,
                    LongVectorEntry(1, 1L) to false,
                    LongVectorEntry(1, 0L) to true,
                ) { (entry, expected) -> (entry in vector) shouldBeEqual expected }
            }
        },
    )
