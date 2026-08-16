package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.LongRational
import spock.lang.Specification

@CompileDynamic
final class AdditiveOperatorsSpecification extends Specification {
    def "positive should succeed"() {
        given:
        final rational = new LongRational(1L, 2L)

        when:
        final actual = +rational

        then:
        actual == rational
    }

    def "plus should succeed"() {
        given:
        final rational = new LongRational(1L, 2L)
        final summand = new LongRational(3L, 4L)

        when:
        final actual = rational + summand

        then:
        actual == new LongRational(5L, 4L)
    }
}
