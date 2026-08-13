package io.github.ltennstedt.irrational.kotlin

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import io.github.ltennstedt.irrational.core.numeric.LongGaussian
import io.github.ltennstedt.irrational.core.numeric.LongRational
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual

class ByteExtensionsSpec :
    ShouldSpec({
        context("toLongRational") {
            should("succeed") {
                0.toByte().toLongRational() shouldBeEqual LongRational.ZERO
            }
        }
        context("toBigRational") {
            should("succeed") {
                0.toByte().toBigRational() shouldBeEqual BigRational.ZERO
            }
        }
        context("toLongGaussian") {
            should("succeed") {
                0.toByte().toLongGaussian() shouldBeEqual LongGaussian.ZERO
            }
        }
        context("toBigGaussian") {
            should("succeed") {
                0.toByte().toBigGaussian() shouldBeEqual BigGaussian.ZERO
            }
        }
        context("toDoubleComplex") {
            should("succeed") {
                0.toByte().toDoubleComplex() shouldBeEqual DoubleComplex.ZERO
            }
        }
        context("toBigComplex") {
            should("succeed") {
                0.toByte().toBigComplex() shouldBeEqual BigComplex.ZERO
            }
        }
        context("toDoubleQuaternion") {
            should("succeed") {
                0.toByte().toDoubleQuaternion() shouldBeEqual DoubleQuaternion.ZERO
            }
        }
        context("toBigQuaternion") {
            should("succeed") {
                0.toByte().toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
            }
        }
    })
