package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import spock.lang.Specification

@CompileDynamic
final class BigDecimalExtensionsSpecification extends Specification {
    def "toBigComplex should succeed"() {
        when:
        final actual = BigDecimal.ONE.toBigComplex()

        then:
        actual == BigComplex.ONE
    }

    def "toBigQuaternion should succeed"() {
        when:
        final actual = BigDecimal.ONE.toBigQuaternion()

        then:
        actual == BigQuaternion.ONE
    }
}
