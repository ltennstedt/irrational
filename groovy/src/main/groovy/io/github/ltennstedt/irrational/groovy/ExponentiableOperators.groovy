package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.Exponentiable

/** Operators for {@link ExponentiableOperators} */
@CompileStatic
final class ExponentiableOperators {
    private ExponentiableOperators() {}

    /**
     * ** operator
     *
     * @param self self
     * @param exponent exponent
     * @return power
     */
    static <E extends Exponentiable<E, Q>, Q extends Exponentiable<Q, Q>> Q power(final E self, final int exponent) {
        self.pow(exponent)
    }
}
