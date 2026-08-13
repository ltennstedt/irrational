package io.github.ltennstedt.irrational.groovy.numeric

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import spock.lang.Specification

@CompileDynamic
final class PrimitiveNumericOperatorsSpecification extends Specification {
    final complex = new DoubleComplex(1D, 3D)

    def "div should succeed"() {
        given:
        final divisor = new DoubleComplex(2D, 1D)

        when:
        final actual = complex / divisor

        then:
        actual == new DoubleComplex(1D, 1D)
    }

    def "power should succeed"() {
        given:
        final exponent = 0

        when:
        final actual = complex ** exponent

        then:
        actual == DoubleComplex.ONE
    }
}
