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

class ShortExtensionsSpec :
    ShouldSpec({
        context("toLongRational") {
            should("succeed") {
                0.toShort().toLongRational() shouldBeEqual LongRational.ZERO
            }
        }
        context("toBigRational") {
            should("succeed") {
                0.toShort().toBigRational() shouldBeEqual BigRational.ZERO
            }
        }
        context("toLongGaussian") {
            should("succeed") {
                0.toShort().toLongGaussian() shouldBeEqual LongGaussian.ZERO
            }
        }
        context("toBigGaussian") {
            should("succeed") {
                0.toShort().toBigGaussian() shouldBeEqual BigGaussian.ZERO
            }
        }
        context("toDoubleComplex") {
            should("succeed") {
                0.toShort().toDoubleComplex() shouldBeEqual DoubleComplex.ZERO
            }
        }
        context("toBigComplex") {
            should("succeed") {
                0.toShort().toBigComplex() shouldBeEqual BigComplex.ZERO
            }
        }
        context("toDoubleQuaternion") {
            should("succeed") {
                0.toShort().toDoubleQuaternion() shouldBeEqual DoubleQuaternion.ZERO
            }
        }
        context("toBigQuaternion") {
            should("succeed") {
                0.toShort().toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
            }
        }
    })
