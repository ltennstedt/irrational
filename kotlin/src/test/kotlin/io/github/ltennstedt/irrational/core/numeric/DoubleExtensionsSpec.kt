package io.github.ltennstedt.irrational.core.numeric

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigDecimal

class DoubleExtensionsSpec: ShouldSpec({
    context("toDoubleComplex") {
        should("succeed") {
            0.0.toDoubleComplex() shouldBeEqual DoubleComplex.ZERO
        }
    }
    context("toBigComplex") {
        should("succeed") {
            val actual = 1.0.toBigComplex()
            actual.real.compareTo(BigDecimal.ONE) shouldBeEqual 0
            actual.imaginary.compareTo(BigDecimal.ZERO) shouldBeEqual 0
        }
    }
    context("toDoubleQuaternion") {
        should("succeed") {
            0.0.toDoubleQuaternion() shouldBeEqual DoubleQuaternion.ZERO
        }
    }
    context("toBigQuaternion") {
        should("succeed") {
            val actual = 1.0.toBigQuaternion()
            actual.w.compareTo(BigDecimal.ONE) shouldBeEqual 0
            actual.x.compareTo(BigDecimal.ZERO) shouldBeEqual 0
            actual.y.compareTo(BigDecimal.ZERO) shouldBeEqual 0
            actual.z.compareTo(BigDecimal.ZERO) shouldBeEqual 0
        }
    }
})
