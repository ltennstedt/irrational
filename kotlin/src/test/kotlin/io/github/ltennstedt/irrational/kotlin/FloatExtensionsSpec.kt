package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigDecimal

class FloatExtensionsSpec :
    ShouldSpec({
        context("toDoubleComplex") {
            should("succeed") {
                0.0F.toDoubleComplex() shouldBeEqual DoubleComplex.ZERO
            }
        }
        context("toBigComplex") {
            should("succeed") {
                val actual = 1.0F.toBigComplex()
                actual.real.compareTo(BigDecimal.ONE) shouldBeEqual 0
                actual.imaginary.compareTo(BigDecimal.ZERO) shouldBeEqual 0
            }
        }
        context("toDoubleQuaternion") {
            should("succeed") {
                0.0F.toDoubleQuaternion() shouldBeEqual DoubleQuaternion.ZERO
            }
        }
        context("toBigQuaternion") {
            should("succeed") {
                val actual = 1.0F.toBigQuaternion()
                actual.w.compareTo(BigDecimal.ONE) shouldBeEqual 0
                actual.x.compareTo(BigDecimal.ZERO) shouldBeEqual 0
                actual.y.compareTo(BigDecimal.ZERO) shouldBeEqual 0
                actual.z.compareTo(BigDecimal.ZERO) shouldBeEqual 0
            }
        }
    })
