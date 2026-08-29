package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigDecimal

class BigDecimalExtensionsSpec :
    FunSpec(
        {
            test("toBigComplex should succeed") {
                BigDecimal.ZERO.toBigComplex() shouldBeEqual BigComplex.ZERO
            }
            test("toBigQuaternion should succeed") {
                BigDecimal.ZERO.toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
            }
        },
    )
