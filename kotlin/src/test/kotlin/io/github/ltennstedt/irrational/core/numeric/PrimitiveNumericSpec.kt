package io.github.ltennstedt.irrational.core.numeric

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual

class PrimitiveNumericSpec :
    ShouldSpec({
        context("div") {
            should("succeed") {
                DoubleComplex(1.0, 3.0) / DoubleComplex(2.0, 1.0) shouldBeEqual DoubleComplex(1.0, 1.0)
            }
        }
    })
