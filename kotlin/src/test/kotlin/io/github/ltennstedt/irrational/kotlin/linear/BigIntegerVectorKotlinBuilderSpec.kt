package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.BigIntegerVector
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldEqual
import java.math.BigInteger

class BigIntegerVectorKotlinBuilderSpec :
    FunSpec(
        {
            test("bigIntegerVector should throw when size is negative") {
                shouldThrow<IllegalArgumentException> {
                    bigIntegerVector(size = -1) { }
                }.message shouldEqual "size must be greater than or equal to 0 but was -1"
            }
            test("bigIntegerVector should succeed") {
                bigIntegerVector(size = 2) {
                    entry(index = 1, value = BigInteger.valueOf(3L))
                    entry(index = 2, value = BigInteger.valueOf(4L))
                } shouldBeEqual
                    BigIntegerVector
                        .builder(2)
                        .entry(1, BigInteger.valueOf(3L))
                        .entry(2, BigInteger.valueOf(4L))
                        .build()
            }
        },
    )
