package sliderule.core.number

import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.jupiter.api.Test

class LongRationalKotlinTest {
    @Test
    fun `unaryPlus should succeed`() {
        +LongRational.ZERO shouldBeSameInstanceAs LongRational.ZERO
    }
}
