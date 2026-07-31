package io.github.ltennstedt.irrational.core.numeric

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.longs.shouldBeExactly

class LongGaussianOperatorsSpec: ShouldSpec({
    context("Destructuring") {
        should("succeed") {
            shouldNotThrow<Throwable> {
                listOf(LongGaussian.ZERO).map { (r, i) -> r + i }
            }
        }
    }
    context("component1") {
        should("succeed") {
            LongGaussian.ONE.component1() shouldBeExactly 1L
        }
    }
    context("component2") {
        should("succeed") {
            LongGaussian.ONE.component2() shouldBeExactly 0L
        }
    }
})
