package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.BigIntegerVector
import io.github.ltennstedt.irrational.core.linear.BigIntegerVector.BigIntegerVectorEntry
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withShoulds
import io.kotest.matchers.equals.shouldBeEqual
import java.math.BigInteger

class BigIntegerVectorOperatorsSpec :
    ShouldSpec(
        {
            context("get") {
                val vector =
                    BigIntegerVector(
                        listOf(
                            BigIntegerVectorEntry(1, BigInteger.valueOf(3L)),
                            BigIntegerVectorEntry(2, BigInteger.valueOf(4L)),
                        ),
                    )
                should("succeed") {
                    vector[1] shouldBeEqual BigInteger.valueOf(3L)
                }
            }
            context("contains") {
                val vector = BigIntegerVector(listOf(BigIntegerVectorEntry(1, BigInteger.ZERO)))
                withShoulds(BigInteger.ONE to false, BigInteger.ZERO to true) { (value, expected) ->
                    (value in vector) shouldBeEqual expected
                }
                withShoulds(
                    BigIntegerVectorEntry(2, BigInteger.ZERO) to false,
                    BigIntegerVectorEntry(1, BigInteger.ONE) to false,
                    BigIntegerVectorEntry(1, BigInteger.ZERO) to true,
                ) { (entry, expected) -> (entry in vector) shouldBeEqual expected }
            }
        },
    )
