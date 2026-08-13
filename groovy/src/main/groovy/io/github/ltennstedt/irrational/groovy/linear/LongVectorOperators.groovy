package io.github.ltennstedt.irrational.groovy.linear

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.linear.LongVector

/**
 * Extensions for LongVector
 */
@CompileStatic
final class LongVectorOperators {
    private LongVectorOperators() { }

    /**
     * Unary + operator
     *
     * @param self self
     * @return self
     */
    static LongVector positive(final LongVector self) {
        self
    }

    /**
     * Unary - operator
     *
     * @param self self
     * @return negated
     */
    static LongVector negative(final LongVector self) {
        self.negate()
    }

    /**
     * Binary + operator
     *
     * @param self self
     * @param summand summand
     * @return self
     */
    static LongVector plus(final LongVector self, final LongVector summand) {
        self.add(summand)
    }

    /**
     * ** operator
     *
     * @param self self
     * @return self
     */
    static LongVector minus(final LongVector self, final LongVector subtrahend) {
        self.subtract(subtrahend)
    }

    /**
     * ** operator
     *
     * @param self self
     * @param other other
     * @return self
     */
    static long multiply(final LongVector self, final LongVector other) {
        self.dotProduct(other)
    }
}
