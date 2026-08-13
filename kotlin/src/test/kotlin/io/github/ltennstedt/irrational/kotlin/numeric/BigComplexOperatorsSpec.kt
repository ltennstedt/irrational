package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.math.BigDecimal

class BigComplexOperatorsSpec :
    ShouldSpec({
        context("Destructuring") {
            should("succeed") {
                shouldNotThrow<Throwable> {
                    listOf(BigComplex.ZERO).map { (r, i) -> r + i }
                }
            }
        }
        context("component1") {
            should("succeed") {
                BigComplex.ONE.component1() shouldBeSameInstanceAs BigDecimal.ONE
            }
        }
        context("component2") {
            should("succeed") {
                BigComplex.ONE.component2() shouldBeSameInstanceAs BigDecimal.ZERO
            }
        }
    })
