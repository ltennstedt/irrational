package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldEqual

class LongVectorKotlinBuilderSpec :
    FunSpec(
        {
            test("longVector should throw when size is negative") {
                shouldThrow<IllegalArgumentException> {
                    longVector(size = -1) { }
                }.message shouldEqual "size must be greater than or equal to 0 but was -1"
            }
            test("longVector should succeed") {
                longVector(size = 2) {
                    entry(index = 1, value = 3L)
                    entry(index = 2, value = 4L)
                } shouldBeEqual
                    LongVector
                        .builder(2)
                        .entry(1, 3L)
                        .entry(2, 4L)
                        .build()
            }
        },
    )
