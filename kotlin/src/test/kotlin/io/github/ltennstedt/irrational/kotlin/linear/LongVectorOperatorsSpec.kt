package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.LongVector
import io.github.ltennstedt.irrational.core.linear.LongVector.LongVectorEntry
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withShoulds
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldEqual
import io.kotest.matchers.types.shouldBeSameInstanceAs

class LongVectorOperatorsSpec :
    ShouldSpec(
        {
            context("unaryPlus") {
                should("succeed") {
                    +LongVector.EMPTY shouldBeSameInstanceAs LongVector.EMPTY
                }
            }
            context("unaryPlus") {
                val vector = LongVector(listOf(LongVectorEntry(1, 2L), LongVectorEntry(2, 3L)))
                should("succeed") {
                    val negated = LongVector(listOf(LongVectorEntry(1, -2L), LongVectorEntry(2, -3L)))
                    -vector shouldBeEqual negated
                }
            }
            context("plus") {
                val vector = LongVector(listOf(LongVectorEntry(1, 1L), LongVectorEntry(2, 2L)))
                val summand = LongVector(listOf(LongVectorEntry(1, 3L), LongVectorEntry(2, 4L)))
                should("succeed") {
                    val expected = LongVector(listOf(LongVectorEntry(1, 4L), LongVectorEntry(2, 6L)))
                    vector + summand shouldBeEqual expected
                }
            }
            context("minus") {
                val vector = LongVector(listOf(LongVectorEntry(1, 1L), LongVectorEntry(2, 2L)))
                val subtrahend = LongVector(listOf(LongVectorEntry(1, 3L), LongVectorEntry(2, 5L)))
                should("succeed") {
                    val expected = LongVector(listOf(LongVectorEntry(1, -2L), LongVectorEntry(2, -3L)))
                    vector - subtrahend shouldBeEqual expected
                }
            }
            context("times") {
                val vector = LongVector(listOf(LongVectorEntry(1, 1L), LongVectorEntry(2, 2L)))
                val other = LongVector(listOf(LongVectorEntry(1, 3L), LongVectorEntry(2, 4L)))
                should("succeed") {
                    vector * other shouldEqual 11L
                }
            }
            context("get") {
                val vector = LongVector(listOf(LongVectorEntry(1, 3L), LongVectorEntry(2, 4L)))
                should("succeed") {
                    vector[1] shouldBeEqual 3L
                }
            }
            context("contains") {
                val vector = LongVector(listOf(LongVectorEntry(1, 0L)))
                withShoulds(1L to false, 0L to true) { (value, expected) -> (value in vector) shouldBeEqual expected }
                withShoulds(
                    LongVectorEntry(2, 0L) to false,
                    LongVectorEntry(1, 1L) to false,
                    LongVectorEntry(1, 0L) to true,
                ) { (entry, expected) -> (entry in vector) shouldBeEqual expected }
            }
        },
    )
