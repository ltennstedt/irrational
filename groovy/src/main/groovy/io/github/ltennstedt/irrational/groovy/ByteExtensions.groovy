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

/** Extensions for {@link Byte} */
@CompileStatic
final class ByteExtensions {
    private ByteExtensions() { }

    /**
     * Returns this as {@link LongRational}
     * @param self self
     * @return {@link LongRational}
     */
    static LongRational toLongRational(final Byte self) {
        new LongRational(self, 1L)
    }

    /**
     * Returns this as {@link BigRational}
     * @param self self
     * @return {@link BigRational}
     */
    static BigRational toBigRational(final Byte self) {
        new BigRational(BigInteger.valueOf(self), BigInteger.ONE)
    }

    /**
     * Returns this as {@link LongGaussian}
     * @param self self
     * @return {@link LongGaussian}
     */
    static LongGaussian toLongGaussian(final Byte self) {
        new LongGaussian(self, 0L)
    }

    /**
     * Returns this as {@link BigGaussian}
     * @param self self
     * @return {@link BigGaussian}
     */
    static BigGaussian toBigGaussian(final Byte self) {
        new BigGaussian(BigInteger.valueOf(self), BigInteger.ZERO)
    }

    /**
     * Returns this as {@link DoubleComplex}
     * @param self self
     * @return {@link DoubleComplex}
     */
    static DoubleComplex toDoubleComplex(final Byte self) {
        new DoubleComplex(self, 0.0D)
    }

    /**
     * Returns this as {@link BigComplex}
     * @param self self
     * @return {@link BigComplex}
     */
    static BigComplex toBigComplex(final Byte self) {
        new BigComplex(BigDecimal.valueOf(self), BigDecimal.ZERO)
    }

    /**
     * Returns this as {@link DoubleQuaternion}
     * @param self self
     * @return {@link DoubleQuaternion}
     */
    static DoubleQuaternion toDoubleQuaternion(final Byte self) {
        new DoubleQuaternion(self, 0.0D, 0.0D, 0.0D)
    }

    /**
     * Returns this as {@link BigQuaternion}
     * @param self self
     * @return {@link BigQuaternion}
     */
    static BigQuaternion toBigQuaternion(final Byte self) {
        new BigQuaternion(BigDecimal.valueOf(self), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }
}
