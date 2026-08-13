package io.github.ltennstedt.irrational.groovy.numeric

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.numeric.Numeric

/** Operators for Numeric */
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
        self
    }

    /**
     * Unary - operator
     *
     * @param self self
     * @return negated
     */
    static <N extends Numeric<N, Q>, Q extends Numeric<Q, Q>> N negative(final N self) {
        self.negate()
    }

    /**
     * Binary + operator
     *
     * @param self self
     * @param summand summand
     * @return self
     */
    static <N extends Numeric<N, Q>, Q extends Numeric<Q, Q>> N plus(final N self, final N summand) {
        self.add(summand)
    }

    /**
     * ** operator
     *
     * @param self self
     * @return self
     */
    static <N extends Numeric<N, Q>, Q extends Numeric<Q, Q>> N minus(final N self, final N subtrahend) {
        self.subtract(subtrahend)
    }
}
