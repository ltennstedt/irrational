package io.github.ltennstedt.irrational.core.numeric

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigDecimal

class BigDecimalExtensionsSpec: ShouldSpec({
    context("toBigComplex") {
        should("succeed") {
            BigDecimal.ZERO.toBigComplex() shouldBeEqual BigComplex.ZERO
        }
    }
    context("toBigQuaternion") {
        should("succeed") {
            BigDecimal.ZERO.toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
        }
    }
})
