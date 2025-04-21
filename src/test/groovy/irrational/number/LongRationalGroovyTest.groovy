package irrational.number

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

@CompileStatic
final class LongRationalGroovyTest {
    @Test
    void "numerator should succeed"() {
        assert LongRational.ZERO.numerator === 0L
    }

    @Test
    void "denominator should succeed"() {
        assert LongRational.ZERO.denominator === 1L
    }

    @Test
    void "multiply should succeed"() {
        assert LongRational.of(2L, 3L) * LongRational.of(4L, 5L) == LongRational.of(8L, 15L)
    }
}
