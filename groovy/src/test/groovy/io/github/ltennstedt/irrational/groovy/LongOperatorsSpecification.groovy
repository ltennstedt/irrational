package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.linear.LongVector
import spock.lang.Specification

@CompileDynamic
final class LongOperatorsSpecification extends Specification {
    def "times should succeed"() {
        given:
        final vector = LongVector.builder(2).entry(1, 1L).entry(2, 2L).build()

        when:
        final actual = 3L * vector

        then:
        actual == LongVector.builder(2).entry(1, 3L).entry(2, 6L).build()
    }
}
