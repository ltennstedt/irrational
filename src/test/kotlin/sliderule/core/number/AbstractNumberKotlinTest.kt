package sliderule.core.number

import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test

class AbstractNumberKotlinTest {
    private val rational1 = LongRational(2, 3)
    private val rational2 = LongRational(4, 5)

    @Test
    fun `unaryMinus should succeed`() {
        -LongRational.ONE shouldBeEqual LongRational.ONE.negate()
    }

    @Test
    fun `plus should succeed`() {
        rational1 + rational2 shouldBeEqual rational1.add(rational2)
    }

    @Test
    fun `minus should succeed`() {
        rational1 - rational2 shouldBeEqual rational1.subtract(rational2)
    }

    @Test
    fun `times should succeed`() {
        rational1 * rational2 shouldBeEqual rational1.multiply(rational2)
    }

    @Test
    fun `div should succeed`() {
        rational1 / rational2 shouldBeEqual rational1.divide(rational2)
    }
}
