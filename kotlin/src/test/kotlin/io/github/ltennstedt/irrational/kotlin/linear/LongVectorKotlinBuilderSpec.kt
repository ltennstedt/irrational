package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual

class LongVectorKotlinBuilderSpec :
    ShouldSpec({
        context("longVector") {
            should("succeed") {
                longVector(size = 2) {
                    entry(index = 1, value = 3)
                    entry(index = 2, value = 4)
                } shouldBeEqual
                    LongVector
                        .builder(2)
                        .entry(1, 3L)
                        .entry(2, 4L)
                        .build()
            }
        }
    })
