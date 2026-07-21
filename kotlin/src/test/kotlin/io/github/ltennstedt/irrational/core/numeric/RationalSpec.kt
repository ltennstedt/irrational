package io.github.ltennstedt.irrational.core.numeric

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigInteger

class RationalSpec :
    ShouldSpec({
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
