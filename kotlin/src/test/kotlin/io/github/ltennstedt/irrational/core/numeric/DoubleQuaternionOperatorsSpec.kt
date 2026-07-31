package io.github.ltennstedt.irrational.core.numeric

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.doubles.shouldBeExactly

class DoubleQuaternionOperatorsSpec: ShouldSpec({
    context("Destructuring") {
        should("succeed") {
            shouldNotThrow<Throwable> {
                listOf(DoubleQuaternion.ZERO).map { (w, x, y, z) -> w + x + y + z }
            }
        }
    }
    val quaternion = DoubleQuaternion(1.0, 2.0, 3.0, 4.0)
    context("component1") {
        should("succeed") {
            quaternion.component1() shouldBeExactly 1.0
        }
    }
    context("component2") {
        should("succeed") {
            quaternion.component2() shouldBeExactly 2.0
        }
    }
    context("component3") {
        should("succeed") {
            quaternion.component3() shouldBeExactly 3.0
        }
    }
    context("component4") {
        should("succeed") {
            quaternion.component4() shouldBeExactly 4.0
        }
    }
})
