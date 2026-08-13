package io.github.ltennstedt.irrational.groovy.numeric

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.BigRational
import spock.lang.Specification

@CompileDynamic
final class BigRationalOperatorsSpecification extends Specification {
    final complex = new BigRational(BigInteger.ONE, BigInteger.TWO)

    def "div should succeed"() {
        given:
        final divisor = new BigRational(BigInteger.valueOf(3L), BigInteger.valueOf(4L))

        when:
        final actual = complex / divisor

        then:
        actual == new BigRational(BigInteger.TWO, BigInteger.valueOf(3L))
    }

    def "power should succeed"() {
        given:
        final exponent = 0

        when:
        final actual = complex ** exponent

        then:
        actual == BigRational.ONE
    }
}
