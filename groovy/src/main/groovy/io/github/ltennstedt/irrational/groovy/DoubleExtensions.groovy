package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion

/** Extensions for {@link Double} */
@CompileStatic
final class DoubleExtensions {
    private DoubleExtensions() { }

    /**
     * Returns this as {@link DoubleComplex}
     * @param self self
     * @return {@link DoubleComplex}
     */
    static DoubleComplex toDoubleComplex(final Double self) {
        new DoubleComplex(self, 0.0D)
    }

    /**
     * Returns this as {@link BigComplex}
     * @param self self
     * @return {@link BigComplex}
     */
    static BigComplex toBigComplex(final Double self) {
        new BigComplex(new BigDecimal(self.toString()), BigDecimal.ZERO)
    }

    /**
     * Returns this as {@link DoubleQuaternion}
     * @param self self
     * @return {@link DoubleQuaternion}
     */
    static DoubleQuaternion toDoubleQuaternion(final Double self) {
        new DoubleQuaternion(self, 0.0D, 0.0D, 0.0D)
    }

    /**
     * Returns this as {@link BigQuaternion}
     * @param self self
     * @return {@link BigQuaternion}
     */
    static BigQuaternion toBigQuaternion(final Double self) {
        new BigQuaternion(new BigDecimal(self.toString()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }
}
