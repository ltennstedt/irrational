package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.LongRational
import spock.lang.Specification

@CompileDynamic
final class SubtractableOperatorsSpecification extends Specification {
    def "negative should succeed"() {
        given:
        final rational = new LongRational(1L, 2L)

        when:
        final actual = -rational

        then:
        actual == new LongRational(-1L, 2L)
    }

    def "minus should succeed"() {
        given:
        final rational = new LongRational(1L, 2L)
        final subtrahend = new LongRational(3L, 4L)

        when:
        final actual = rational - subtrahend

        then:
        actual == new LongRational(-1L, 4L)
    }
}
