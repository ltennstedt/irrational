package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileDynamic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import io.github.ltennstedt.irrational.core.numeric.LongGaussian
import io.github.ltennstedt.irrational.core.numeric.LongRational
import spock.lang.Specification

@CompileDynamic
final class ByteExtensionsSpecification extends Specification {
    def "toLongRational should succeed"() {
        when:
        final actual = ((byte) 0).toLongRational()

        then:
        actual == LongRational.ZERO
    }

    def "toBigRational should succeed"() {
        when:
        final actual = ((byte) 0).toBigRational()

        then:
        actual == BigRational.ZERO
    }

    def "toLongGaussian should succeed"() {
        when:
        final actual = ((byte) 1).toLongGaussian()

        then:
        actual == LongGaussian.ONE
    }

    def "toBigGaussian should succeed"() {
        when:
        final actual = ((byte) 1).toBigGaussian()

        then:
        actual == BigGaussian.ONE
    }

    def "toDoubleComplex should succeed"() {
        when:
        final actual = ((byte) 1).toDoubleComplex()

        then:
        actual == DoubleComplex.ONE
    }

    def "toBigComplex should succeed"() {
        when:
        final actual = ((byte) 1).toBigComplex()

        then:
        actual == BigComplex.ONE
    }

    def "toDoubleQuaternion should succeed"() {
        when:
        final actual = ((byte) 1).toDoubleQuaternion()

        then:
        actual == DoubleQuaternion.ONE
    }

    def "toBigQuaternion should succeed"() {
        when:
        final actual = ((byte) 1).toBigQuaternion()

        then:
        actual == BigQuaternion.ONE
    }
}
