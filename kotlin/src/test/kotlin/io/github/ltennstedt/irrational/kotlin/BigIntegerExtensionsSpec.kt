package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigInteger

class BigIntegerExtensionsSpec :
    ShouldSpec({
        context("toBigRational") {
            should("succeed") {
                BigInteger.ZERO.toBigRational() shouldBeEqual BigRational.ZERO
            }
        }
        context("toBigGaussian") {
            should("succeed") {
                BigInteger.ZERO.toBigGaussian() shouldBeEqual BigGaussian.ZERO
            }
        }
        context("toBigComplex") {
            should("succeed") {
                BigInteger.ZERO.toBigComplex() shouldBeEqual BigComplex.ZERO
            }
        }
        context("toBigQuaternion") {
            should("succeed") {
                BigInteger.ZERO.toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
            }
        }
    })
