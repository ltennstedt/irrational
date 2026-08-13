package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.math.BigInteger

class BigGaussianOperatorsSpec :
    ShouldSpec({
        context("Destructuring") {
            should("succeed") {
                shouldNotThrow<Throwable> {
                    listOf(BigGaussian.ZERO).map { (r, i) -> r + i }
                }
            }
        }
        context("component1") {
            should("succeed") {
                BigGaussian.ONE.component1() shouldBeSameInstanceAs BigInteger.ONE
            }
        }
        context("component2") {
            should("succeed") {
                BigGaussian.ONE.component2() shouldBeSameInstanceAs BigInteger.ZERO
            }
        }
    })
