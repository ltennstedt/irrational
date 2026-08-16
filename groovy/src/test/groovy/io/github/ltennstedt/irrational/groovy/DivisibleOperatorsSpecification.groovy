package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import spock.lang.Specification

@CompileDynamic
final class DivisibleOperatorsSpecification extends Specification {
    def "div should succeed"() {
        given:
        final complex = new DoubleComplex(1D, 3D)
        final divisor = new DoubleComplex(2D, 1D)

        when:
        final actual = complex / divisor

        then:
        actual == new DoubleComplex(1D, 1D)
    }
}
