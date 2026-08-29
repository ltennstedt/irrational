package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.shouldBeExactly

class DoubleComplexOperatorsSpec :
    FunSpec(
        {
            test("Destructuring should succeed") {
                shouldNotThrow<Throwable> {
                    listOf(DoubleComplex.ZERO).map { (r, i) -> r + i }
                }
            }
            test("component1 should succeed") {
                DoubleComplex.ONE.component1() shouldBeExactly 1.0
            }
            test("component2 should succeed") {
                DoubleComplex.ONE.component2() shouldBeExactly 0.0
            }
        },
    )
