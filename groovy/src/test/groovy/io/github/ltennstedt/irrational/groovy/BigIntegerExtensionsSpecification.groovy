package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import spock.lang.Specification

@CompileDynamic
final class BigIntegerExtensionsSpecification extends Specification {
    def "toBigRational should succeed"() {
        when:
        final actual = BigInteger.ZERO.toBigRational()

        then:
        actual == BigRational.ZERO
    }

    def "toBigGaussian should succeed"() {
        when:
        final actual = BigInteger.ONE.toBigGaussian()

        then:
        actual == BigGaussian.ONE
    }

    def "toBigComplex should succeed"() {
        when:
        final actual = BigInteger.ONE.toBigComplex()

        then:
        actual == BigComplex.ONE
    }

    def "toBigQuaternion should succeed"() {
        when:
        final actual = BigInteger.ONE.toBigQuaternion()

        then:
        actual == BigQuaternion.ONE
    }
}
