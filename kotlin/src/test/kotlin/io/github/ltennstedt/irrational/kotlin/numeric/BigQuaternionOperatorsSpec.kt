package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.math.BigDecimal

class BigQuaternionOperatorsSpec :
    FunSpec(
        {
            test("Destructuring should succeed") {
                shouldNotThrow<Throwable> {
                    listOf(BigQuaternion.ZERO).map { (w, x, y, z) -> w + x + y + z }
                }
            }
            context("BigQuaternion") {
                val quaternion =
                    BigQuaternion(
                        BigDecimal.ONE,
                        BigDecimal.valueOf(2L),
                        BigDecimal.valueOf(3L),
                        BigDecimal.valueOf(4L),
                    )
                test("component1 should succeed") {
                    quaternion.component1() shouldBeSameInstanceAs BigDecimal.ONE
                }
                test("component2 should succeed") {
                    quaternion.component2() shouldBeSameInstanceAs BigDecimal.valueOf(2L)
                }
                test("component3 should succeed") {
                    quaternion.component3() shouldBeSameInstanceAs BigDecimal.valueOf(3L)
                }
                test("component4 should succeed") {
                    quaternion.component4() shouldBeSameInstanceAs BigDecimal.valueOf(4L)
                }
            }
        },
    )
