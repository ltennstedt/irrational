package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigGaussian
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.BigRational
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion
import io.github.ltennstedt.irrational.core.numeric.LongGaussian
import io.github.ltennstedt.irrational.core.numeric.LongRational

/** Extensions for {@link Short} */
@CompileStatic
final class ShortExtensions {
    private ShortExtensions() { }

    /**
     * Returns this as {@link LongRational}
     * @param self self
     * @return {@link LongRational}
     */
    static LongRational toLongRational(final Short self) {
        new LongRational(self, 1L)
    }

    /**
     * Returns this as {@link BigRational}
     * @param self self
     * @return {@link BigRational}
     */
    static BigRational toBigRational(final Short self) {
        new BigRational(BigInteger.valueOf(self), BigInteger.ONE)
    }

    /**
     * Returns this as {@link LongGaussian}
     * @param self self
     * @return {@link LongGaussian}
     */
    static LongGaussian toLongGaussian(final Short self) {
        new LongGaussian(self, 0L)
    }

    /**
     * Returns this as {@link BigGaussian}
     * @param self self
     * @return {@link BigGaussian}
     */
    static BigGaussian toBigGaussian(final Short self) {
        new BigGaussian(BigInteger.valueOf(self), BigInteger.ZERO)
    }

    /**
     * Returns this as {@link DoubleComplex}
     * @param self self
     * @return {@link DoubleComplex}
     */
    static DoubleComplex toDoubleComplex(final Short self) {
        new DoubleComplex(self, 0.0D)
    }

    /**
     * Returns this as {@link BigComplex}
     * @param self self
     * @return {@link BigComplex}
     */
    static BigComplex toBigComplex(final Short self) {
        new BigComplex(BigDecimal.valueOf(self), BigDecimal.ZERO)
    }

    /**
     * Returns this as {@link DoubleQuaternion}
     * @param self self
     * @return {@link DoubleQuaternion}
     */
    static DoubleQuaternion toDoubleQuaternion(final Short self) {
        new DoubleQuaternion(self, 0.0D, 0.0D, 0.0D)
    }

    /**
     * Returns this as {@link BigQuaternion}
     * @param self self
     * @return {@link BigQuaternion}
     */
    static BigQuaternion toBigQuaternion(final Short self) {
        new BigQuaternion(BigDecimal.valueOf(self), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }
}
