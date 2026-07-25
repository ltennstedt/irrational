package io.github.ltennstedt.irrational.core.numeric

import groovy.transform.CompileStatic

/**
 * Operators for PrimitiveNumeric
 */
@CompileStatic
final class PrimitiveNumericOperators {
    private PrimitiveNumericOperators() {}

    /**
     * Binary / operator
     *
     * @param self self
     * @param divisor divisor
     * @return quotient
     */
    static <N extends PrimitiveNumeric<N, Q>, Q extends PrimitiveNumeric<Q, Q>> Q div(final N self, final N divisor) {
        return self.divide(divisor)
    }

    /**
     * ** operator
     *
     * @param self self
     * @param exponent exponent
     * @return power
     */
    static <N extends PrimitiveNumeric<N, Q>, Q extends PrimitiveNumeric<Q, Q>> Q power(final N self,
        final int exponent) {
        return self.pow(exponent)
    }
}
