package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.Divisible

/**
 * Operators for PrimitiveNumeric
 */
@CompileStatic
final class DivisibleOperators {
    private DivisibleOperators() {}

    /**
     * Binary / operator
     *
     * @param self self
     * @param divisor divisor
     * @return quotient
     */
    static <D extends Divisible<D, Q>, Q extends Divisible<Q, Q>> Q div(final D self, final D divisor) {
        self.divide(divisor)
    }
}
