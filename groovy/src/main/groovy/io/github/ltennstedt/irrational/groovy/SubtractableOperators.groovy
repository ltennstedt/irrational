package io.github.ltennstedt.irrational.groovy

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.Subtractable

/** Operators for Numeric */
@CompileStatic
final class SubtractableOperators {
    private SubtractableOperators() {}
    /**
     * Unary - operator
     *
     * @param self self
     * @return negated
     */
    static <S extends Subtractable<S>> S negative(final S self) {
        self.negate()
    }

    /**
     * Binary - operator
     *
     * @param self self
     * @param subtrahend subtrahend
     * @return self
     */
    static <S extends Subtractable<S>> S minus(final S self, final S subtrahend) {
        self.subtract(subtrahend)
    }
}
