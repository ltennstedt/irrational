package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigDecimal

class DoubleExtensionsSpec :
    FunSpec(
        {
            test("toDoubleComplex should succeed") {
                0.0.toDoubleComplex() shouldBeEqual DoubleComplex.ZERO
            }
            test("toBigComplex shouldsucceed") {
                val actual = 1.0.toBigComplex()
                actual.real.compareTo(BigDecimal.ONE) shouldBeEqual 0
                actual.imaginary.compareTo(BigDecimal.ZERO) shouldBeEqual 0
            }
            test("toDoubleQuaternion should succeed") {
                0.0.toDoubleQuaternion() shouldBeEqual DoubleQuaternion.ZERO
            }
            test("toBigQuaternion should succeed") {
                val actual = 1.0.toBigQuaternion()
                actual.w.compareTo(BigDecimal.ONE) shouldBeEqual 0
                actual.x.compareTo(BigDecimal.ZERO) shouldBeEqual 0
                actual.y.compareTo(BigDecimal.ZERO) shouldBeEqual 0
                actual.z.compareTo(BigDecimal.ZERO) shouldBeEqual 0
            }
        },
    )
