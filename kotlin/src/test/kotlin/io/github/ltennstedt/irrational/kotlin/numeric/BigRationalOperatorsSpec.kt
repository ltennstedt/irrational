package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigInteger

class BigRationalOperatorsSpec :
    FunSpec(
        {
            test("Destructuring should succeed") {
                shouldNotThrow<Throwable> {
                    listOf(BigRational.ZERO).map { (n, d) -> n + d }
                }
            }
            test("component1 should succeed") {
                BigRational.ZERO.component1() shouldBeEqual BigInteger.ZERO
            }
            test("component2 should succeed") {
                BigRational.ZERO.component2() shouldBeEqual BigInteger.ONE
            }
            test("div should succeed") {
                BigRational(
                    BigInteger.ONE,
                    BigInteger.TWO,
                ) / BigRational(BigInteger.valueOf(3L), BigInteger.valueOf(4L)) shouldBeEqual
                    BigRational(BigInteger.TWO, BigInteger.valueOf(3L))
            }
        },
    )
