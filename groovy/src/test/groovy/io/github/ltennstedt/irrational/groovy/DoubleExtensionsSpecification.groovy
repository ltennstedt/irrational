package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import spock.lang.Specification

@CompileDynamic
final class DoubleExtensionsSpecification extends Specification {
    def "toDoubleComplex should succeed"() {
        when:
        final actual = 1.0D.toDoubleComplex()

        then:
        actual == DoubleComplex.ONE
    }

    def "toBigComplex should succeed"() {
        when:
        final actual = 1.0D.toBigComplex()

        then:
        actual == new BigComplex(new BigDecimal('1.0'), BigDecimal.ZERO)
    }

    def "toDoubleQuaternion should succeed"() {
        when:
        final actual = 1.0D.toDoubleQuaternion()

        then:
        actual == DoubleQuaternion.ONE
    }

    def "toBigQuaternion should succeed"() {
        when:
        final actual = 1.0D.toBigQuaternion()

        then:
        actual == new BigQuaternion(new BigDecimal('1.0'), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }
}
