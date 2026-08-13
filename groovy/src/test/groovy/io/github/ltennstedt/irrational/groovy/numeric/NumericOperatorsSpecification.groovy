package io.github.ltennstedt.irrational.groovy.numeric

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.LongRational
import spock.lang.Specification

@CompileDynamic
final class NumericOperatorsSpecification extends Specification {
    final rational1 = new LongRational(1L, 2L)
    final rational2 = new LongRational(3L, 4L)

    def "positive should succeed"() {
        when:
        final actual = +rational1

        then:
        actual == rational1
    }

    def "negative should succeed"() {
        when:
        final actual = -rational1

        then:
        actual == new LongRational(-1L, 2L)
    }

    def "plus should succeed"() {
        when:
        final actual = rational1 + rational2

        then:
        actual == new LongRational(5L, 4L)
    }

    def "minus should succeed"() {
        when:
        final actual = rational1 - rational2

        then:
        actual == new LongRational(-1L, 4L)
    }

    def "multiply should succeed"() {
        when:
        final actual = rational1 * rational2

        then:
        actual == new LongRational(3L, 8L)
    }
}
