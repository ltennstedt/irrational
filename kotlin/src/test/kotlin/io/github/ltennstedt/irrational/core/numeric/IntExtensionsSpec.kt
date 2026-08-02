package io.github.ltennstedt.irrational.core.numeric

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual

class IntExtensionsSpec: ShouldSpec({
    context("toLongRational") {
        should("succeed") {
            0.toLongRational() shouldBeEqual LongRational.ZERO
        }
    }
    context("toBigRational") {
        should("succeed") {
            0.toBigRational() shouldBeEqual BigRational.ZERO
        }
    }
    context("toLongGaussian") {
        should("succeed") {
            0.toLongGaussian() shouldBeEqual LongGaussian.ZERO
        }
    }
    context("toBigGaussian") {
        should("succeed") {
            0.toBigGaussian() shouldBeEqual BigGaussian.ZERO
        }
    }
    context("toDoubleComplex") {
        should("succeed") {
            0.toDoubleComplex() shouldBeEqual DoubleComplex.ZERO
        }
    }
    context("toBigComplex") {
        should("succeed") {
            0.toBigComplex() shouldBeEqual BigComplex.ZERO
        }
    }
    context("toDoubleQuaternion") {
        should("succeed") {
            0.toDoubleQuaternion() shouldBeEqual DoubleQuaternion.ZERO
        }
    }
    context("toBigQuaternion") {
        should("succeed") {
            0.toBigQuaternion() shouldBeEqual BigQuaternion.ZERO
        }
    }
})
