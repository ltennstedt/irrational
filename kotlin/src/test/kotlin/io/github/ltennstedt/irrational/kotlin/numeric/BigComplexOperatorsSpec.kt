package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.math.BigDecimal

class BigComplexOperatorsSpec :
    FunSpec(
        {
            test("Destructuring should succeed") {
                shouldNotThrow<Throwable> {
                    listOf(BigComplex.ZERO).map { (r, i) -> r + i }
                }
            }
            test("component1 should succeed") {
                BigComplex.ONE.component1() shouldBeSameInstanceAs BigDecimal.ONE
            }
            test("component2 should should succeed") {
                BigComplex.ONE.component2() shouldBeSameInstanceAs BigDecimal.ZERO
            }
        },
    )
