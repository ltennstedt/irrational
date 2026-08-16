package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational

/** Extensions for {@link BigInteger} */
@CompileStatic
final class BigIntegerExtensions {
    private BigIntegerExtensions() { }

    /**
     * Returns this as {@link BigRational}
     * @param self self
     * @return {@link BigRational}
     */
    static BigRational toBigRational(final BigInteger self) {
        new BigRational(self, BigInteger.ONE)
    }

    /**
     * Returns this as {@link BigGaussian}
     * @param self self
     * @return {@link BigGaussian}
     */
    static BigGaussian toBigGaussian(final BigInteger self) {
        new BigGaussian(self, BigInteger.ZERO)
    }

    /**
     * Returns this as {@link BigComplex}
     * @param self self
     * @return {@link BigComplex}
     */
    static BigComplex toBigComplex(final BigInteger self) {
        new BigComplex(new BigDecimal(self), BigDecimal.ZERO)
    }

    /**
     * Returns this as {@link BigQuaternion}
     * @param self self
     * @return {@link BigQuaternion}
     */
    static BigQuaternion toBigQuaternion(final BigInteger self) {
        new BigQuaternion(new BigDecimal(self), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }
}
