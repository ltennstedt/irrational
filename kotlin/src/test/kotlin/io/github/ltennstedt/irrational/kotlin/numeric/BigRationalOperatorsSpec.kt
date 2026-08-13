package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigInteger

class BigRationalOperatorsSpec :
    ShouldSpec({
        context("Destructuring") {
            should("succeed") {
                shouldNotThrow<Throwable> {
                    listOf(BigRational.ZERO).map { (n, d) -> n + d }
                }
            }
        }
        context("component1") {
            should("succeed") {
                BigRational.ZERO.component1() shouldBeEqual BigInteger.ZERO
            }
        }
        context("component2") {
            should("succeed") {
                BigRational.ZERO.component2() shouldBeEqual BigInteger.ONE
            }
        }
        context("div") {
            should("succeed") {
                BigRational(
                    BigInteger.ONE,
                    BigInteger.TWO,
                ) / BigRational(BigInteger.valueOf(3L), BigInteger.valueOf(4L)) shouldBeEqual
                    BigRational(BigInteger.TWO, BigInteger.valueOf(3L))
            }
        }
    })
