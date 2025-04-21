package irrational.number

import io.kotest.matchers.longs.shouldBeExactly
import org.junit.jupiter.api.Test

class LongRationKotlinTest {
    @Test
    fun `numerator should succeed`() {
        LongRational.ZERO.numerator shouldBeExactly 0L
    }

    @Test
    fun `denominator should succeed`() {
        LongRational.ZERO.denominator shouldBeExactly 1L
    }
}
