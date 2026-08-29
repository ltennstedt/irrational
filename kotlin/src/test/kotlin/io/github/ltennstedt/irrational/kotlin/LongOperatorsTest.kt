package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

class LongOperatorsTest :
    FunSpec(
        {
            test("times should succeed") {
                3L * LongVector(listOf(LongVectorEntry(1, 1L), LongVectorEntry(2, 2L))) shouldBeEqual
                    LongVector(
                        listOf(
                            LongVectorEntry(1, 3L),
                            LongVectorEntry(2, 6L),
                        ),
                    )
            }
        },
    )
