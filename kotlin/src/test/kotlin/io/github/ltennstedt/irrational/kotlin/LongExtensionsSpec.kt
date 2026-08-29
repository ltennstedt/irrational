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

class LongExtensionsSpec :
    FunSpec(
        {
            test("toLongRational should succeed") {
                0L.toLongRational() shouldBeEqual LongRational.ZERO
            }
            test("toBigRational should succeed") {
                0L.toBigRational() shouldBeEqual BigRational.ZERO
            }
            test("toLongGaussian should succeed") {
                0L.toLongGaussian() shouldBeEqual LongGaussian.ZERO
            }
            test("toBigGaussian should succeed") {
                0L.toBigGaussian() shouldBeEqual BigGaussian.ZERO
            }
            test("toDoubleComplex should succeed") {
                0L.toDoubleComplex() shouldBeEqual DoubleComplex.ZERO
            }
            test("toBigComplex should succeed") {
                0L.toBigComplex() shouldBeEqual BigComplex.ZERO
            }
            test("toDoubleQuaternion should succeed") {
                0L.toDoubleQuaternion() shouldBeEqual DoubleQuaternion.ZERO
            }
            test("toBigQuaternion should succeed") {
                0L.toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
            }
        },
    )
