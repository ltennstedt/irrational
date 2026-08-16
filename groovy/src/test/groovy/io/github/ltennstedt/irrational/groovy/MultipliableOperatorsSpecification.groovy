package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.LongRational
import spock.lang.Specification

@CompileDynamic
final class MultipliableOperatorsSpecification extends Specification {
    def "multiply should succeed"() {
        given:
        final rational = new LongRational(1L, 2L)
        final multiplier = new LongRational(3L, 4L)

        when:
        final actual = rational * multiplier

        then:
        actual == new LongRational(3L, 8L)
    }
}
