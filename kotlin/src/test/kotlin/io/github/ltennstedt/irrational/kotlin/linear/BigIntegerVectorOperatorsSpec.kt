package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.BigIntegerVector
import io.github.ltennstedt.irrational.core.linear.BigIntegerVector.BigIntegerVectorEntry
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigInteger

class BigIntegerVectorOperatorsSpec :
    FunSpec(
        {
            test("get should succeed") {
                val vector =
                    BigIntegerVector(
                        listOf(
                            BigIntegerVectorEntry(1, BigInteger.valueOf(3L)),
                            BigIntegerVectorEntry(2, BigInteger.valueOf(4L)),
                        ),
                    )
                vector[1] shouldBeEqual BigInteger.valueOf(3L)
            }
            context("contains should succeed") {
                val vector = BigIntegerVector(listOf(BigIntegerVectorEntry(1, BigInteger.ZERO)))
                withData(BigInteger.ONE to false, BigInteger.ZERO to true) { (value, expected) ->
                    (value in vector) shouldBeEqual expected
                }
                withData(
                    BigIntegerVectorEntry(2, BigInteger.ZERO) to false,
                    BigIntegerVectorEntry(1, BigInteger.ONE) to false,
                    BigIntegerVectorEntry(1, BigInteger.ZERO) to true,
                ) { (entry, expected) -> (entry in vector) shouldBeEqual expected }
            }
        },
    )
