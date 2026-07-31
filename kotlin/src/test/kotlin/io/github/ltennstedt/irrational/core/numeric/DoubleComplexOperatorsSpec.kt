package io.github.ltennstedt.irrational.core.numeric

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.doubles.shouldBeExactly

class DoubleComplexOperatorsSpec: ShouldSpec({
    context("Destructuring") {
        should("succeed") {
            shouldNotThrow<Throwable> {
                listOf(DoubleComplex.ZERO).map { (r, i) -> r + i }
            }
        }
    }
    context("component1") {
        should("succeed") {
            DoubleComplex.ONE.component1() shouldBeExactly 1.0
        }
    }
    context("component2") {
        should("succeed") {
            DoubleComplex.ONE.component2() shouldBeExactly 0.0
        }
    }
})
