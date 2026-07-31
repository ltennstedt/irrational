package io.github.ltennstedt.irrational.core.numeric

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.longs.shouldBeExactly

class LongRationalOperatorsSpec: ShouldSpec({
    context("Destructuring") {
        should("succeed") {
            shouldNotThrow<Throwable> {
                listOf(LongRational.ZERO).map { (n, d) -> n + d }
            }
        }
    }
    context("component1") {
        should("succeed") {
            LongRational.ZERO.component1() shouldBeExactly 0L
        }
    }
    context("component2") {
        should("succeed") {
            LongRational.ZERO.component2() shouldBeExactly 1L
        }
    }
})
