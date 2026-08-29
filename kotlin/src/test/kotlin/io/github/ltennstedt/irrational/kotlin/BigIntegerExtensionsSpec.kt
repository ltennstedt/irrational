package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigInteger

class BigIntegerExtensionsSpec :
    FunSpec(
        {
            test("toBigRational should succeed") {
                BigInteger.ZERO.toBigRational() shouldBeEqual BigRational.ZERO
            }
            test("toBigGaussian should succeed") {
                BigInteger.ZERO.toBigGaussian() shouldBeEqual BigGaussian.ZERO
            }
            test("toBigComplex should succeed") {
                BigInteger.ZERO.toBigComplex() shouldBeEqual BigComplex.ZERO
            }
            test("toBigQuaternion should succeed") {
                BigInteger.ZERO.toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
            }
        },
    )
