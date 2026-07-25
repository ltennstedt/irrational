package io.github.ltennstedt.irrational.core.numeric

import groovy.transform.CompileStatic

/**
 * Operators for Numeric
 */
@CompileStatic
final class NumericOperators {
    private NumericOperators() {}

    /**
     * Unary + operator
     *
     * @param self self
     * @return self
     */
    static <N extends Numeric<N, Q>, Q extends Numeric<Q, Q>> N positive(final N self) {
        return self
    }

    /**
     * Unary - operator
     *
     * @param self self
     * @return negated
     */
    static <N extends Numeric<N, Q>, Q extends Numeric<Q, Q>> N negative(final N self) {
        return self.negate()
    }

    /**
     * Binary + operator
     *
     * @param self self
     * @param summand summand
     * @return self
     */
    static <N extends Numeric<N, Q>, Q extends Numeric<Q, Q>> N plus(final N self, final N summand) {
        return self.add(summand)
    }

    /**
     * ** operator
     *
     * @param self self
     * @return self
     */
    static <N extends Numeric<N, Q>, Q extends Numeric<Q, Q>> N minus(final N self, final N subtrahend) {
        return self.subtract(subtrahend)
    }
}
