package io.github.ltennstedt.irrational.kotlin.numeric

import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.shouldBeExactly

class DoubleQuaternionOperatorsSpec :
    FunSpec(
        {
            test("Destructuring should succeed") {
                shouldNotThrow<Throwable> {
                    listOf(DoubleQuaternion.ZERO).map { (w, x, y, z) -> w + x + y + z }
                }
            }
            context("DoubleQuaternion") {
                val quaternion = DoubleQuaternion(1.0, 2.0, 3.0, 4.0)
                test("component1 should succeed") {
                    quaternion.component1() shouldBeExactly 1.0
                }
                test("component2 should succeed") {
                    quaternion.component2() shouldBeExactly 2.0
                }
                test("component3 should succeed") {
                    quaternion.component3() shouldBeExactly 3.0
                }
                test("component4 should succeed") {
                    quaternion.component4() shouldBeExactly 4.0
                }
            }
        },
    )
