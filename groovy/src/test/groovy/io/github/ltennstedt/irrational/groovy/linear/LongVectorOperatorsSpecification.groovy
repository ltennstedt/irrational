package io.github.ltennstedt.irrational.groovy.linear

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.linear.LongVector
import spock.lang.Specification

@CompileDynamic
final class LongVectorOperatorsSpecification extends Specification {
    final vector = LongVector.builder(2).entry(1, 1L).entry(2, 2L).build()

    def "positive should succeed"() {
        when:
        final actual = +vector

        then:
        actual == vector
    }

    def "negative should succeed"() {
        when:
        final actual = -vector

        then:
        actual == LongVector.builder(2).entry(1, -1L).entry(2, -2L).build()
    }

    def "plus should succeed"() {
        given:
        final summand = LongVector.builder(2).entry(1, 3L).entry(2, 4L).build()

        when:
        final actual = vector + summand

        then:
        actual == LongVector.builder(2).entry(1, 4L).entry(2, 6L).build()
    }

    def "minus should succeed"() {
        given:
        final subtrahend = LongVector.builder(2).entry(1, 3L).entry(2, 5L).build()

        when:
        final actual = vector - subtrahend

        then:
        actual == LongVector.builder(2).entry(1, -2L).entry(2, -3L).build()
    }

    def "times should succeed"() {
        given:
        final other = LongVector.builder(2).entry(1, 3L).entry(2, 4L).build()

        when:
        final actual = vector * other

        then:
        actual == 11L
    }
}
