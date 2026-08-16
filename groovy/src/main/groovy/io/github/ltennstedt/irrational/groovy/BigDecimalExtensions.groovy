package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.numeric.BigComplex
import io.github.ltennstedt.irrational.core.numeric.BigQuaternion

/** Extensions for {@link BigDecimal} */
@CompileStatic
final class BigDecimalExtensions {
    private BigDecimalExtensions() { }

    /**
     * Returns this as {@link BigComplex}
     * @param self self
     * @return {@link BigComplex}
     */
    static BigComplex toBigComplex(final BigDecimal self) {
        new BigComplex(self, BigDecimal.ZERO)
    }

    /**
     * Returns this as {@link BigQuaternion}
     * @param self self
     * @return {@link BigQuaternion}
     */
    static BigQuaternion toBigQuaternion(final BigDecimal self) {
        new BigQuaternion(self, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }
}
