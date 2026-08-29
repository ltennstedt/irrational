package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.math.BigInteger

class BigGaussianOperatorsSpec :
    FunSpec(
        {
            test("Destructuring should succeed") {
                shouldNotThrow<Throwable> {
                    listOf(BigGaussian.ZERO).map { (r, i) -> r + i }
                }
            }
            test("component1 should succeed") {
                BigGaussian.ONE.component1() shouldBeSameInstanceAs BigInteger.ONE
            }
            test("component2 should succeed") {
                BigGaussian.ONE.component2() shouldBeSameInstanceAs BigInteger.ZERO
            }
        },
    )
