package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.linear.LongVector

/** Extensions for {@link Long */
@CompileStatic
final class LongOperators {
    private LongOperators() { }

    /**
     * Binary * operator
     *
     * @param self self
     * @param vector vector
     * @return scalar product
     */
    static LongVector multiply(final Long self, final LongVector vector) {
        vector.scalarMultiply(self)
    }
}
