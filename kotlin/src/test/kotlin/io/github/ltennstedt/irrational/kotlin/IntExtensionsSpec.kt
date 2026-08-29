package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import io.github.ltennstedt.irrational.core.numeric.LongGaussian
import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

class IntExtensionsSpec :
    FunSpec(
        {
            test("toLongRational should succeed") {
                0.toLongRational() shouldBeEqual LongRational.ZERO
            }
            test("toBigRational should succeed") {
                0.toBigRational() shouldBeEqual BigRational.ZERO
            }
            test("toLongGaussian should succeed") {
                0.toLongGaussian() shouldBeEqual LongGaussian.ZERO
            }
            test("toBigGaussian should succeed") {
                0.toBigGaussian() shouldBeEqual BigGaussian.ZERO
            }
            test("toDoubleComplex should succeed") {
                0.toDoubleComplex() shouldBeEqual DoubleComplex.ZERO
            }
            test("toBigComplex should succeed") {
                0.toBigComplex() shouldBeEqual BigComplex.ZERO
            }
            test("toDoubleQuaternion should succeed") {
                0.toDoubleQuaternion() shouldBeEqual DoubleQuaternion.ZERO
            }
            test("toBigQuaternion should succeed") {
                0.toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
            }
        },
    )
