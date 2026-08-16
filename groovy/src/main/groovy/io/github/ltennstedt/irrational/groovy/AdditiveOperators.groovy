package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.Additive

/** Operators for Numeric */
@CompileStatic
final class AdditiveOperators {
    private AdditiveOperators() {}

    /**
     * Unary + operator
     *
     * @param self self
     * @return self
     */
    static <A extends Additive<A>> A positive(final A self) {
        self
    }

    /**
     * Binary + operator
     *
     * @param self self
     * @param summand summand
     * @return self
     */
    static <A extends Additive<A>> A plus(final A self, final A summand) {
        self.add(summand)
    }
}
