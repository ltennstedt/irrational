package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import spock.lang.Specification

@CompileDynamic
final class ExponentiableOperatorsSpecification extends Specification {
    def "power should succeed"() {
        given:
        final complex = new DoubleComplex(1D, 3D)

        when:
        final actual = complex ** 0

        then:
        actual == DoubleComplex.ONE
    }
}
