package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion
import io.github.ltennstedt.irrational.core.numeric.DoubleComplex
import io.github.ltennstedt.irrational.core.numeric.DoubleQuaternion

/** Extensions for {@link Float} */
@CompileStatic
final class FloatExtensions {
    private FloatExtensions() { }

    /**
     * Returns this as {@link DoubleComplex}
     * @param self self
     * @return {@link DoubleComplex}
     */
    static DoubleComplex toDoubleComplex(final Float self) {
        new DoubleComplex(self, 0.0D)
    }

    /**
     * Returns this as {@link BigComplex}
     * @param self self
     * @return {@link BigComplex}
     */
    static BigComplex toBigComplex(final Float self) {
        new BigComplex(new BigDecimal(self.toString()), BigDecimal.ZERO)
    }

    /**
     * Returns this as {@link DoubleQuaternion}
     * @param self self
     * @return {@link DoubleQuaternion}
     */
    static DoubleQuaternion toDoubleQuaternion(final Float self) {
        new DoubleQuaternion(self, 0.0D, 0.0D, 0.0D)
    }

    /**
     * Returns this as {@link BigQuaternion}
     * @param self self
     * @return {@link BigQuaternion}
     */
    static BigQuaternion toBigQuaternion(final Float self) {
        new BigQuaternion(new BigDecimal(self.toString()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }
}
